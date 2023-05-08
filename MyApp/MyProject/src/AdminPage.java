import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminPage extends JFrame implements ActionListener {
    private JButton insertBookButton, viewBooksButton, logoutButton;

    public AdminPage() {
        super("Admin Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new GridLayout(5, 1));

        insertBookButton = new JButton("Insert Book");
        insertBookButton.addActionListener(this);
        add(insertBookButton);

        viewBooksButton = new JButton("View Books");
        viewBooksButton.addActionListener(this);
        add(viewBooksButton);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        add(logoutButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertBookButton) {
            new InsertBookForm();
        } else if (e.getSource() == viewBooksButton) {

           new ViewBooksForm(true, null);
           
        } else if (e.getSource() == logoutButton) {
            new PreHomePage();
            dispose();
        }
    }

}
