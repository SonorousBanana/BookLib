import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignUpForm extends JFrame implements ActionListener {
    private JLabel usernameLabel, passwordLabel, adminLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox adminCheckBox;
    private JButton signUpButton;
    private JButton backButton;

    public SignUpForm() {
        super("Sign Up Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new GridLayout(4, 2));

        usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        usernameField = new JTextField();
        add(usernameField);

        passwordLabel = new JLabel("Password:");
        add(passwordLabel);

        passwordField = new JPasswordField();
        add(passwordField);

        adminLabel = new JLabel("Admin:");
        add(adminLabel);

        adminCheckBox = new JCheckBox();
        add(adminCheckBox);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        add(backButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(this);
        add(signUpButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        boolean isAdmin = adminCheckBox.isSelected();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "");
            Statement stmt = conn.createStatement();
            String query = "INSERT INTO users (username, password, isAdmin) VALUES ('" + username + "', '" + password + "', " + isAdmin + ")";
            stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(this, "Sign up successful!");
            conn.close();
            new PreHomePage();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    } else if (e.getSource() == backButton) {
        new PreHomePage();
        dispose();
    }
}

}