/** Navil Hernandez
 * Software Development I
 * 202410-CEN-3024C-16046
 * 09-10-2023
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
  /** This class is for the functionality of the library management system**/
    public class LMS {
        private final List<Book> books = new ArrayList<>();


        /** The following method  named loadBooksFromFile splits a line of text from the file into the data for each
         * property of the Book object, its return type is void and it takes a String, the filePath
         * to the text file, as an argument
         */
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
        /** This method allows the user to add additional books once the ones from
         * the text file have been added, which is not explicitly required but could
         * be useful functionality, its named addUserInputBooks, its return value is
         * void and it takes a BufferedReader object as an argument*/
        public void addUserInputBooks(BufferedReader reader) throws IOException {
            int id = books.size() + 1;
            System.out.print("Enter Book Title: ");
            String title = reader.readLine();
            System.out.print("Enter Book Author: ");
            String author = reader.readLine();

            Book book = new Book(id, title, author);
            books.add(book);
            System.out.println("Book added successfully.");
        }

      /** This method is named removeBookById, it accepts an entered id, once it finds
       * a match, it deletes that book from the collection and calls the reassignIds method
       * Its return type is void and it takes a BufferedReader object as an argument
       */
        public void removeBookById(BufferedReader reader) throws IOException {
            System.out.print("Enter Book ID to remove: ");
            int id = Integer.parseInt(reader.readLine());
            books.removeIf(book -> book.getId() == id);
            System.out.println("Book with ID " + id + " removed.");
            reassignIds();
        }

      /** This method is named listAllBooks, it prints all the books of the collection
       * to the console, its return type is void, no arguments
       */
      public void listAllBooks () {
                if (books.isEmpty()) {
                    System.out.println("No books in the library.");
                } else {
                    System.out.println("Books in the library:");
                    for (Book book : books) {
                        System.out.println(book);
                    }
                }
            }

        /** This assigns the id of each book based on its position in the collection.
         * It is called when we remove a book so that, for example,  a first book of id 1,
         * the second book becomes id 1, making sure each id is unique and to be less
         * confusing to the user avoiding a case in which they can see an id 4 but no id 3.
         * It is named reassignIds, return type void, no arguments
         */
        public void reassignIds() {
            int idCounter = 1;
            for (Book book : books) {
                book.setId(idCounter++);
            }
        }
        }

