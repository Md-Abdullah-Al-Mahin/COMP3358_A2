import javax.swing.*;
import java.awt.*;

public class ProfilePage extends JPanel {
    private JTextField nameField, winsField, gamesField, avgTimeField, rankField;
    GameClient ui;

    public ProfilePage(GameClient ui, String name, int rank, int wins, int games, double avgTime) {
        this.ui = ui;

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField(name);
        nameField.setEditable(false);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Number of Wins:"));
        winsField = new JTextField(String.valueOf(wins));
        winsField.setEditable(false);
        inputPanel.add(winsField);

        inputPanel.add(new JLabel("Number of Games:"));
        gamesField = new JTextField(String.valueOf(games));
        gamesField.setEditable(false);
        inputPanel.add(gamesField);

        inputPanel.add(new JLabel("Average Time to Win (seconds):"));
        avgTimeField = new JTextField(String.valueOf(avgTime));
        avgTimeField.setEditable(false);
        inputPanel.add(avgTimeField);

        inputPanel.add(new JLabel("Rank:"));
        rankField = new JTextField(String.valueOf(rank));
        rankField.setEditable(false);
        inputPanel.add(rankField);

        add(inputPanel, BorderLayout.CENTER);
    }
}