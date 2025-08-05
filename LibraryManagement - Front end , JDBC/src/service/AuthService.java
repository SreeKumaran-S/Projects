package service;

import dao.UserDAO;
import model.User;

public class AuthService {
    
    public LoginResponse login(String email, String password) throws Exception {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail(email);
        
        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        
        if (!password.equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        
        return new LoginResponse(user, "Login successful");
    }
    
    public static class LoginResponse {
        public User user;
        public String message;
        
        public LoginResponse(User user, String message) {
            this.user = user;
            this.message = message;
        }
    }
} 