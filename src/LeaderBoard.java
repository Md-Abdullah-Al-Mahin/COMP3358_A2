import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderBoard extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public LeaderBoard(ArrayList<User> data) {
        setLayout(new BorderLayout());

        // Create a DefaultTableModel with data and column names
        model = new DefaultTableModel();
        model.addColumn("Rank");
        model.addColumn("Name");
        model.addColumn("Wins");
        model.addColumn("Games");
        model.addColumn("Avg Time to Game (mins)");

        data.sort(Comparator.comparing(User::getRank, Comparator.naturalOrder()));

        // Add data to the model
        for (int i = 0; i < data.size(); i++) {
            ArrayList<Object> rowData = new ArrayList<Object>();
            rowData.add(data.get(i).getRank());
            rowData.add(data.get(i).getName());
            rowData.add(data.get(i).getGamesWon());
            rowData.add(data.get(i).getGamesPlayed());
            rowData.add(data.get(i).getAvgTimeToGame());
            model.addRow(rowData.toArray());
        }

        // Create a JTable with the DefaultTableModel
        table = new JTable(model);

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50); // Rank column width
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Name column width
        table.getColumnModel().getColumn(2).setPreferredWidth(80); // Wins column width
        table.getColumnModel().getColumn(3).setPreferredWidth(80); // Games column width
        table.getColumnModel().getColumn(4).setPreferredWidth(200); // Avg Time to Game column width

        // Add the table to a JScrollPane to allow scrolling
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the panel
        add(scrollPane, BorderLayout.CENTER);
    }
}