import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class MainContainer extends JPanel {
    private GameClient ui;
    private JPanel navPanel;
    private JPanel pagesPanel;

    public MainContainer(GameClient ui, JPanel content){
        this.ui = ui;

        setLayout(new BorderLayout());

        // Navigation panel with buttons stacked horizontally
        navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton button1 = new JButton("User Profile");
        button1.addActionListener(profileActionListener);
        JButton button2 = new JButton("Play Game");
        button2.addActionListener(playActionListener);
        JButton button3 = new JButton("Leader Board");
        button3.addActionListener(leaderActionListener);
        JButton button4 = new JButton("Logout");
        button4.addActionListener(logoutActionListener);
        navPanel.add(button1);
        navPanel.add(button2);
        navPanel.add(button3);
        navPanel.add(button4);

        pagesPanel = new JPanel();
        pagesPanel.add(content);

        // Add panels to JFrame
        add(navPanel, BorderLayout.NORTH);
        add(pagesPanel, BorderLayout.CENTER);
    }

    ActionListener logoutActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ui.game.logoutUser(ui.getUserData().getName());
                ui.setUserData(null);
            } catch (RemoteException ex) {
                ui.error("Error connecting to server!");
            }
            ui.navigate("Login");
        }
    };

    ActionListener playActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ui.navigate("Play");
        }
    };

    ActionListener profileActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ui.navigate("Profile");
        }
    };
    ActionListener leaderActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ui.navigate("Leader");
        }
    };
}
