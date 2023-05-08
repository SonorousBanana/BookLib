import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MyBorrowingsForm extends JFrame {
    private JPanel borrowingsPanel;

    public MyBorrowingsForm(String username) {
        super("My Borrowings Form");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(1, 2));

        borrowingsPanel = new JPanel();
        borrowingsPanel.setLayout(new BoxLayout(borrowingsPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(borrowingsPanel));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM borrowings WHERE username='" + username + "'");
            while (rs.next()) {
                String title = rs.getString("title");
                JPanel borrowingPanel = new JPanel();
                borrowingPanel.setLayout(new BoxLayout(borrowingPanel, BoxLayout.X_AXIS));
                JLabel borrowingLabel = new JLabel(title);
                borrowingPanel.add(borrowingLabel);
                JButton returnButton = new JButton("Return");
                returnButton.addActionListener(new ReturnBookActionListener(title, username));
                borrowingPanel.add(returnButton);
                borrowingsPanel.add(borrowingPanel);
            }
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }

        setVisible(true);
    }

    private class ReturnBookActionListener implements ActionListener {
        private String title, username;

        public ReturnBookActionListener(String title, String username) {
            this.title = title;
            this.username = username;
        }

        public void actionPerformed(ActionEvent e) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "");
                Statement stmt = conn.createStatement();
                String query = "DELETE FROM borrowings WHERE title='" + title + "' AND username='" + username + "'";
                int rows = stmt.executeUpdate(query);
                if (rows > 0) {
                    //JOptionPane.showMessageDialog(MyBorrowingsForm.this, "Book returned successfully!");
                    dispose();
                    new MyBorrowingsForm(username);
                } else {
                    JOptionPane.showMessageDialog(MyBorrowingsForm.this, "Book not found.");
                }
                conn.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(MyBorrowingsForm.this, "Error: " + ex.getMessage());
            }
        }
    }
}

