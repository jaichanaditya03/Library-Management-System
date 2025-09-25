
public class Book {
    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;
    private String borrowedBy; 
    

    public Book(int bookId, String title, String author, String isbn) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true; 
        this.borrowedBy = null;
    }
    
    public int getBookId() {
        return bookId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public String getBorrowedBy() {
        return borrowedBy;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public boolean issueBook(String userId) {
        if (isAvailable) {
            isAvailable = false;
            borrowedBy = userId;
            return true;
        }
        return false;
    }
    
    public boolean returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            borrowedBy = null;
            return true;
        }
        return false;
    }
    
    public void displayBookInfo() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + isbn);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
        if (!isAvailable) {
            System.out.println("Borrowed by: " + borrowedBy);
        }
        System.out.println("------------------------");
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", isAvailable=" + isAvailable +
                ", borrowedBy='" + borrowedBy + '\'' +
                '}';
    }
}