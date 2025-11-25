import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class MenuPanel extends JPanel {

    private final Color BG_COLOR = new Color(220, 245, 235);
    private final Color ACCENT_BLACK = Color.BLACK;
    private final Color ACCENT_GREEN = new Color(180, 220, 210);
    private final Color TEXT_MAIN = Color.BLACK;

    public MenuPanel(MainFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BG_COLOR);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setMaximumSize(new Dimension(500, 80));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logoIcon = new JLabel("🎨");
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        
        JLabel titleText = new JLabel("COLOR RUSH");
        titleText.setFont(new Font("Verdana", Font.BOLD, 28));
        titleText.setForeground(TEXT_MAIN);
        
        headerPanel.add(logoIcon);
        headerPanel.add(Box.createHorizontalStrut(10));
        headerPanel.add(titleText);

        ModernButton btnStart = new ModernButton("Mulai Permainan", "🎮", true);
        ModernButton btnRank = new ModernButton("Leaderboard", "🏆", false);
        ModernButton btnLogout = new ModernButton("Logout", "🔙", false);
        ModernButton btnExit = new ModernButton("Keluar Aplikasi", "✖", false);

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
            GamePanel gp = (GamePanel) frame.mainPanel.getComponent(2); 
            gp.startGame(); 
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Game");
        });

        btnRank.addActionListener(e -> {
            LeaderboardPanel lp = (LeaderboardPanel) frame.mainPanel.getComponent(3);
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

    class ModernButton extends JButton {
        private boolean isActive;
        private Color normalColor;
        private Color hoverColor;
        private Color pressedColor;

        public ModernButton(String text, String icon, boolean isActive) {
            super(icon + "  " + text);
            this.isActive = isActive;
            
            setFont(new Font("Arial", Font.BOLD, 16));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            
            setHorizontalAlignment(SwingConstants.CENTER); 
            setBorder(new EmptyBorder(15, 20, 15, 20)); 
            
            setMaximumSize(new Dimension(500, 60)); 
            setAlignmentX(Component.CENTER_ALIGNMENT);

            if (isActive) {
                setForeground(Color.WHITE);
                normalColor = ACCENT_BLACK;
                hoverColor = new Color(60, 60, 60);
            } else {
                setForeground(TEXT_MAIN);
                normalColor = ACCENT_GREEN; 
                hoverColor = new Color(160, 200, 190);
            }
            pressedColor = hoverColor.darker();

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { setBackground(hoverColor); repaint(); }
                public void mouseExited(MouseEvent e) { setBackground(normalColor); repaint(); }
            });
            
            setBackground(normalColor);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()) {
                g2.setColor(pressedColor);
            } else if (getModel().isRollover()) {
                g2.setColor(hoverColor);
            } else {
                g2.setColor(getBackground());
            }

            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

            super.paintComponent(g);
            g2.dispose();
        }
    }
}