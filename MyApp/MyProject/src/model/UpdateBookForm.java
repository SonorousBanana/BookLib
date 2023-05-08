import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateBookForm extends JFrame implements ActionListener {
    private JLabel titleLabel, authorLabel, yearLabel;
    private JTextField titleField, authorField, yearField;
    private JButton updateButton;

    public UpdateBookForm() {
        super("Update Book Form");
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

        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        add(updateButton);

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
        String query = "UPDATE book SET author='" + author + "', year='" + year + "' WHERE title='" + title + "'";
        int rows = stmt.executeUpdate(query);
        if (rows > 0) {
            JOptionPane.showMessageDialog(this, "Book updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Book not found.");
        }
        conn.close();
        dispose();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
}
}