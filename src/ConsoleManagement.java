import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class ConsoleManagement {
    private LMS librarySystem;

    public ConsoleManagement(LMS librarySystem) {
        this.librarySystem = librarySystem;
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. List All Books");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                int choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:
                        librarySystem.addUserInputBooks(reader);
                        break;
                    case 2:
                        librarySystem.removeBookById(reader);
                        break;
                    case 3:
                        librarySystem.listAllBooks();
                        break;
                    case 4:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        librarySystem.saveBooksToFile("library.txt");

    }
}
