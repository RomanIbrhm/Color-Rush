import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static CardLayout cardLayout;
    public static JPanel mainPanel;
    public static String currentUser = "";

    public MainFrame() {
        setTitle("Color Rush - Database Edition");
        setSize(600, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new MenuPanel(this), "Menu");
        mainPanel.add(new GamePanel(this), "Game"); 
        mainPanel.add(new LeaderboardPanel(this), "Leaderboard");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login"); 

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}