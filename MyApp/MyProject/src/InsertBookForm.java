import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InsertBookForm extends JFrame implements ActionListener {
    private JLabel titleLabel, authorLabel, yearLabel;
    private JTextField titleField, authorField, yearField;
    private JButton insertButton;

    public InsertBookForm() {
        super("Insert Book Form");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLayout(new GridLayout(4, 2));

        titleLabel = new JLabel("Title:");
        add(titleLabel);

        titleField = new JTextField();
        add(titleField);

        authorLabel = new JLabel("Author:");
        add(authorLabel);

        authorField = new JTextField();
        add(authorField);

        yearLabel = new JLabel("Year:");
        add(yearLabel);

        yearField = new JTextField();
        add(yearField);

        insertButton = new JButton("Insert");
        insertButton.addActionListener(this);
        add(insertButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String title = titleField.getText();
        String author = authorField.getText();
        String year = yearField.getText();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "");
            Statement stmt = conn.createStatement();
            String query = "INSERT INTO book (title, author, year) VALUES ('" + title + "', '" + author + "', '" + year + "')";
            stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(this, "Book inserted successfully!");
            conn.close();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

