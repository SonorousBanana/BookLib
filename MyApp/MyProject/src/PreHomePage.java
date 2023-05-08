import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PreHomePage extends JFrame implements ActionListener {
    private JButton registerButton, loginButton;
    private JLabel titleLabel;

    public PreHomePage() {
        super("Pre-Home Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new GridLayout(5, 5));

        titleLabel = new JLabel("Welcome to the Bookstore Log In/Sign Up Page!");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        add(registerButton);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        add(loginButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            new SignUpForm();
            dispose();
        } else if (e.getSource() == loginButton) {
            new AuthenticationForm();
            dispose();
        }
    }
}