package model;

public class Role {
    private int id;
    private String name;
    private boolean canViewBooks;
    private boolean canAddBooks;
    private boolean canUpdateBooks;
    private boolean canDeleteBooks;
    private boolean canViewAuthors;
    private boolean canAddAuthors;
    private boolean canUpdateAuthors;
    private boolean canDeleteAuthors;
    private boolean canViewAllocations;
    private boolean canAddAllocations;
    private boolean canUpdateAllocations;
    private boolean canViewUsers;
    private boolean canAddUsers;
    private boolean canUpdateUsers;
    private boolean canDeleteUsers;

    public Role() {
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isCanViewBooks() {
        return canViewBooks;
    }
    
    public void setCanViewBooks(boolean canViewBooks) {
        this.canViewBooks = canViewBooks;
    }
    
    public boolean isCanAddBooks() {
        return canAddBooks;
    }
    
    public void setCanAddBooks(boolean canAddBooks) {
        this.canAddBooks = canAddBooks;
    }
    
    public boolean isCanUpdateBooks() {
        return canUpdateBooks;
    }
    
    public void setCanUpdateBooks(boolean canUpdateBooks) {
        this.canUpdateBooks = canUpdateBooks;
    }
    
    public boolean isCanDeleteBooks() {
        return canDeleteBooks;
    }
    
    public void setCanDeleteBooks(boolean canDeleteBooks) {
        this.canDeleteBooks = canDeleteBooks;
    }
    
    public boolean isCanViewAuthors() {
        return canViewAuthors;
    }
    
    public void setCanViewAuthors(boolean canViewAuthors) {
        this.canViewAuthors = canViewAuthors;
    }
    
    public boolean isCanAddAuthors() {
        return canAddAuthors;
    }
    
    public void setCanAddAuthors(boolean canAddAuthors) {
        this.canAddAuthors = canAddAuthors;
    }
    
    public boolean isCanUpdateAuthors() {
        return canUpdateAuthors;
    }
    
    public void setCanUpdateAuthors(boolean canUpdateAuthors) {
        this.canUpdateAuthors = canUpdateAuthors;
    }
    
    public boolean isCanDeleteAuthors() {
        return canDeleteAuthors;
    }
    
    public void setCanDeleteAuthors(boolean canDeleteAuthors) {
        this.canDeleteAuthors = canDeleteAuthors;
    }
    
    public boolean isCanViewAllocations() {
        return canViewAllocations;
    }
    
    public void setCanViewAllocations(boolean canViewAllocations) {
        this.canViewAllocations = canViewAllocations;
    }
    
    public boolean isCanAddAllocations() {
        return canAddAllocations;
    }
    
    public void setCanAddAllocations(boolean canAddAllocations) {
        this.canAddAllocations = canAddAllocations;
    }
    
    public boolean isCanUpdateAllocations() {
        return canUpdateAllocations;
    }
    
    public void setCanUpdateAllocations(boolean canUpdateAllocations) {
        this.canUpdateAllocations = canUpdateAllocations;
    }

    public boolean isCanViewUsers() {
        return canViewUsers;
    }
    
    public void setCanViewUsers(boolean canViewUsers) {
        this.canViewUsers = canViewUsers;
    }

    public boolean isCanAddUsers() {
        return canAddUsers;
    }
    
    public void setCanAddUsers(boolean canAddUsers) {
        this.canAddUsers = canAddUsers;
    }

    public boolean isCanUpdateUsers() {
        return canUpdateUsers;
    }
    
    public void setCanUpdateUsers(boolean canUpdateUsers) {
        this.canUpdateUsers = canUpdateUsers;
    }

    public boolean isCanDeleteUsers() {
        return canDeleteUsers;
    }
    
    public void setCanDeleteUsers(boolean canDeleteUsers) {
        this.canDeleteUsers = canDeleteUsers;
    }
    
}