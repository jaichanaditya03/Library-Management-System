import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Library {
    private String libraryName;
    private Map<Integer, Book> books;
    private Map<String, User> users; 
    private List<String> transactionHistory;
    private int nextBookId;
    
    public Library(String libraryName) {
        this.libraryName = libraryName;
        this.books = new HashMap<>();
        this.users = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
        this.nextBookId = 1;
    }
    
    public boolean addBook(String title, String author, String isbn) {
        Book newBook = new Book(nextBookId, title, author, isbn);
        books.put(nextBookId, newBook);
        logTransaction("Added new book: " + title + " (ID: " + nextBookId + ")");
        nextBookId++;
        return true;
    }
    
    public boolean addBook(Book book) {
        if (!books.containsKey(book.getBookId())) {
            books.put(book.getBookId(), book);
            logTransaction("Added book: " + book.getTitle() + " (ID: " + book.getBookId() + ")");
            if (book.getBookId() >= nextBookId) {
                nextBookId = book.getBookId() + 1;
            }
            return true;
        }
        return false;
    }
    
    public boolean removeBook(int bookId) {
        Book book = books.get(bookId);
        if (book != null && book.isAvailable()) {
            books.remove(bookId);
            logTransaction("Removed book: " + book.getTitle() + " (ID: " + bookId + ")");
            return true;
        }
        return false; 
    }
    
    public Book findBookById(int bookId) {
        return books.get(bookId);
    }
    
    public List<Book> findBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
    
    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
    
    public boolean addUser(String userId, String name, String email, String phoneNumber) {
        if (!users.containsKey(userId)) {
            User newUser = new User(userId, name, email, phoneNumber);
            users.put(userId, newUser);
            logTransaction("Added new user: " + name + " (ID: " + userId + ")");
            return true;
        }
        return false; 
    }
    
    public boolean addUser(User user) {
        if (!users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
            logTransaction("Added user: " + user.getName() + " (ID: " + user.getUserId() + ")");
            return true;
        }
        return false;
    }
    
    public boolean removeUser(String userId) {
        User user = users.get(userId);
        if (user != null && user.getBorrowedBooksCount() == 0) {
            users.remove(userId);
            logTransaction("Removed user: " + user.getName() + " (ID: " + userId + ")");
            return true;
        }
        return false; 
    }
    
    public User findUserById(String userId) {
        return users.get(userId);
    }
    
    public boolean issueBook(int bookId, String userId) {
        Book book = books.get(bookId);
        User user = users.get(userId);
        
        if (book == null) {
            System.out.println("Error: Book with ID " + bookId + " not found.");
            return false;
        }
        
        if (user == null) {
            System.out.println("Error: User with ID " + userId + " not found.");
            return false;
        }
        
        if (!book.isAvailable()) {
            System.out.println("Error: Book '" + book.getTitle() + "' is already borrowed.");
            return false;
        }
        
        if (!user.canBorrowMoreBooks()) {
            System.out.println("Error: User " + user.getName() + " has reached the maximum book limit.");
            return false;
        }
        
        // Issue the book
        if (book.issueBook(userId) && user.borrowBook(bookId)) {
            logTransaction("Book issued: '" + book.getTitle() + "' to " + user.getName());
            System.out.println("Book '" + book.getTitle() + "' successfully issued to " + user.getName());
            return true;
        }
        
        return false;
    }
    
    public boolean returnBook(int bookId, String userId) {
        Book book = books.get(bookId);
        User user = users.get(userId);
        
        if (book == null) {
            System.out.println("Error: Book with ID " + bookId + " not found.");
            return false;
        }
        
        if (user == null) {
            System.out.println("Error: User with ID " + userId + " not found.");
            return false;
        }
        
        if (book.isAvailable()) {
            System.out.println("Error: Book '" + book.getTitle() + "' is not currently borrowed.");
            return false;
        }
        
        if (!userId.equals(book.getBorrowedBy())) {
            System.out.println("Error: Book '" + book.getTitle() + "' was not borrowed by " + user.getName());
            return false;
        }
        
        // Return the book
        if (book.returnBook() && user.returnBook(bookId)) {
            logTransaction("Book returned: '" + book.getTitle() + "' by " + user.getName());
            System.out.println("Book '" + book.getTitle() + "' successfully returned by " + user.getName());
            return true;
        }
        
        return false;
    }
    
    public void displayAllBooks() {
        System.out.println("=== ALL BOOKS IN " + libraryName.toUpperCase() + " ===");
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            for (Book book : books.values()) {
                book.displayBookInfo();
            }
        }
        System.out.println("Total books: " + books.size());
        System.out.println();
    }
    
    public void displayAvailableBooks() {
        System.out.println("=== AVAILABLE BOOKS ===");
        int availableCount = 0;
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                book.displayBookInfo();
                availableCount++;
            }
        }
        System.out.println("Available books: " + availableCount);
        System.out.println();
    }
    
    public void displayBorrowedBooks() {
        System.out.println("=== BORROWED BOOKS ===");
        int borrowedCount = 0;
        for (Book book : books.values()) {
            if (!book.isAvailable()) {
                book.displayBookInfo();
                borrowedCount++;
            }
        }
        System.out.println("Borrowed books: " + borrowedCount);
        System.out.println();
    }
    
    public void displayAllUsers() {
        System.out.println("=== ALL USERS ===");
        if (users.isEmpty()) {
            System.out.println("No users registered in the library.");
        } else {
            for (User user : users.values()) {
                user.displayUserInfo();
            }
        }
        System.out.println("Total users: " + users.size());
        System.out.println();
    }
    
    public void displayUserBorrowedBooks(String userId) {
        User user = users.get(userId);
        if (user == null) {
            System.out.println("User with ID " + userId + " not found.");
            return;
        }
        
        System.out.println("=== BOOKS BORROWED BY " + user.getName().toUpperCase() + " ===");
        List<Integer> borrowedBookIds = user.getBorrowedBooks();
        if (borrowedBookIds.isEmpty()) {
            System.out.println("No books currently borrowed.");
        } else {
            for (int bookId : borrowedBookIds) {
                Book book = books.get(bookId);
                if (book != null) {
                    book.displayBookInfo();
                }
            }
        }
        System.out.println();
    }
    
    public void displayLibraryStats() {
        System.out.println("=== " + libraryName.toUpperCase() + " STATISTICS ===");
        System.out.println("Total Books: " + books.size());
        
        int availableBooks = 0;
        int borrowedBooks = 0;
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                availableBooks++;
            } else {
                borrowedBooks++;
            }
        }
        
        System.out.println("Available Books: " + availableBooks);
        System.out.println("Borrowed Books: " + borrowedBooks);
        System.out.println("Total Users: " + users.size());
        System.out.println("Total Transactions: " + transactionHistory.size());
        System.out.println();
    }
    
    public void displayTransactionHistory() {
        System.out.println("=== TRANSACTION HISTORY ===");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
        System.out.println();
    }
    
    private void logTransaction(String transaction) {
        String timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        transactionHistory.add("[" + timestamp + "] " + transaction);
    }
    
    public String getLibraryName() {
        return libraryName;
    }
    
    public int getTotalBooks() {
        return books.size();
    }
    
    public int getTotalUsers() {
        return users.size();
    }
}