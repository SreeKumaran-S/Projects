package service;

import dao.AllocationDAO;
import dao.BookDAO;
import dao.UserDAO;
import model.Allocation;
import model.Book;
import model.User;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class AllocationService {
    
    private static final double FINE_PER_DAY = 2.0;
    private static final int BORROW_DAYS = 7;
    
    public Allocation borrowBook(Allocation allocation) throws Exception {
        if (allocation.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (allocation.getBookId() <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }
        
        
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(allocation.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getBookById(allocation.getBookId());
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        
        AllocationDAO allocationDAO = new AllocationDAO();
        
        if (allocationDAO.hasAnyActiveAllocation(allocation.getUserId())) {
            throw new IllegalArgumentException("User can only borrow one book at a time. Please return the current book first.");
        }
        
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalArgumentException("Book is not available for borrowing");
        }
        
       
        
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        allocation.setAllocatedOn(currentTime);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.DAY_OF_MONTH, BORROW_DAYS);
        Timestamp dueDate = new Timestamp(calendar.getTimeInMillis());
        allocation.setDueOn(dueDate);
        
        allocationDAO.borrowBook(allocation);
        
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookDAO.updateBook(book);
        
        return allocation;
    }
    
    public Allocation returnBook(Allocation allocation) throws Exception {
        if (allocation.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (allocation.getId() <= 0) {
            throw new IllegalArgumentException("Invalid allocation ID");
        }
        
       
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(allocation.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        AllocationDAO allocationDAO = new AllocationDAO();
        Allocation existingAllocation = allocationDAO.getAllocationById(allocation.getId());
        if (existingAllocation == null) {
            throw new IllegalArgumentException("Allocation not found");
        }

        if (existingAllocation.getUserId() != allocation.getUserId()) {
            throw new IllegalArgumentException("This allocation does not belong to the current user");
        }
        
        if (existingAllocation.getReturnedOn() != null) {
            throw new IllegalArgumentException("Book has already been returned");
        }
        
       
        
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        double fineAmount = calculateFine(existingAllocation.getDueOn(), currentTime);
        
        allocationDAO.returnBook(allocation.getId(), currentTime, fineAmount);
        
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getBookById(existingAllocation.getBookId());
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookDAO.updateBook(book);
        
        allocation.setReturnedOn(currentTime);
        allocation.setFineAmount(fineAmount);
        
        return allocation;
    }
    
   
   
    
    public List<Allocation> getAllAllocationsPaginated(int startIndex, int limit) throws Exception {
        if (startIndex < 0 || limit <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }
        AllocationDAO allocationDAO = new AllocationDAO();
        return allocationDAO.getAllAllocationsPaginated(startIndex, limit);
    }
    
    public Allocation getAllocationById(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid allocation ID");
        }
        
        AllocationDAO allocationDAO = new AllocationDAO();
        Allocation allocation = allocationDAO.getAllocationById(id);
        if (allocation == null) {
            throw new IllegalArgumentException("Allocation not found");
        }
        
        return allocation;
    }
    
    private double calculateFine(Timestamp dueOn, Timestamp returnedOn) {
        if (returnedOn.before(dueOn) || returnedOn.equals(dueOn)) {
            return 0.0;
        }
        
        long diffInMilliseconds = returnedOn.getTime() - dueOn.getTime();
        long diffInDays = diffInMilliseconds / (24 * 60 * 60 * 1000);
        
        return diffInDays * FINE_PER_DAY;
    }
} 