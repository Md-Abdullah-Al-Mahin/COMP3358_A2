import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class Login extends JPanel {
    private GameClient ui;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    public Login(GameClient ui){
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

        // Login Button
        loginButton = new JButton("Login");
        loginButton.addActionListener(loginActionListener);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.addActionListener(registerActionListener);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(registerButton, gbc);
    }

    ActionListener registerActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ui.navigate("Register") ;
        }
    };
    ActionListener loginActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                ui.error("Username and password fields cannot be empty!");
                return;
            }
            boolean authenticated = false;
            try {
                authenticated = ui.game.authenticateUser(username, password);
            } catch (RemoteException ex) {
                ui.error("Error in logging in.");
            }
            if (authenticated) {
                try {
                    ui.setUserData(ui.game.getUserData(username));
                    ui.navigate("Profile");
                } catch (RemoteException ex) {
                    ui.error("Error connecting to server!");
                }
            } else {
                ui.error("Invalid Username or Password!");
            }
        }
    };
}
