/** Navil Hernandez
 * Software Development I
 * 202410-CEN-3024C-16046
 * 11-15-2023
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

/** This class sets the layout and functionality of the GUI
 * to be used to interface with the MySQL database
 */
public class LibraryManagementDatabase {
    private Connection connection;
    private JTextArea booksTextArea;

    public LibraryManagementDatabase(Connection connection) {
        this.connection = connection;
        createAndShowGUI();
    }

    /**
     * This method creates the frame, panel, buttons, text area
     * for the books to be displayed and the methods called by each button.
     * Its return type is void, takes no arguments and its name is createAndShowGUI.
     */
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        JButton removeByTitleButton = new JButton("Remove Book By Title");
        JButton removeByBarcodeButton = new JButton("Remove Book By Barcode");
        JButton checkOutButton = new JButton("Check Out");
        JButton checkInButton = new JButton("Check In");
        JButton listButton = new JButton("List All Books");

        JPanel actionPanel = new JPanel(new GridLayout(5, 2));
        actionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        actionPanel.add(listButton);
        actionPanel.add(removeByTitleButton);
        actionPanel.add(removeByBarcodeButton);
        actionPanel.add(checkOutButton);
        actionPanel.add(checkInButton);

        booksTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(booksTextArea);
        booksTextArea.setEditable(false);

        frame.add(actionPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.EAST);
        scrollPane.setPreferredSize(new Dimension(250, 200));


        removeByTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = JOptionPane.showInputDialog(frame, "Enter book title:");
                    int rowsAffected = removeBookByTitleFromDatabase(title);
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "Book removed successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    updateBooksTextAreaFromDatabase();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        removeByBarcodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String barcodeInput = JOptionPane.showInputDialog(frame, "Enter book barcode:");
                    int barcode = Integer.parseInt(barcodeInput);
                    int rowsAffected = removeBookByBarcodeFromDatabase(barcode);
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "Book removed successfully.");
                    }else {
                        JOptionPane.showMessageDialog(frame, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    updateBooksTextAreaFromDatabase();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid barcode format", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String barcodeInput = JOptionPane.showInputDialog(frame, "Enter book barcode:");
                    int barcode = Integer.parseInt(barcodeInput);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.WEEK_OF_YEAR, 4);
                    Date dueDate = new Date(calendar.getTimeInMillis());
                    int updateResult = updateBookStatusAndDueDate(barcode, "checked-out", dueDate);

                    if (updateResult == 2) {
                        JOptionPane.showMessageDialog(frame, "Book checked out successfully.");
                    } else if (updateResult == 0) {
                        JOptionPane.showMessageDialog(frame, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Book already checked out", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    updateBooksTextAreaFromDatabase();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid barcode format", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        checkInButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String barcodeInput = JOptionPane.showInputDialog(frame, "Enter book barcode:");
            int barcode = Integer.parseInt(barcodeInput);
            int rowsAffected = updateBookStatusAndDueDate(barcode, "checked-in", null);
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(frame, "Book checked in successfully.");
            } else{
                JOptionPane.showMessageDialog(frame, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
            updateBooksTextAreaFromDatabase();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid barcode. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBooksTextAreaFromDatabase();
                JOptionPane.showMessageDialog(frame, "Books listed from the database in GUI.");
            }
        });

        frame.setVisible(true);
    }

    /**
     * This method called updateBooksTextAreaFRomDatabase returns void, takes no arguments,
     * and is called after every button is pressed to display the books in the JTextArea.
     */
        private void updateBooksTextAreaFromDatabase() {

            try {
                String url = "jdbc:mysql://localhost:3306/LMS_database?useSSL=false&serverTimezone=UTC";
                String user = "root";
                String password = "LMS_database";
                try (Connection connection = DriverManager.getConnection(url, user, password)) {
                    try (Statement statement = connection.createStatement()) {
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
                        booksTextArea.setText("");
                        while (resultSet.next()) {
                            int barcode = resultSet.getInt("barcode");
                            String title = resultSet.getString("title");
                            String author = resultSet.getString("author");
                            String genre = resultSet.getString("genre");
                            String status = resultSet.getString("status");
                            Date dueDate = resultSet.getDate("due_date");
                            booksTextArea.append(
                                    "Barcode: " + barcode + "\n" +
                                            "Title: " + title + "\n" +
                                            "Author: " + author + "\n" +
                                            "Genre: " + genre + "\n" +
                                            "Status: " + status + "\n" +
                                            "Due Date: " + (dueDate != null ? dueDate.toString() : "N/A") + "\n\n"
                            );
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                booksTextArea.setText("Error fetching data from the database.");
            }
        }
        /** This method is called updateBooksStatusAndDueDate,
        it returns int, takes an int, String and Date as parameters, and
        it is used to check books in and out.
         **/
    private int updateBookStatusAndDueDate(int barcode, String status, Date dueDate) {
        int rowsAffected = 0;
        try {
            String url = "jdbc:mysql://localhost:3306/LMS_database?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "LMS_database";

            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                String updateQuery = "UPDATE books SET status = ?, due_date = ? WHERE barcode = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, status);
                    preparedStatement.setDate(2, dueDate);
                    preparedStatement.setInt(3, barcode);
                    rowsAffected = preparedStatement.executeUpdate();

                    if (status.equals("checked-out") && rowsAffected > 0) {
                        try (PreparedStatement selectStatement = connection.prepareStatement("SELECT status FROM books WHERE barcode = ?")) {
                            selectStatement.setInt(1, barcode);
                            ResultSet resultSet = selectStatement.executeQuery();
                            if (resultSet.next()) {
                                String existingStatus = resultSet.getString("status");
                                if (!existingStatus.equals("checked-out")) {
                                    return 2;
                                } else {
                                    return -1;
                                }
                            }
                        }
                    }

                    if (rowsAffected > 0) {
                        System.out.println("Book status and due date updated in the database.");
                    } else {
                        System.out.println("Book not found in the database.");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            booksTextArea.setText("Error updating book status and due date in the database.");
        }

        if (status.equals("checked-out") && rowsAffected == 0) {
            return 0;
        } else {
            return rowsAffected;
        }
    }

    /** This method is called removeBookByBarcodeFromDatabase, it returns
     * int, takes an int as a parameter, and it is called when the button
     * to remove a book by barcode number is pressed.
     */
    private int removeBookByBarcodeFromDatabase(int barcode) throws SQLException {
        String sql = "DELETE FROM books WHERE barcode = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, barcode);
            return pstmt.executeUpdate();
        }
    }
    /** This method is called removeBookByTitleFromDatabase, it returns
     * int, takes a String as a parameter, and it is called when the button
     * to remove a book by title is pressed.
     */
    private int removeBookByTitleFromDatabase(String title) throws SQLException {
        String sql = "DELETE FROM books WHERE title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            return pstmt.executeUpdate();
        }
    }

    /**
     * This connects to the database and launches the GUI
     */

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/LMS_database";
            String user = "root";
            String password = "LMS_database";
            Connection connection = DriverManager.getConnection(url, user, password);

            LibraryManagementDatabase gui = new LibraryManagementDatabase(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

