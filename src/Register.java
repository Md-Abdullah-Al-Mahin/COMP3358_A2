import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class Register extends JPanel {
    private GameClient ui;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton cancelButton;
    public Register(GameClient ui){
        this.ui = ui;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        // Username Field
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        // Password Field
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        // Confirm Password Label
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(confirmPasswordLabel, gbc);

        // Confirm Password Field
        confirmPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(confirmPasswordField, gbc);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.addActionListener(registerActionListener);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(cancelActionListener);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(cancelButton, gbc);
    }

    ActionListener cancelActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ui.navigate("Login");
        }
    };
    ActionListener registerActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            // Validate username and password
            if (username.isEmpty() || password.isEmpty()) {
                ui.error("Username and password fields cannot be empty!");
                return;
            }
            // Confirm password
            if (!password.equals(confirmPassword)) {
                ui.error("Passwords do not match!");
                return;
            }
            // Perform user registration
            boolean success = false;
            try {
                success = ui.game.registerAndLoginUser(username, 0, 0, 0.0, password);
            } catch (RemoteException ex) {
                ui.error("Error in registering user.");
            }
            if (success) {
                // Navigate to the profile page if registration is successful
                try {
                    ui.setUserData(ui.game.getUserData(username));
                    ui.navigate("Profile");
                } catch (RemoteException ex) {
                    ui.error("Error connecting to server!");
                }
            } else {
                // Display an error message if registration fails
                ui.error("Error registering user!");
            }
        }
    };
}
