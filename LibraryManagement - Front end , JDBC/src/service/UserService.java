package service;

import dao.UserDAO;
import model.User;

import java.util.List;
import java.util.regex.Pattern;

public class UserService {
    
    public List<User> getUsersPaginated(int startIndex, int limit) throws Exception {
        if (startIndex < 0 || limit <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }
        UserDAO userDAO = new UserDAO();
        return userDAO.getUsersPaginated(startIndex, limit);
    }

    public void addUser(User user) throws Exception {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("User name is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (user.getRoleId() <= 0) {
            throw new IllegalArgumentException("Valid role ID is required");
        }
        
        UserDAO userDAO = new UserDAO();
        if (userDAO.isEmailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        userDAO.addUser(user);
    }

    public User getUserById(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }

    public void updateUser(User user) throws Exception {
        if (user.getId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("User name is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (user.getRoleId() <= 0) {
            throw new IllegalArgumentException("Valid role ID is required");
        }
        
        UserDAO userDAO = new UserDAO();
        
        User existingUser = userDAO.getUserById(user.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        
       
        if (!existingUser.getEmail().equals(user.getEmail()) && userDAO.isEmailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        userDAO.updateUser(user);
    }

    public void deleteUser(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        UserDAO userDAO = new UserDAO();
        User existingUser = userDAO.getUserById(id);
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        userDAO.deleteUser(id);
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
} 