package controller;

import model.Author;
import service.AuthorService;
import utils.ResponseUtil;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class AuthorServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            
            String jsonString = sb.toString().trim();
            if (jsonString.isEmpty()) {
                ResponseUtil.badRequest(resp, "Request body is empty");
                return;
            }
            
            Gson gson = new Gson();
            Author author;
            try {
                author = gson.fromJson(jsonString, Author.class);
            } catch (Exception e) {
                ResponseUtil.badRequest(resp, "Invalid JSON format: " + e.getMessage());
                return;
            }
            
            if (author == null) {
                ResponseUtil.badRequest(resp, "Failed to parse author data");
                return;
            }

            AuthorService authorService = new AuthorService();
            authorService.addAuthor(author);
            ResponseUtil.sendSuccess(resp, "Author added successfully");
            
        } catch (NumberFormatException e) {
            ResponseUtil.badRequest(resp, "Invalid number format in input");
        } catch (IllegalArgumentException e) {
            ResponseUtil.badRequest(resp, e.getMessage());
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error adding author: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                int authorId = Integer.parseInt(pathInfo.substring(1));
                AuthorService authorService = new AuthorService();
                Author author = authorService.getAuthorById(authorId);
                ResponseUtil.sendSuccess(resp, author);
            } catch (NumberFormatException e) {
                ResponseUtil.badRequest(resp, "Invalid author ID format");
            } catch (IllegalArgumentException e) {
                ResponseUtil.badRequest(resp, e.getMessage());
            } catch (Exception e) {
                ResponseUtil.serverError(resp, "Error fetching author");
            }
        } else {
            String startIndexParam = req.getParameter("startIndex");
            String limitParam = req.getParameter("limit");
            int startIndex = 0;
            int limit = 10;

            try {
                if (startIndexParam != null)
                    startIndex = Integer.parseInt(startIndexParam);
                if (limitParam != null)
                    limit = Integer.parseInt(limitParam);

                AuthorService authorService = new AuthorService();
                List<Author> authors = authorService.getAuthorsPaginated(startIndex, limit);

                ResponseUtil.sendSuccess(resp, authors);
            } catch (NumberFormatException e) {
                ResponseUtil.badRequest(resp, "Invalid pagination parameters");
            } catch (IllegalArgumentException e) {
                ResponseUtil.badRequest(resp, e.getMessage());
            } catch (Exception e) {
                ResponseUtil.serverError(resp, "Error fetching authors");
            }
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.length() <= 1) {
            ResponseUtil.badRequest(resp, "Author ID is required");
            return;
        }
        
        try {
            int authorId = Integer.parseInt(pathInfo.substring(1));
            
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            
            String jsonString = sb.toString().trim();
            if (jsonString.isEmpty()) {
                ResponseUtil.badRequest(resp, "Request body is empty");
                return;
            }
            
            Gson gson = new Gson();
            Author author;
            try {
                author = gson.fromJson(jsonString, Author.class);
            } catch (Exception e) {
                ResponseUtil.badRequest(resp, "Invalid JSON format: " + e.getMessage());
                return;
            }
            
            if (author == null) {
                ResponseUtil.badRequest(resp, "Failed to parse author data");
                return;
            }
            
            author.setId(authorId);
            
            AuthorService authorService = new AuthorService();
            authorService.updateAuthor(author);
            ResponseUtil.sendSuccess(resp, "Author updated successfully");
            
        } catch (NumberFormatException e) {
            ResponseUtil.badRequest(resp, "Invalid author ID format");
        } catch (IllegalArgumentException e) {
            ResponseUtil.badRequest(resp, e.getMessage());
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error updating author: " + e.getMessage());
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.length() <= 1) {
            ResponseUtil.badRequest(resp, "Author ID is required");
            return;
        }
        
        try {
            int authorId = Integer.parseInt(pathInfo.substring(1));
            
            AuthorService authorService = new AuthorService();
            authorService.deleteAuthor(authorId);
            ResponseUtil.sendSuccess(resp, "Author deleted successfully");
            
        } catch (NumberFormatException e) {
            ResponseUtil.badRequest(resp, "Invalid author ID format");
        } catch (IllegalArgumentException e) {
            ResponseUtil.badRequest(resp, e.getMessage());
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error deleting author: " + e.getMessage());
        }
    }
} 