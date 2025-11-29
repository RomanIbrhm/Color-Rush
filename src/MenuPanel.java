import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(MainFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        setBackground(Theme.BG_COLOR);
        setBorder(new EmptyBorder(40, 40, 40, 40)); 

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        headerPanel.setBackground(Theme.BG_COLOR);
        headerPanel.setMaximumSize(new Dimension(500, 80));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logoIcon = new JLabel("🎨");
        logoIcon.setFont(Theme.FONT_ICON);
        logoIcon.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel titleText = new JLabel("COLOR HUNTER");
        titleText.setFont(Theme.FONT_HEADER);
        titleText.setForeground(Theme.TEXT_MAIN);
        
        headerPanel.add(logoIcon);
        headerPanel.add(Box.createHorizontalStrut(10)); 
        headerPanel.add(titleText);


        JButton btnStart = Theme.createButton("Mulai Permainan", 500, 60, Theme.TEXT_MAIN, Theme.HOVER1, Color.WHITE, 20);
        JButton btnRank = Theme.createButton("Leaderboard", 500, 60, Theme.BUTTON_GREEN, Theme.HOVER3, Theme.TEXT_MAIN, 20);
        JButton btnLogout = Theme.createButton("Logout", 500, 60, Theme.BUTTON_GREEN, Theme.HOVER3, Theme.TEXT_MAIN, 20);
        JButton btnExit = Theme.createButton("Keluar Aplikasi", 500, 60, Theme.BUTTON_GREEN, Theme.HOVER3, Theme.TEXT_MAIN, 20);

        add(headerPanel);
        add(Box.createVerticalStrut(40)); 
        
        add(btnStart);
        add(Box.createVerticalStrut(15)); 
        add(btnRank);
        add(Box.createVerticalStrut(15));
        add(btnLogout);
        add(Box.createVerticalStrut(15));
        add(btnExit);

        btnStart.addActionListener(e -> {
            GamePanel gp = (GamePanel) MainFrame.mainPanel.getComponent(2); 
            gp.startGame(); 
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Game");
        });

        btnRank.addActionListener(e -> {
            LeaderboardPanel lp = (LeaderboardPanel) MainFrame.mainPanel.getComponent(3);
            lp.refreshData();
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Leaderboard");
        });

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Yakin ingin logout?", 
                "Konfirmasi Logout", 
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                MainFrame.currentUser = ""; 
                MainFrame.cardLayout.show(MainFrame.mainPanel, "Login");
            }
        });

        btnExit.addActionListener(e -> {
            System.exit(0);
        });
    }

}