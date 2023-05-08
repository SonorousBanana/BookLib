import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AuthenticationForm extends JFrame implements ActionListener {
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, backButton;

    public AuthenticationForm() {
        super("Authentication Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new GridLayout(3, 2));

        usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        usernameField = new JTextField();
        add(usernameField);

        passwordLabel = new JLabel("Password:");
        add(passwordLabel);

        passwordField = new JPasswordField();
        add(passwordField);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        add(backButton);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        add(loginButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String user = "root";
        String passwd = "";
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'");
            if (rs.next()) {
                //JOptionPane.showMessageDialog(this, "Login successful!");

                boolean isAdmin = rs.getBoolean("isAdmin");
                if (isAdmin) {
                    new AdminPage();
                } else {
                    new HomePage(username);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    } else if (e.getSource() == backButton) {
        new PreHomePage();
        dispose();
    }
}
}
