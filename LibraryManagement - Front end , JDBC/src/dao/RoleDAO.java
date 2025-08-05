package dao;

import db.DBConnection;
import model.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT * FROM roles ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    roles.add(createRoleFromResultSet(rs));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return roles;
    }
    
    public Role getRoleById(int id) {
        String query = "SELECT * FROM roles WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createRoleFromResultSet(rs);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    
    private Role createRoleFromResultSet(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setId(rs.getInt("id"));
        role.setName(rs.getString("name"));
        role.setCanViewBooks(rs.getBoolean("canViewBooks"));
        role.setCanAddBooks(rs.getBoolean("canAddBooks"));
        role.setCanUpdateBooks(rs.getBoolean("canUpdateBooks"));
        role.setCanDeleteBooks(rs.getBoolean("canDeleteBooks"));
        role.setCanViewAuthors(rs.getBoolean("canViewAuthors"));
        role.setCanAddAuthors(rs.getBoolean("canAddAuthors"));
        role.setCanUpdateAuthors(rs.getBoolean("canUpdateAuthors"));
        role.setCanDeleteAuthors(rs.getBoolean("canDeleteAuthors"));
        role.setCanViewAllocations(rs.getBoolean("canViewAllocations"));
        role.setCanAddAllocations(rs.getBoolean("canAddAllocations"));
        role.setCanUpdateAllocations(rs.getBoolean("canUpdateAllocations"));
        role.setCanViewUsers(rs.getBoolean("canViewUsers"));
        role.setCanAddUsers(rs.getBoolean("canAddUsers"));
        role.setCanUpdateUsers(rs.getBoolean("canUpdateUsers"));
        role.setCanDeleteUsers(rs.getBoolean("canDeleteUsers"));
        
        return role;
    }
}