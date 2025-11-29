import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ResultDialog extends JDialog {

    public ResultDialog(JFrame parent, boolean isWin, int levelReached, double totalTime) {
        super(parent, "Hasil Permainan", true); 
        setUndecorated(true); 
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setBackground(new Color(0, 0, 0, 0)); 

        JPanel contentPanel = new RoundedPanel(25);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        Color themeColor = isWin ? Theme.WIN : Theme.LOSE; 
        String titleText = isWin ? "LUAR BIASA!" : "GAME OVER";
        String iconText = isWin ? "🏆" : "⏳";
        String descText = isWin ? "Semua level berhasil diselesaikan!" : "Waktu habis atau salah pilih.";
 
        double avgTime = totalTime / (levelReached == 0 ? 1 : levelReached);

        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setFont(Theme.FONT_ICON);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(themeColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel(descText);
        descLabel.setFont(Theme.FONT_LABEL);
        descLabel.setForeground(Color.GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel statsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        statsPanel.setBackground(new Color(245, 245, 245)); 
        statsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        statsPanel.setMaximumSize(new Dimension(300, 120));
        
        addStat(statsPanel, "Level Selesai:", String.valueOf(levelReached));
        addStat(statsPanel, "Total Waktu:", String.format("%.1f detik", totalTime));
        addStat(statsPanel, "Rata-rata:", String.format("%.1f s / level", avgTime));

        JButton btnClose = Theme.createButton("KEMBALI KE MENU", 300, 45, themeColor, themeColor.darker(), Color.WHITE, 16);
        btnClose.addActionListener(e -> dispose()); 

        contentPanel.add(iconLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(descLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(statsPanel);
        contentPanel.add(Box.createVerticalStrut(25));
        contentPanel.add(btnClose);

        add(contentPanel);
    }

    private void addStat(JPanel panel, String label, String value) {
        JLabel l = new JLabel(label);
        l.setFont(Theme.FONT_LABEL);
        l.setForeground(Color.DARK_GRAY);
        
        JLabel v = new JLabel(value, SwingConstants.RIGHT);
        v.setFont(Theme.FONT_LABEL);
        v.setForeground(Color.BLACK);
        
        panel.add(l);
        panel.add(v);
    }

    class RoundedPanel extends JPanel {
        private int radius;
        RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            super.paintComponent(g);
        }
    }
}