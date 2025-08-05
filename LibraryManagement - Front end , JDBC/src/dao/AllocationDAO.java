package dao;

import db.DBConnection;
import model.Allocation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AllocationDAO {
    
    public void borrowBook(Allocation allocation) {
        String query = "INSERT INTO allocations (user_id, book_id, allocated_on, due_on) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, allocation.getUserId());
            stmt.setInt(2, allocation.getBookId());
            stmt.setTimestamp(3, allocation.getAllocatedOn());
            stmt.setTimestamp(4, allocation.getDueOn());
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void returnBook(int allocationId, Timestamp returnedOn, double fineAmount) {
        String query = "UPDATE allocations SET returned_on = ?, fine_amount = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, returnedOn);
            stmt.setDouble(2, fineAmount);
            stmt.setInt(3, allocationId);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Allocation not found");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Allocation getAllocationById(int id) {
        String query = "SELECT * FROM allocations WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createAllocationFromResultSet(rs);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    
    
    public List<Allocation> getAllAllocationsPaginated(int startIndex, int limit) {
        List<Allocation> allocations = new ArrayList<>();
        String query = "SELECT * FROM allocations LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, limit);
            ps.setInt(2, startIndex);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    allocations.add(createAllocationFromResultSet(rs));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return allocations;
    }
    

    
    public boolean hasAnyActiveAllocation(int userId) {
        String query = "SELECT COUNT(*) FROM allocations WHERE user_id = ? AND returned_on IS NULL";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private Allocation createAllocationFromResultSet(ResultSet rs) throws SQLException {
        Allocation allocation = new Allocation();
        allocation.setId(rs.getInt("id"));
        allocation.setUserId(rs.getInt("user_id"));
        allocation.setBookId(rs.getInt("book_id"));
        allocation.setAllocatedOn(rs.getTimestamp("allocated_on"));
        allocation.setDueOn(rs.getTimestamp("due_on"));
        allocation.setReturnedOn(rs.getTimestamp("returned_on"));
        allocation.setFineAmount(rs.getDouble("fine_amount"));
        return allocation;
    }
} 