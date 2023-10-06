/** Navil Hernandez
 * Software Development I
 * 202410-CEN-3024C-16046
 * 10-06-2023
 */

import java.time.LocalDate;
import java.util.Objects;

/**This class defines the properties of the Book objects contained
 * in the collection
 */
class Book {
    private int id;
    private final String title;
    private final String author;

    private String status = "checked in";

    private LocalDate dueDate = null;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    /** This is the only property with a setter since it is the only
     * one that can change
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    /** This setter is essential for the checking out function because
     * it also prints the error message and takes care of assigning the due date
     */

    public void setStatus() {
        if (Objects.equals(this.status, "checked in")) {
            this.status = "checked out";
            LocalDate today = LocalDate.now();
            this.dueDate = today.plusWeeks(4);
            System.out.println(this.title + " has been checked out. It is due on " + this.dueDate);
        }
        else {
            System.err.println("That book was already checked out.");
        }
    }

    /**
     * This one is for checking in books, I decided it should also have an
     * error message if the book is already checked in
     */
    public void setStatus2() {
        if (Objects.equals(this.status, "checked out")) {
            this.status = "checked in";
            this.dueDate = null;
            System.out.println(this.title + " has been checked in.");
        }
        else {
            System.err.println("That book has already been checked in.");
        }
    }

    /**
     * Had to slightly modify the toString method to account for the new properties
     * and a new line for readability
     */
    @Override
    public String toString() {
        return  id + "," + title + "," + author + "," + status + "," + " due date: " + dueDate + "\n";
    }
}