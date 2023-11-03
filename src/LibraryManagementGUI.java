/** Navil Hernandez
 * Software Development I
 * 202410-CEN-3024C-16046
 * 11-02-2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/** This class sets the layout and functionality of the GUI
 * by creating a LMS object and calling the createAndShowGUI method
 */
public class LibraryManagementGUI {
    private LMS librarySystem;
    private JTextArea booksTextArea;

    public LibraryManagementGUI(LMS librarySystem) {
        this.librarySystem = librarySystem;
        createAndShowGUI();
    }

    /** This method creates the frame, panel, buttons, text area
     * for the books to be displayed and the methods called by each button.
     * Its return type is void, takes no arguments and its name is createAndShowGUI.
     */
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        JButton loadFromFileButton = new JButton("Add Text File");
        JButton removeByTitleButton = new JButton("Remove Book By Title");
        JButton removeByIdButton = new JButton("Remove Book By ID");
        JButton checkOutButton = new JButton("Check Out");
        JButton checkInButton = new JButton("Check In");
        JButton listButton = new JButton("List All Books");

        JPanel actionPanel = new JPanel(new GridLayout(3, 2));
        actionPanel.add(loadFromFileButton);
        actionPanel.add(listButton);
        actionPanel.add(removeByTitleButton);
        actionPanel.add(removeByIdButton);
        actionPanel.add(checkOutButton);
        actionPanel.add(checkInButton);

        booksTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(booksTextArea);
        booksTextArea.setEditable(false);

        frame.add(actionPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);
        scrollPane.setPreferredSize(new Dimension(200,200));

        loadFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog(frame, "Enter file name:");
                librarySystem.loadBooksFromFile(fileName);
            }
        });

        removeByTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = JOptionPane.showInputDialog(frame, "Enter book title:");
                    boolean bookExists = librarySystem.books.stream().anyMatch(book -> book.getTitle().equals(title));

                    if (bookExists) {
                        librarySystem.removeBookByTitle(new BufferedReader(new StringReader(title)));
                        JOptionPane.showMessageDialog(frame, "Book removed successfully.");
                        updateBooksTextArea(); // Update the JTextArea after removing the book
                    } else {
                        JOptionPane.showMessageDialog(frame, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        removeByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter barcode number"));
                    boolean bookExists = librarySystem.books.stream().anyMatch(book -> book.getId() == id);
                    if (bookExists) {
                        librarySystem.removeBookById(id);
                        JOptionPane.showMessageDialog(frame, "Book removed successfully.");
                        updateBooksTextArea();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = JOptionPane.showInputDialog(frame, "Enter book title:");
                    boolean bookExists = librarySystem.books.stream().anyMatch(book -> book.getTitle().equals(title));
                    if (bookExists) {
                        librarySystem.checkOutBook(new BufferedReader(new StringReader(title)));
                        JOptionPane.showMessageDialog(frame, "Book checked out successfully.");
                        updateBooksTextArea();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = JOptionPane.showInputDialog(frame, "Enter book title:");
                    boolean bookExists = librarySystem.books.stream().anyMatch(book -> book.getTitle().equals(title));
                    if (bookExists) {
                        librarySystem.checkInBook(new BufferedReader(new StringReader(title)));
                        JOptionPane.showMessageDialog(frame, "Book checked in successfully.");
                        updateBooksTextArea();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBooksTextArea();
                JOptionPane.showMessageDialog(frame, "Books listed in GUI.");
            }
        });


        frame.setVisible(true);
    }

    /** This method called updateBooksTextArea returns void, takes no arguments,
     * and is called after every button is pressed to display the books in the JTextArea.
     */
    private void updateBooksTextArea() {
        booksTextArea.setText("");
        for (Book book : librarySystem.books) {
            booksTextArea.append(book.toString() + "\n");
        }
    }

    /**This launches the GUI
     */

    public static void main(String[] args) {
        LMS librarySystem = new LMS();
        LibraryManagementGUI gui = new LibraryManagementGUI(librarySystem);
    }
}
