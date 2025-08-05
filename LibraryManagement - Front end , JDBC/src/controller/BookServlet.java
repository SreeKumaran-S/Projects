package controller;

import model.Book;
import service.BookService;
import utils.ResponseUtil;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class BookServlet extends HttpServlet {

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
            Book book;
            try {
                book = gson.fromJson(jsonString, Book.class);
            } catch (Exception e) {
                ResponseUtil.badRequest(resp, "Invalid JSON format: " + e.getMessage());
                return;
            }
            
            if (book == null) {
                ResponseUtil.badRequest(resp, "Failed to parse book data");
                return;
            }

            BookService bookService = new BookService();
            bookService.addBook(book);
            ResponseUtil.sendSuccess(resp, "Book added successfully");
            
        } catch (NumberFormatException e) {
            ResponseUtil.badRequest(resp, "Invalid number format in input");
        } catch (IllegalArgumentException e) {
            ResponseUtil.badRequest(resp, e.getMessage());
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error adding book: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                int bookId = Integer.parseInt(pathInfo.substring(1));
                BookService bookService = new BookService();
                Book book = bookService.getBookById(bookId);
                ResponseUtil.sendSuccess(resp, book);
            } catch (NumberFormatException e) {
                ResponseUtil.badRequest(resp, "Invalid book ID format");
            } catch (IllegalArgumentException e) {
                ResponseUtil.badRequest(resp, e.getMessage());
            } catch (Exception e) {
                ResponseUtil.serverError(resp, "Error fetching book");
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

                BookService bookService = new BookService();
                List<Book> books = bookService.getBooksPaginated(startIndex, limit);

                ResponseUtil.sendSuccess(resp, books);
            } catch (NumberFormatException e) {
                ResponseUtil.badRequest(resp, "Invalid pagination parameters");
            } catch (IllegalArgumentException e) {
                ResponseUtil.badRequest(resp, e.getMessage());
            } catch (Exception e) {
                ResponseUtil.serverError(resp, "Error fetching books");
            }
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.length() <= 1) {
            ResponseUtil.badRequest(resp, "Book ID is required");
            return;
        }
        
        try {
            int bookId = Integer.parseInt(pathInfo.substring(1));
            
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
            Book book;
            try {
                book = gson.fromJson(jsonString, Book.class);
            } catch (Exception e) {
                ResponseUtil.badRequest(resp, "Invalid JSON format: " + e.getMessage());
                return;
            }
            
            if (book == null) {
                ResponseUtil.badRequest(resp, "Failed to parse book data");
                return;
            }
            
            book.setId(bookId);
            
            BookService bookService = new BookService();
            bookService.updateBook(book);
            ResponseUtil.sendSuccess(resp, "Book updated successfully");
            
        } catch (NumberFormatException e) {
            ResponseUtil.badRequest(resp, "Invalid book ID format");
        } catch (IllegalArgumentException e) {
            ResponseUtil.badRequest(resp, e.getMessage());
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error updating book: " + e.getMessage());
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.length() <= 1) {
            ResponseUtil.badRequest(resp, "Book ID is required");
            return;
        }
        
        try {
            int bookId = Integer.parseInt(pathInfo.substring(1));
            
            BookService bookService = new BookService();
            bookService.deleteBook(bookId);
            ResponseUtil.sendSuccess(resp, "Book deleted successfully");
            
        } catch (NumberFormatException e) {
            ResponseUtil.badRequest(resp, "Invalid book ID format");
        } catch (IllegalArgumentException e) {
            ResponseUtil.badRequest(resp, e.getMessage());
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error deleting book: " + e.getMessage());
        }
    }
}
