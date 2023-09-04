import java.io.*;
import java.util.ArrayList;
import java.util.List;
    public class LMS {
        private List<Book> books = new ArrayList<>();

        public void loadBooksFromFile(String filePath) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        int id = Integer.parseInt(parts[0]);
                        String title = parts[1];
                        String author = parts[2];
                        books.add(new Book(id, title, author));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void saveBooksToFile(String filePath) {
            try (PrintWriter writer = new PrintWriter(filePath)) {
                for (Book book : books) {
                    writer.println(book.getId() + "," + book.getTitle() + "," + book.getAuthor());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        public void addUserInputBooks(BufferedReader reader) throws IOException {
            System.out.print("Enter Book ID: ");
            int id = Integer.parseInt(reader.readLine());
            System.out.print("Enter Book Title: ");
            String title = reader.readLine();
            System.out.print("Enter Book Author: ");
            String author = reader.readLine();

            Book book = new Book(id, title, author);
            books.add(book);
            System.out.println("Book added successfully.");
        }
        public void removeBookById(BufferedReader reader) throws IOException {
            System.out.print("Enter Book ID to remove: ");
            int id = Integer.parseInt(reader.readLine());
            books.removeIf(book -> book.getId() == id);
            System.out.println("Book with ID " + id + " removed.");
        }
        public void listAllBooks() {
            if (books.isEmpty()) {
                System.out.println("No books in the library.");
            } else {
                System.out.println("Books in the library:");
                for (Book book : books) {
                    System.out.println(book);
                }
            }
        }

    }