/** Navil Hernandez
 * Software Development I
 * 202410-CEN-3024C-16046
 * 10-06-2023
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.util.Scanner;
/** This class structures how the console will interact with the user **/
public class ConsoleManagement {
    /**
     * Declared an LMS object to use the methods of that class
     */
    private final LMS librarySystem;
    public static String filePath = "";

    public ConsoleManagement(LMS librarySystem) {
        this.librarySystem = librarySystem;
    }

    /**
     * This method named run starts the program in the main method, its return type is void,
     * it takes no arguments
     */
    public void run() {
        boolean running = true;
        boolean fileExists = false;
        /*
         * These conditional statements check if the user entered the file path correctly
         * before proceeding
         */
        while (running) {
            System.out.println("\nLibrary Management System");
            if (!fileExists) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter a file path:");
                filePath = scanner.nextLine();
                File file = new File(filePath);

                if (!file.exists()) {
                    System.out.println("File does not exist. Please check the path.");
                } else {
                    fileExists = true;
                    librarySystem.loadBooksFromFile(ConsoleManagement.filePath);
                }
            }
            if (fileExists) {
                System.out.println("Printing database...");
                librarySystem.listAllBooks();
                System.out.println("1. Add Book");
                System.out.println("2. Remove Book By Barcode Number");
                System.out.println("3. Remove Book By Title");
                System.out.println("4. Check Out Book By Title");
                System.out.println("5. Check In Book By Title");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    int choice = Integer.parseInt(reader.readLine());
                    /*
                     * Added 3 new cases for the new functionality (removing by barcode number,
                     * checking in and checking out)
                     */
                    switch (choice) {
                        case 1 -> librarySystem.addUserInputBooks(reader);
                        case 2 -> librarySystem.removeBookById(reader);
                        case 3 -> librarySystem.removeBookByTitle(reader);
                        case 4 -> librarySystem.checkOutBook(reader);
                        case 5 -> librarySystem.checkInBook(reader);
                        case 6 -> running = false;
                        default -> System.out.println("Invalid choice. Please try again.");
                    }
                } catch (IOException | NumberFormatException e) {
                    if (e instanceof NumberFormatException) {
                        System.err.println("Enter a valid number.");
                    } else {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
