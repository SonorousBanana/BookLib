import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.awt.print.Book;
import java.sql.*;

public class ViewBooksForm extends JFrame {
    private JPanel booksPanel;
    //private JTable booksTable;
    //private String username;
    //private Book[] books;

    public ViewBooksForm(boolean isAdmin, String username) {
        super("View Books Form");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 300);

        booksPanel = new JPanel();
        booksPanel.setLayout(new BoxLayout(booksPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(booksPanel));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM book");
            //int count = 0;
            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                String year = rs.getString("year");
                boolean isBorrowed = isBookBorrowed(title);
                JPanel bookPanel = new JPanel();
                bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.X_AXIS));
                JLabel bookLabel = new JLabel(title + " by " + author + " (" + year + ")");
                bookPanel.add(bookLabel);
                //Book book = new Book(title, author, year);
                //books[count] = book;
                //count++;
                if (isBorrowed) {
                    if (isAdmin) {
                        JLabel borrowedLabel = new JLabel(" (Borrowed)");
                        bookPanel.add(borrowedLabel);

                        JButton returnButton = new JButton("Return");
                        returnButton.addActionListener(new ReturnBookActionListener1(title, username));
                        bookPanel.add(returnButton);

                        JButton deleteButton = new JButton("Delete");
                        deleteButton.addActionListener(new DeleteBookActionListener(title));
                        bookPanel.add(deleteButton);
                        JButton updateButton = new JButton("Update");
                        updateButton.addActionListener(new UpdateBookActionListener(title, author, year));
                        bookPanel.add(updateButton);
                    }
                    JLabel borrowedLabel = new JLabel(" (Borrowed)");
                    bookPanel.add(borrowedLabel);

                    /*JButton returnButton = new JButton("Return");
                    returnButton.addActionListener(new ReturnBookActionListener1(title, username));
                    bookPanel.add(returnButton);*/
                } else {
                    if (isAdmin) {

                        JButton deleteButton = new JButton("Delete");
                        deleteButton.addActionListener(new DeleteBookActionListener(title));
                        bookPanel.add(deleteButton);
                        JButton updateButton = new JButton("Update");
                        updateButton.addActionListener(new UpdateBookActionListener(title, author, year));
                        bookPanel.add(updateButton);
                    } else{
                    
                        JButton borrowButton = new JButton("Borrow");
                        borrowButton.addActionListener(new BorrowBookActionListener(title, username));
                        bookPanel.add(borrowButton);
                    }
                    
                }
                booksPanel.add(bookPanel);
            }
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
        /*String[] columnNames = {"Title", "Author", "Year", "Borrowed"};
        Object[][] data = new Object[books.length][4];
        for (int i = 0; i < books.length; i++) {
            data[i][0] = books[i].getTitle();
            data[i][1] = books[i].getAuthor();
            data[i][2] = books[i].getYear();
        }
        booksTable = new JTable(data, columnNames);
        add(new JScrollPane(booksTable));*/

        setVisible(true);
    }

    private class DeleteBookActionListener implements ActionListener {
        private String title;

        public DeleteBookActionListener(String title) {
            this.title = title;
        }

        public void actionPerformed(ActionEvent e) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "");
                Statement stmt = conn.createStatement();
                String query = "DELETE FROM book WHERE title='" + title + "'";
                int rows = stmt.executeUpdate(query);
                if (rows > 0) {
                    JOptionPane.showMessageDialog(ViewBooksForm.this, "Book deleted successfully!");
                    dispose();
                    new ViewBooksForm(true, null);
                } else {
                    JOptionPane.showMessageDialog(ViewBooksForm.this, "Book not found.");
                }
                conn.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ViewBooksForm.this, "Error: " + ex.getMessage());
            }
        }
    }

    private class UpdateBookActionListener implements ActionListener {
        private String title, author, year;

        private JLabel titleLabel, authorLabel, yearLabel;
        private JTextField titleField, authorField, yearField;
        private JButton updateButton;

        public UpdateBookActionListener(String title, String author, String year) {
            this.title = title;
            this.author = author;
            this.year = year;
        }

        public void actionPerformed(ActionEvent e) {
            JFrame updateBookFrame = new JFrame("Update Book Form");
            updateBookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            updateBookFrame.setSize(300, 150);
            updateBookFrame.setLayout(new GridLayout(4, 2));

            titleLabel = new JLabel("Title:");
            updateBookFrame.add(titleLabel);

            titleField = new JTextField(title);
            updateBookFrame.add(titleField);

            authorLabel = new JLabel("Author:");
            updateBookFrame.add(authorLabel);

            authorField = new JTextField(author);
            updateBookFrame.add(authorField);

            yearLabel = new JLabel("Year:");
            updateBookFrame.add(yearLabel);

            yearField = new JTextField(year);
            updateBookFrame.add(yearField);

            updateButton = new JButton("Update");
            updateButton.addActionListener(new UpdateBookFormActionListener());
            updateBookFrame.add(updateButton);

            updateBookFrame.setVisible(true);
        }

        private class UpdateBookFormActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String newTitle = titleField.getText();
                String newAuthor = authorField.getText();
                String newYear = yearField.getText();
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "");
                    Statement stmt = conn.createStatement();
                    String query = "UPDATE book SET title='" + newTitle + "', author='" + newAuthor + "', year='" + newYear + "' WHERE title='" + title + "'";
                    int rows = stmt.executeUpdate(query);
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(ViewBooksForm.this, "Book updated successfully!");
                        dispose();
                        new ViewBooksForm(true, null);
                    } else {
                        JOptionPane.showMessageDialog(ViewBooksForm.this, "Book not found.");
                    }
                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ViewBooksForm.this, "Error: " + ex.getMessage());
                }
            }
        }
    }
    private class BorrowBookActionListener implements ActionListener {
        private String title, username;

    
        public BorrowBookActionListener(String title, String username) {
            this.title = title;
            this.username = username;

        }
    
        public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "");
                    Statement stmt = conn.createStatement();
                    String query = "INSERT INTO borrowings (username, title) VALUES ('" + username + "', '" + title + "')";
                    int rows = stmt.executeUpdate(query);
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(ViewBooksForm.this, "Book borrowed successfully!");
                        dispose();
                        new ViewBooksForm(false, username);
                    } else {
                        JOptionPane.showMessageDialog(ViewBooksForm.this, "Error borrowing book.");
                    }
                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ViewBooksForm.this, "Error: " + ex.getMessage());
                }
            }
        }

        private boolean isBookBorrowed(String title) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM borrowings WHERE title='" + title + "'");
                boolean isBorrowed = rs.next();
                conn.close();
                return isBorrowed;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                return false;
            }
        }

        private class ReturnBookActionListener1 implements ActionListener {
            private String title;
            private String username;
    
            public ReturnBookActionListener1(String title, String username) {
                this.title = title;
                this.username = username;
            }
    
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "");
                    Statement stmt = conn.createStatement();
                    String query = "DELETE FROM borrowings WHERE title='" + title + "'";
                    int rows = stmt.executeUpdate(query);
                    if (rows > 0) {
                        //JOptionPane.showMessageDialog(MyBorrowingsForm.this, "Book returned successfully!");
                        dispose();
                        new ViewBooksForm(true, username);
                    } else {
                        JOptionPane.showMessageDialog(ViewBooksForm.this, "Book not found.");
                    }
                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ViewBooksForm.this, "Error: " + ex.getMessage());
                }
            }
        }
    }
    
    

