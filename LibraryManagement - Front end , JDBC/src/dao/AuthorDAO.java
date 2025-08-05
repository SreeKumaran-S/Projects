package dao;

import db.DBConnection;
import model.Author;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
    public boolean existsById(int authorId) throws Exception {
        String sql = "SELECT 1 FROM authors WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, authorId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<Author> getAuthorsPaginated(int startIndex, int limit) {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM authors LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, limit);
            ps.setInt(2, startIndex);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Author author = new Author();
                    author.setId(rs.getInt("id"));
                    author.setName(rs.getString("name"));
                    author.setEmail(rs.getString("email"));
                    author.setCreatedAt(rs.getTimestamp("created_at"));
                    authors.add(author);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }

    public Author getAuthorById(int id) {
        String query = "SELECT * FROM authors WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Author author = new Author();
                    author.setId(rs.getInt("id"));
                    author.setName(rs.getString("name"));
                    author.setEmail(rs.getString("email"));
                    author.setCreatedAt(rs.getTimestamp("created_at"));
                    return author;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addAuthor(Author author) {
        String query = "INSERT INTO authors (name, email) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, author.getName());
            stmt.setString(2, author.getEmail());
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAuthor(Author author) {
        String query = "UPDATE authors SET name = ?, email = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, author.getName());
            stmt.setString(2, author.getEmail());
            stmt.setInt(3, author.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Author not found");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAuthor(int id) {
        String query = "DELETE FROM authors WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Author not found");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEmailExists(String email) throws Exception {
        String sql = "SELECT 1 FROM authors WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
