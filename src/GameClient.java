import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;

public class GameClient {
    RemoteServerInterface game;
    private final JFrame frame;
    private Login LoginPanel = new Login(this);
    private Register RegisterPanel;
    private PlayGame PlayGamePanel;
    private MainContainer MainPanel;
    private ProfilePage ProfilePanel;
    private LeaderBoard LeaderPanel;
    private User user;

    public static void main(String[] args) {
        new GameClient(args[0]);
    }
    public GameClient(String host) {
        this.frame = new JFrame("JPoker 24-Game");
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            this.game = (RemoteServerInterface) registry.lookup("GameServer");
            int width = 500;
            int height = 500;
            this.frame.setSize(width, height);
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.getContentPane().add(LoginPanel);
            this.frame.pack();
            this.frame.setVisible(true);
            this.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // Perform cleanup actions here
                    int choice = JOptionPane.showConfirmDialog(frame,
                            "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        try {
                            game.logoutUser((user != null) ? user.getName() : null);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                        frame.dispose();
                    }
                }
            });
        } catch (Exception ex) {
            this.frame.dispose();
            System.err.println("Failed to access the remote server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void navigate(String to) {
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().revalidate();
        this.frame.repaint();
        if (Objects.equals(to, "Login")) {
            this.LoginPanel = new Login(this);
            this.frame.getContentPane().add(LoginPanel);
        } else if (Objects.equals(to, "Register")) {
            this.RegisterPanel = new Register(this);
            this.frame.getContentPane().add(RegisterPanel);
        } else if (Objects.equals(to, "Play")) {
            this.PlayGamePanel = new PlayGame(this);
            this.MainPanel = new MainContainer(this, this.PlayGamePanel);
            this.frame.getContentPane().add(this.MainPanel);
        } else if (Objects.equals(to, "Profile")) {
            try{
                this.user = this.game.getUserData(user.getName());
                this.ProfilePanel = new ProfilePage(this, user.getName(), user.getRank(), user.getGamesWon(), user.getGamesPlayed(), user.getAvgTimeToGame());
                this.MainPanel = new MainContainer(this, this.ProfilePanel);
                this.frame.getContentPane().add(this.MainPanel);
            } catch (RemoteException e) {
                this.error("Error connecting to server!");
            }
        } else if (Objects.equals(to, "Leader")) {
            try{
                this.LeaderPanel = new LeaderBoard(this.game.getAllUserData());
                this.MainPanel = new MainContainer(this, this.LeaderPanel);
                this.frame.getContentPane().add(this.MainPanel);
            } catch (RemoteException e) {
                this.error("Error connecting to server!");
            }
        }
        this.frame.pack();
    }

    public void error(String errorMessage){
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setUserData(User user) {
        this.user = user;
    }

    public User getUserData() {
        return this.user;
    }

}
