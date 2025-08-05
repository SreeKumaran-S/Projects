package controller;

import model.User;
import service.UserService;
import utils.ResponseUtil;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class UserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            User user;
            try {
                user = gson.fromJson(jsonString, User.class);
            } catch (Exception e) {
                ResponseUtil.badRequest(resp, "Invalid JSON format: " + e.getMessage());
                return;
            }
            
            if (user == null) {
                ResponseUtil.badRequest(resp, "Failed to parse user data");
                return;
            }

            UserService userService = new UserService();
            userService.addUser(user);
            ResponseUtil.sendSuccess(resp, "User added successfully");
            
        } catch (NumberFormatException e) {
            ResponseUtil.badRequest(resp, "Invalid number format in input");
        } catch (IllegalArgumentException e) {
            ResponseUtil.badRequest(resp, e.getMessage());
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error adding user: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                int userId = Integer.parseInt(pathInfo.substring(1));
                UserService userService = new UserService();
                User user = userService.getUserById(userId);
                ResponseUtil.sendSuccess(resp, user);
            } catch (NumberFormatException e) {
                ResponseUtil.badRequest(resp, "Invalid user ID format");
            } catch (IllegalArgumentException e) {
                ResponseUtil.badRequest(resp, e.getMessage());
            } catch (Exception e) {
                ResponseUtil.serverError(resp, "Error fetching user");
            }
        } else {
            String startIndexParam = req.getParameter("startIndex");
            String limitParam = req.getParameter("limit");
            int startIndex = 0;
            int limit = 10;

            try {
                if (startIndexParam != null)
                    startIndex = Integer.parseInt(startIndexParam);
                if (limitParam != null)
                    limit = Integer.parseInt(limitParam);

                UserService userService = new UserService();
                List<User> users = userService.getUsersPaginated(startIndex, limit);

                ResponseUtil.sendSuccess(resp, users);
            } catch (NumberFormatException e) {
                ResponseUtil.badRequest(resp, "Invalid pagination parameters");
            } catch (IllegalArgumentException e) {
                ResponseUtil.badRequest(resp, e.getMessage());
            } catch (Exception e) {
                ResponseUtil.serverError(resp, "Error fetching users");
            }
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.length() <= 1) {
            ResponseUtil.badRequest(resp, "User ID is required");
            return;
        }
        
        try {
            int userId = Integer.parseInt(pathInfo.substring(1));
            
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
            User user;
            try {
                user = gson.fromJson(jsonString, User.class);
            } catch (Exception e) {
                ResponseUtil.badRequest(resp, "Invalid JSON format: " + e.getMessage());
                return;
            }
            
            if (user == null) {
                ResponseUtil.badRequest(resp, "Failed to parse user data");
                return;
            }
            
            user.setId(userId);
            
            UserService userService = new UserService();
            userService.updateUser(user);
            ResponseUtil.sendSuccess(resp, "User updated successfully");
            
        } catch (NumberFormatException e) {
            ResponseUtil.badRequest(resp, "Invalid user ID format");
        } catch (IllegalArgumentException e) {
            ResponseUtil.badRequest(resp, e.getMessage());
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error updating user: " + e.getMessage());
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.length() <= 1) {
            ResponseUtil.badRequest(resp, "User ID is required");
            return;
        }
        
        try {
            int userId = Integer.parseInt(pathInfo.substring(1));
            
            UserService userService = new UserService();
            userService.deleteUser(userId);
            ResponseUtil.sendSuccess(resp, "User deleted successfully");
            
        } catch (NumberFormatException e) {
            ResponseUtil.badRequest(resp, "Invalid user ID format");
        } catch (IllegalArgumentException e) {
            ResponseUtil.badRequest(resp, e.getMessage());
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error deleting user: " + e.getMessage());
        }
    }
} 