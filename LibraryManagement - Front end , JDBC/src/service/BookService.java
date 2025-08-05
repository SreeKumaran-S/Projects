package service;

import dao.BookDAO;
import dao.AuthorDAO;
import model.Book;

import java.util.List;

public class BookService {
    public List<Book> getBooksPaginated(int startIndex, int limit) {
        if (startIndex < 0 || limit <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }
        BookDAO bookDAO = new BookDAO();
        return bookDAO.getBooksPaginated(startIndex, limit);
    }

    public void addBook(Book book) throws Exception {
        if (book.getName() == null || book.getName().isEmpty()) {
            throw new IllegalArgumentException("Book name is required");
        }
        if (book.getAuthorId() <= 0) {
            throw new IllegalArgumentException("Valid authorId is required");
        }
        String isbnStr = String.valueOf(book.getIsbn());
        if (isbnStr.length() != 13) {
            throw new IllegalArgumentException("ISBN must be a 13-digit number");
        }
        if (book.getAvailableCopies() < 0 || book.getTotalCopies() <= 0) {
            throw new IllegalArgumentException("Invalid copies count");
        }
        if (book.getAvailableCopies() > book.getTotalCopies()) {
            throw new IllegalArgumentException("Available copies cannot exceed total copies");
        }
        AuthorDAO authorDAO = new AuthorDAO();
        if (!authorDAO.existsById(book.getAuthorId())) {
            throw new IllegalArgumentException("Author does not exist");
        }
        BookDAO bookDAO = new BookDAO();
        if (bookDAO.isIsbnExists(book.getIsbn())) {
            throw new IllegalArgumentException("ISBN already exists");
        }
        bookDAO.addBook(book);
    }

    public Book getBookById(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getBookById(id);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        return book;
    }

    public void updateBook(Book book) throws Exception {
        if (book.getId() <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }
        if (book.getName() == null || book.getName().isEmpty()) {
            throw new IllegalArgumentException("Book name is required");
        }
        if (book.getAuthorId() <= 0) {
            throw new IllegalArgumentException("Valid authorId is required");
        }
        String isbnStr = String.valueOf(book.getIsbn());
        if (isbnStr.length() != 13) {
            throw new IllegalArgumentException("ISBN must be a 13-digit number");
        }
        if (book.getAvailableCopies() < 0 || book.getTotalCopies() <= 0) {
            throw new IllegalArgumentException("Invalid copies count");
        }
        if (book.getAvailableCopies() > book.getTotalCopies()) {
            throw new IllegalArgumentException("Available copies cannot exceed total copies");
        }
        
        BookDAO bookDAO = new BookDAO();
        AuthorDAO authorDAO = new AuthorDAO();
        
        if (!authorDAO.existsById(book.getAuthorId())) {
            throw new IllegalArgumentException("Author does not exist");
        }
        
        Book existingBook = bookDAO.getBookById(book.getId());
        if (existingBook == null) {
            throw new IllegalArgumentException("Book not found");
        }
        
        if (book.getIsbn() != existingBook.getIsbn() && bookDAO.isIsbnExists(book.getIsbn())) {
            throw new IllegalArgumentException("ISBN already exists");
        }
        
        bookDAO.updateBook(book);
    }

    public void deleteBook(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }
        
        BookDAO bookDAO = new BookDAO();
        Book existingBook = bookDAO.getBookById(id);
        if (existingBook == null) {
            throw new IllegalArgumentException("Book not found");
        }
        
        bookDAO.deleteBook(id);
    }
}
