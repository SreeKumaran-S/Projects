package service;

import dao.RoleDAO;
import model.Role;
import java.util.List;

public class RoleService {
    
    public List<Role> getAllRoles() throws Exception {
        RoleDAO roleDAO = new RoleDAO();
        return roleDAO.getAllRoles();
    }
    
    public Role getRoleById(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid role ID");
        }
        
        RoleDAO roleDAO = new RoleDAO();
        Role role = roleDAO.getRoleById(id);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        return role;
    }
}