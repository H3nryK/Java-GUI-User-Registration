import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public login() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // Use GridLayout
        formPanel.setBorder(new TitledBorder("Login Form")); // Add a titled border

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (authenticate(usernameField.getText(), new String(passwordField.getPassword()))) {
                    openRegistrationForm();
                } else {
                    JOptionPane.showMessageDialog(login.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        formPanel.add(loginButton);

        panel.add(formPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    private boolean authenticate(String username, String password) {
        // Replace this with your authentication logic
        return username.equals("admin") && password.equals("admin123");
    }

    private void openRegistrationForm() {
        // Close the login form and open the registration form
        dispose(); // Close the login form
        register registerForm = new register();
        registerForm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new login());
    }
}
