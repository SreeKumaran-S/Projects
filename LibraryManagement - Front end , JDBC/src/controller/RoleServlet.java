package controller;

import model.Role;
import service.RoleService;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class RoleServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        try {
            RoleService roleService = new RoleService();
            
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Role> roles = roleService.getAllRoles();
                ResponseUtil.sendSuccess(resp, roles);
            } else {
                String roleIdStr = pathInfo.substring(1); 
                try {
                    int roleId = Integer.parseInt(roleIdStr);
                    Role role = roleService.getRoleById(roleId);
                    ResponseUtil.sendSuccess(resp, role);
                } catch (NumberFormatException e) {
                    ResponseUtil.badRequest(resp, "Invalid role ID format");
                } catch (IllegalArgumentException e) {
                    ResponseUtil.badRequest(resp, e.getMessage());
                }
            }
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error fetching roles: " + e.getMessage());
        }
    }
}