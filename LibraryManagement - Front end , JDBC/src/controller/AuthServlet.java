package controller;

import model.Role;
import service.AuthService;
import service.RoleService;
import utils.ResponseUtil;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class AuthServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if ("/login".equals(pathInfo)) {
            handleLogin(req, resp);
        } else if ("/logout".equals(pathInfo)) {
            handleLogout(req, resp);
        } else {
            ResponseUtil.notFound(resp, "Endpoint not found");
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            
            String jsonString = sb.toString().trim();
            if (jsonString.isEmpty()) {
                ResponseUtil.badRequest(resp, "Request body is empty");
                return;
            }
            
            Gson gson = new Gson();
            LoginRequest loginRequest;
            try {
                loginRequest = gson.fromJson(jsonString, LoginRequest.class);
            } catch (Exception e) {
                ResponseUtil.badRequest(resp, "Invalid JSON format: " + e.getMessage());
                return;
            }
            
            if (loginRequest == null || loginRequest.email == null || loginRequest.password == null) {
                ResponseUtil.badRequest(resp, "Email and password are required");
                return;
            }

            AuthService authService = new AuthService();
            AuthService.LoginResponse loginResponse = authService.login(loginRequest.email, loginRequest.password);
            
            
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", loginResponse.user.getId());
            session.setAttribute("userEmail", loginResponse.user.getEmail());
            session.setAttribute("userName", loginResponse.user.getName());
            session.setAttribute("userRole", loginResponse.user.getRoleId());
            session.setMaxInactiveInterval(30 * 60);
            
            ResponseUtil.sendSuccess(resp, loginResponse);
            
        } catch (IllegalArgumentException e) {
            ResponseUtil.unauthorized(resp, e.getMessage());
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error during login: " + e.getMessage());
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            ResponseUtil.sendSuccess(resp, "Logged out successfully");
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error during logout: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if ("/me".equals(pathInfo)) {
            handleGetCurrentUser(req, resp);
        } else {
            ResponseUtil.notFound(resp, "Endpoint not found");
        }
    }

    private void handleGetCurrentUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            if (session == null) {
                ResponseUtil.unauthorized(resp, "Not authenticated");
                return;
            }
            
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) {
                ResponseUtil.unauthorized(resp, "Not authenticated");
                return;
            }
            
            Integer userRoleId = (Integer) session.getAttribute("userRole");
            RoleService roleService = new RoleService();
            Role userRole = roleService.getRoleById(userRoleId);
            
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", session.getAttribute("userId"));
            userInfo.put("userEmail", session.getAttribute("userEmail"));
            userInfo.put("userName", session.getAttribute("userName"));
            userInfo.put("userRole", session.getAttribute("userRole"));
            userInfo.put("userRoleName", userRole != null ? userRole.getName() : "Unknown");
            userInfo.put("isLoggedIn", true);
            
            if (userRole != null) {
                Map<String, Boolean> permissions = new HashMap<>();
                permissions.put("canViewBooks", userRole.isCanViewBooks());
                permissions.put("canAddBooks", userRole.isCanAddBooks());
                permissions.put("canUpdateBooks", userRole.isCanUpdateBooks());
                permissions.put("canDeleteBooks", userRole.isCanDeleteBooks());
                permissions.put("canViewAuthors", userRole.isCanViewAuthors());
                permissions.put("canAddAuthors", userRole.isCanAddAuthors());
                permissions.put("canUpdateAuthors", userRole.isCanUpdateAuthors());
                permissions.put("canDeleteAuthors", userRole.isCanDeleteAuthors());
                permissions.put("canViewAllocations", userRole.isCanViewAllocations());
                permissions.put("canAddAllocations", userRole.isCanAddAllocations());
                permissions.put("canUpdateAllocations", userRole.isCanUpdateAllocations());
                permissions.put("canViewUsers", userRole.isCanViewUsers());
                permissions.put("canAddUsers", userRole.isCanAddUsers());
                permissions.put("canUpdateUsers", userRole.isCanUpdateUsers());
                permissions.put("canDeleteUsers", userRole.isCanDeleteUsers());
                userInfo.put("permissions", permissions);
            }
            
            ResponseUtil.sendSuccess(resp, userInfo);
            
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error fetching current user: " + e.getMessage());
        }
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }
} 