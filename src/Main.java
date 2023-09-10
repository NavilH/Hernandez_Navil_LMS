/** Navil Hernandez
 * Software Development I
 * 202410-CEN-3024C-16046
 */
public class Main {
    /** This starts the program by declaring a new instance of the
     * LMS class, which is what I chose to call the class for how
     * the Library Management System will work, the ConsoleManagement constructor
     * takes an LMS object as a parameter, then the first batch of books
     * are loaded from the text file before showing anything to the user
     */
    public static void main(String[] args) {
        LMS librarySystem = new LMS();
        ConsoleManagement consoleManagement = new ConsoleManagement(librarySystem);
        librarySystem.loadBooksFromFile("C:/Users/navil/.jdks/corretto-17.0.8.1/LMS/src/library.txt");
        consoleManagement.run();

    }
}