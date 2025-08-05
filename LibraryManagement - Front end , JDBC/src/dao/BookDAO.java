package dao;

import db.DBConnection;
import model.Book;

import java.util.*;
import java.sql.*;

public class BookDAO {
    public boolean isIsbnExists(long isbn) throws Exception {
        String sql = "SELECT 1 FROM books WHERE isbn = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<Book> getBooksPaginated(int startIndex, int limit) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, limit);
            ps.setInt(2, startIndex);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setName(rs.getString("name"));
                    book.setAuthorId(rs.getInt("author_id"));
                    book.setIsbn(rs.getLong("isbn"));
                    book.setGenre(rs.getString("genre"));
                    book.setAvailableCopies(rs.getInt("available_copies"));
                    book.setTotalCopies(rs.getInt("total_copies"));
                    book.setCreatedAt(rs.getTimestamp("created_at"));
                    books.add(book);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public void addBook(Book book) {
        String query = "INSERT INTO books (name, author_id, isbn, genre, available_copies, total_copies) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getName());
            stmt.setInt(2, book.getAuthorId());
            stmt.setLong(3, book.getIsbn());
            stmt.setString(4, book.getGenre());
            stmt.setInt(5, book.getAvailableCopies());
            stmt.setInt(6, book.getTotalCopies());
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Book getBookById(int id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setName(rs.getString("name"));
                    book.setAuthorId(rs.getInt("author_id"));
                    book.setIsbn(rs.getLong("isbn"));
                    book.setGenre(rs.getString("genre"));
                    book.setAvailableCopies(rs.getInt("available_copies"));
                    book.setTotalCopies(rs.getInt("total_copies"));
                    book.setCreatedAt(rs.getTimestamp("created_at"));
                    return book;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateBook(Book book) {
        String query = "UPDATE books SET name = ?, author_id = ?, isbn = ?, genre = ?, available_copies = ?, total_copies = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getName());
            stmt.setInt(2, book.getAuthorId());
            stmt.setLong(3, book.getIsbn());
            stmt.setString(4, book.getGenre());
            stmt.setInt(5, book.getAvailableCopies());
            stmt.setInt(6, book.getTotalCopies());
            stmt.setInt(7, book.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Book not found");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(int id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Book not found");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
