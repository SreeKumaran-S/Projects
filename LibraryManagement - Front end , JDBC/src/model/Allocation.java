package model;

import java.sql.Timestamp;

public class Allocation {
    private int id;
    private int userId;
    private int bookId;
    private Timestamp allocatedOn;
    private Timestamp dueOn;
    private Timestamp returnedOn;
    private double fineAmount;

    public Allocation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Timestamp getAllocatedOn() {
        return allocatedOn;
    }

    public void setAllocatedOn(Timestamp allocatedOn) {
        this.allocatedOn = allocatedOn;
    }

    public Timestamp getDueOn() {
        return dueOn;
    }

    public void setDueOn(Timestamp dueOn) {
        this.dueOn = dueOn;
    }

    public Timestamp getReturnedOn() {
        return returnedOn;
    }

    public void setReturnedOn(Timestamp returnedOn) {
        this.returnedOn = returnedOn;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }
} 