import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame {
    private JLabel welcomeLabel;
    private String username;


    public HomePage(String username) {
        super("Home Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new GridLayout(5, 1));
        this.username = username;

        welcomeLabel = new JLabel("Welcome, " + username + "!");
        add(welcomeLabel, BorderLayout.NORTH);

        JButton viewBooksButton = new JButton("View Books");
        viewBooksButton.addActionListener(new ViewBooksActionListener());
        add(viewBooksButton, BorderLayout.CENTER);

        
        JButton myBorrowingsButton = new JButton("View My Borrowings");
        myBorrowingsButton.addActionListener(new MyBorrowingsActionListener());
        add(myBorrowingsButton, BorderLayout.SOUTH);

        JButton backToPreHomePageButton = new JButton("Log Out");
        backToPreHomePageButton.addActionListener(new BackToPreHomePageActionListener());
        add(backToPreHomePageButton, BorderLayout.WEST);

        setVisible(true);
    }

    private class ViewBooksActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new ViewBooksForm(false, username);
    }
    }
    private class MyBorrowingsActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new MyBorrowingsForm(username);
        }
    }

    private class BackToPreHomePageActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            new PreHomePage();
        }
    }
}


