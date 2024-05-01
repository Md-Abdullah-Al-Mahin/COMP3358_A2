import javax.swing.*;

public class PlayGame extends JPanel {
    private GameClient ui;
    public PlayGame(GameClient ui) {
        this.ui = ui;
        JLabel label = new JLabel("To be implemented");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }
}
