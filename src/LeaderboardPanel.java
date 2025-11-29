import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class LeaderboardPanel extends JPanel {
    private JPanel listPanel; 
    private GameDAO dao;

    public LeaderboardPanel(MainFrame frame) {
        dao = new GameDAO();
        setLayout(new BorderLayout());
        setBackground(Theme.BG_COLOR);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = new JLabel("LEADERBOARD", SwingConstants.CENTER);
        titleLabel.setFont(Theme.FONT_HEADER);
        titleLabel.setForeground(Theme.TEXT_MAIN);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); 
        listPanel.setBackground(Theme.BG_COLOR); 
        
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null); 
        scrollPane.getViewport().setBackground(Theme.BG_COLOR); 
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); 
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Theme.BG_COLOR);
        bottomPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JButton btnBack = Theme.createButton("KEMBALI", 200, 50, Color.BLACK, Theme.HOVER1, Color.WHITE, 20);
        btnBack.addActionListener(e -> MainFrame.cardLayout.show(MainFrame.mainPanel, "Menu"));
        
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void refreshData() {
        listPanel.removeAll();
        List<String> ranks = dao.getLeaderboard();

        if (ranks.isEmpty()) {
            JLabel emptyLabel = new JLabel("Belum ada data permainan.", SwingConstants.CENTER);
            emptyLabel.setFont(Theme.FONT_LABEL);
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(emptyLabel);
        } else {
            for (String rowData : ranks) {
                listPanel.add(new RankCard(rowData));
                listPanel.add(Box.createVerticalStrut(10));
            }
        }
        
        listPanel.revalidate();
        listPanel.repaint();
    }
}