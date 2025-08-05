package filter;


import service.RoleService;
import utils.ResponseUtil;
import model.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PermissionBasedAuthFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            ResponseUtil.unauthorized(httpResponse, "Authentication required");
            return;
        }
        
        Integer userRoleId = (Integer) session.getAttribute("userRole");
        if (userRoleId == null) {
            ResponseUtil.forbidden(httpResponse, "User role not found");
            return;
        }
        
        try {
            RoleService roleService = new RoleService();
            Role userRole = roleService.getRoleById(userRoleId);
            
            if (userRole == null) {
                ResponseUtil.forbidden(httpResponse, "Invalid user role");
                return;
            }
            
            String requestPath = httpRequest.getServletPath();
            String httpMethod = httpRequest.getMethod();
            
            if (!hasPermission(userRole, requestPath, httpMethod)) {
                ResponseUtil.forbidden(httpResponse, "Access denied. You don't have permission to perform this action.");
                return;
            }
            
            chain.doFilter(request, response);
            
        } catch (Exception e) {
            ResponseUtil.serverError(httpResponse, "Error checking permissions: " + e.getMessage());
        }
    }
    
    private boolean hasPermission(Role userRole, String requestPath, String httpMethod) {
        switch (httpMethod) {
            case "GET":
                if ("/books".equals(requestPath)) {
                    return userRole.isCanViewBooks();
                } else if ("/authors".equals(requestPath)) {
                    return userRole.isCanViewAuthors();
                } else if ("/allocations".equals(requestPath)) {
                    return userRole.isCanViewAllocations();
                } else if ("/users".equals(requestPath)) {
                    return userRole.isCanViewUsers();
                } else if ("/roles".equals(requestPath)) {
                    return true;
                }
                break;
                
            case "POST":
                if ("/books".equals(requestPath)) {
                    return userRole.isCanAddBooks();
                } else if ("/authors".equals(requestPath)) {
                    return userRole.isCanAddAuthors();
                } else if ("/allocations".equals(requestPath)) {
                    return userRole.isCanAddAllocations();
                } else if ("/users".equals(requestPath)) {
                    return userRole.isCanAddUsers();
                } 
                break;
                
            case "PUT":
                if ("/books".equals(requestPath)) {
                    return userRole.isCanUpdateBooks();
                } else if ("/authors".equals(requestPath)) {
                    return userRole.isCanUpdateAuthors();
                } else if ("/allocations".equals(requestPath)) {
                    return userRole.isCanUpdateAllocations();
                } else if ("/users".equals(requestPath)) {
                    return userRole.isCanUpdateUsers();
                }
                break;
                
            case "DELETE":
                if ("/books".equals(requestPath)) {
                    return userRole.isCanDeleteBooks();
                } else if ("/authors".equals(requestPath)) {
                    return userRole.isCanDeleteAuthors();
                } else if ("/users".equals(requestPath)) {
                    return userRole.isCanDeleteUsers();
                } 
                break;
        }
        
        return false;
    }
}
