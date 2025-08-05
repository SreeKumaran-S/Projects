package service;

import dao.AuthorDAO;
import model.Author;
import java.util.List;
import java.util.regex.Pattern;

public class AuthorService {
    
    public List<Author> getAuthorsPaginated(int startIndex, int limit) {
        if (startIndex < 0 || limit <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }
        AuthorDAO authorDAO = new AuthorDAO();
        return authorDAO.getAuthorsPaginated(startIndex, limit);
    }

    public Author getAuthorById(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid author ID");
        }
        AuthorDAO authorDAO = new AuthorDAO();
        Author author = authorDAO.getAuthorById(id);
        if (author == null) {
            throw new IllegalArgumentException("Author not found");
        }
        return author;
    }

    public void addAuthor(Author author) throws Exception {
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Author name is required");
        }
        if (author.getEmail() == null || author.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Author email is required");
        }
        if (!isValidEmail(author.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        AuthorDAO authorDAO = new AuthorDAO();
        if (authorDAO.isEmailExists(author.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        authorDAO.addAuthor(author);
    }

    public void updateAuthor(Author author) throws Exception {
        if (author.getId() <= 0) {
            throw new IllegalArgumentException("Invalid author ID");
        }
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Author name is required");
        }
        if (author.getEmail() == null || author.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Author email is required");
        }
        if (!isValidEmail(author.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        AuthorDAO authorDAO = new AuthorDAO();
        Author existingAuthor = authorDAO.getAuthorById(author.getId());
        if (existingAuthor == null) {
            throw new IllegalArgumentException("Author not found");
        }
        
        if (!author.getEmail().equals(existingAuthor.getEmail()) && authorDAO.isEmailExists(author.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        authorDAO.updateAuthor(author);
    }

    public void deleteAuthor(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid author ID");
        }
        
        AuthorDAO authorDAO = new AuthorDAO();
        Author existingAuthor = authorDAO.getAuthorById(id);
        if (existingAuthor == null) {
            throw new IllegalArgumentException("Author not found");
        }
        
        authorDAO.deleteAuthor(id);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
} 