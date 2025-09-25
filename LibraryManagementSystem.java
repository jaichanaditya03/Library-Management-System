import java.util.Scanner;


public class LibraryManagementSystem {
    private static Library library;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        library = new Library("Central Public Library");
        scanner = new Scanner(System.in);
        
        initializeSampleData();
        
        System.out.println("=======================================================");
        System.out.println("    Welcome to " + library.getLibraryName() + "!");
        System.out.println("=======================================================");
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    manageBooks();
                    break;
                case 2:
                    manageUsers();
                    break;
                case 3:
                    issueBookMenu();
                    break;
                case 4:
                    returnBookMenu();
                    break;
                case 5:
                    displayReports();
                    break;
                case 6:
                    library.displayTransactionHistory();
                    break;
                case 7:
                    running = false;
                    System.out.println("Thank you for using the Library Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Manage Books");
        System.out.println("2. Manage Users");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. View Reports");
        System.out.println("6. Transaction History");
        System.out.println("7. Exit");
        System.out.println("=".repeat(50));
    }
    
    private static void manageBooks() {
        System.out.println("\n=== BOOK MANAGEMENT ===");
        System.out.println("1. Add New Book");
        System.out.println("2. Remove Book");
        System.out.println("3. Search Books by Title");
        System.out.println("4. Search Books by Author");
        System.out.println("5. Display All Books");
        System.out.println("6. Display Available Books");
        System.out.println("7. Display Borrowed Books");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addNewBook();
                break;
            case 2:
                removeBook();
                break;
            case 3:
                searchBooksByTitle();
                break;
            case 4:
                searchBooksByAuthor();
                break;
            case 5:
                library.displayAllBooks();
                break;
            case 6:
                library.displayAvailableBooks();
                break;
            case 7:
                library.displayBorrowedBooks();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void manageUsers() {
        System.out.println("\n=== USER MANAGEMENT ===");
        System.out.println("1. Add New User");
        System.out.println("2. Remove User");
        System.out.println("3. Display All Users");
        System.out.println("4. Display User's Borrowed Books");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addNewUser();
                break;
            case 2:
                removeUser();
                break;
            case 3:
                library.displayAllUsers();
                break;
            case 4:
                displayUserBorrowedBooks();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void addNewBook() {
        System.out.println("\n=== ADD NEW BOOK ===");
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        
        if (library.addBook(title, author, isbn)) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Failed to add book.");
        }
    }
    
    private static void removeBook() {
        System.out.println("\n=== REMOVE BOOK ===");
        int bookId = getIntInput("Enter book ID to remove: ");
        
        if (library.removeBook(bookId)) {
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Failed to remove book. Book may not exist or is currently borrowed.");
        }
    }
    
    private static void searchBooksByTitle() {
        System.out.println("\n=== SEARCH BOOKS BY TITLE ===");
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine();
        
        var books = library.findBooksByTitle(title);
        if (books.isEmpty()) {
            System.out.println("No books found with that title.");
        } else {
            System.out.println("Found " + books.size() + " book(s):");
            for (Book book : books) {
                book.displayBookInfo();
            }
        }
    }
    
    private static void searchBooksByAuthor() {
        System.out.println("\n=== SEARCH BOOKS BY AUTHOR ===");
        System.out.print("Enter author name to search: ");
        String author = scanner.nextLine();
        
        var books = library.findBooksByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("No books found by that author.");
        } else {
            System.out.println("Found " + books.size() + " book(s):");
            for (Book book : books) {
                book.displayBookInfo();
            }
        }
    }
    
    private static void addNewUser() {
        System.out.println("\n=== ADD NEW USER ===");
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        
        if (library.addUser(userId, name, email, phone)) {
            System.out.println("User added successfully!");
        } else {
            System.out.println("Failed to add user. User ID may already exist.");
        }
    }
    
    private static void removeUser() {
        System.out.println("\n=== REMOVE USER ===");
        System.out.print("Enter user ID to remove: ");
        String userId = scanner.nextLine();
        
        if (library.removeUser(userId)) {
            System.out.println("User removed successfully!");
        } else {
            System.out.println("Failed to remove user. User may not exist or has borrowed books.");
        }
    }
    
    private static void displayUserBorrowedBooks() {
        System.out.println("\n=== USER'S BORROWED BOOKS ===");
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        
        library.displayUserBorrowedBooks(userId);
    }
    
    private static void issueBookMenu() {
        System.out.println("\n=== ISSUE BOOK ===");
        int bookId = getIntInput("Enter book ID to issue: ");
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        
        library.issueBook(bookId, userId);
    }
    
    private static void returnBookMenu() {
        System.out.println("\n=== RETURN BOOK ===");
        int bookId = getIntInput("Enter book ID to return: ");
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        
        library.returnBook(bookId, userId);
    }
    
    private static void displayReports() {
        System.out.println("\n=== REPORTS ===");
        System.out.println("1. Library Statistics");
        System.out.println("2. All Books");
        System.out.println("3. Available Books");
        System.out.println("4. Borrowed Books");
        System.out.println("5. All Users");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                library.displayLibraryStats();
                break;
            case 2:
                library.displayAllBooks();
                break;
            case 3:
                library.displayAvailableBooks();
                break;
            case 4:
                library.displayBorrowedBooks();
                break;
            case 5:
                library.displayAllUsers();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void initializeSampleData() {
        System.out.println("Initializing sample data...");
        
        library.addBook("The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5");
        library.addBook("To Kill a Mockingbird", "Harper Lee", "978-0-06-112008-4");
        library.addBook("1984", "George Orwell", "978-0-452-28423-4");
        library.addBook("Pride and Prejudice", "Jane Austen", "978-0-14-143951-8");
        library.addBook("The Catcher in the Rye", "J.D. Salinger", "978-0-316-76948-0");
        library.addBook("Lord of the Flies", "William Golding", "978-0-571-05686-2");
        library.addBook("Animal Farm", "George Orwell", "978-0-452-28424-1");
        library.addBook("Brave New World", "Aldous Huxley", "978-0-06-085052-4");
        
        library.addUser("U001", "John Doe", "john.doe@email.com", "555-1234");
        library.addUser("U002", "Jane Smith", "jane.smith@email.com", "555-5678");
        library.addUser("U003", "Bob Johnson", "bob.johnson@email.com", "555-9012");
        library.addUser("U004", "Alice Brown", "alice.brown@email.com", "555-3456");
        
        library.issueBook(1, "U001"); 
        library.issueBook(3, "U002"); 
        library.issueBook(5, "U001"); 
        
        System.out.println("Sample data initialized successfully!\n");
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}