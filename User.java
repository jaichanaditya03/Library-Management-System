import java.util.ArrayList;
import java.util.List;


public class User {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private List<Integer> borrowedBooks; 
    private int maxBooksAllowed;
    
    
    public User(String userId, String name, String email, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.borrowedBooks = new ArrayList<>();
        this.maxBooksAllowed = 5; 
    }
    
    public User(String userId, String name, String email, String phoneNumber, int maxBooksAllowed) {
        this(userId, name, email, phoneNumber);
        this.maxBooksAllowed = maxBooksAllowed;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public List<Integer> getBorrowedBooks() {
        return new ArrayList<>(borrowedBooks); 
    }
    
    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }
    
    public int getBorrowedBooksCount() {
        return borrowedBooks.size();
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setMaxBooksAllowed(int maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }
    
    public boolean canBorrowMoreBooks() {
        return borrowedBooks.size() < maxBooksAllowed;
    }
    
    public boolean borrowBook(int bookId) {
        if (canBorrowMoreBooks() && !borrowedBooks.contains(bookId)) {
            borrowedBooks.add(bookId);
            return true;
        }
        return false;
    }
    
    public boolean returnBook(int bookId) {
        return borrowedBooks.remove(Integer.valueOf(bookId));
    }
    
    public boolean hasBorrowedBook(int bookId) {
        return borrowedBooks.contains(bookId);
    }
    
    public void displayUserInfo() {
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Books Borrowed: " + borrowedBooks.size() + "/" + maxBooksAllowed);
        if (!borrowedBooks.isEmpty()) {
            System.out.print("Borrowed Book IDs: ");
            for (int i = 0; i < borrowedBooks.size(); i++) {
                System.out.print(borrowedBooks.get(i));
                if (i < borrowedBooks.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        System.out.println("------------------------");
    }
    
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", borrowedBooks=" + borrowedBooks +
                ", maxBooksAllowed=" + maxBooksAllowed +
                '}';
    }
}