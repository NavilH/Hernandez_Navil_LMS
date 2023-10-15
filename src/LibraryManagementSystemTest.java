
import org.junit.Test;



import java.io.BufferedReader;
import  java.io.StringReader;
import java.io.IOException;
import java.time.LocalDate;


import static org.junit.Assert.*;

public class LibraryManagementSystemTest {
    @Test
    public void libraryManagementSystemTest() throws IOException {
        /**
         * Add Books Test Case
         */
        System.out.println("add books test case");
        LMS librarySystem = new LMS();
        librarySystem.loadBooksFromFile("src/library.txt");
        assertEquals(3, librarySystem.books.size());
        Book firstBook = librarySystem.books.get(0);
        assertEquals(1, firstBook.getId());
        assertEquals("To Kill a Mockingbird", firstBook.getTitle());
        assertEquals("Harper Lee", firstBook.getAuthor());
        System.out.println("passed test");
        /**
         * Remove Books By Barcode Number or Title Test Case
         */
        System.out.println("remove books by barcode number test case");
        String input = "2\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));
        librarySystem.removeBookById(reader);
        assertEquals(2,librarySystem.books.size());
        System.out.println("passed test");
        System.out.println("remove books by author test case");
        input = "The Great Gatsby\n";
        BufferedReader reader2 = new BufferedReader(new StringReader(input));
        librarySystem.removeBookByTitle(reader2);
        assertEquals(1, librarySystem.books.size());
        System.out.println("passed test");
        /**
         * Check Out Books Test Case
         */
        System.out.println("check out books test case");
        input = "To Kill a Mockingbird\n";
        BufferedReader reader3 = new BufferedReader(new StringReader(input));
        LocalDate checkedOutDate = LocalDate.now().plusWeeks(4);
        librarySystem.checkOutBook(reader3);
        Book lastBook = librarySystem.books.get(0);
        assertEquals("checked out", lastBook.getStatus());
        assertEquals(checkedOutDate, lastBook.getDueDate());
        System.out.println("passed test");
        /**
         * Check In Books Test Case
         */
        System.out.println("check in books test case");
        BufferedReader reader4 = new BufferedReader(new StringReader(input));
        librarySystem.checkInBook(reader4);
        lastBook = librarySystem.books.get(0);
        assertEquals("checked in", lastBook.getStatus());
        assertNull(null, lastBook.getDueDate());
        System.out.println("passed test");
    }
}
