import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ResultDialog extends JDialog {

    public ResultDialog(JFrame parent, boolean isWin, int levelReached, double totalTime) {
        super(parent, "Hasil Permainan", true); // True = Modal (Blokir window belakang)
        setUndecorated(true); // Hilangkan border window bawaan OS
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setBackground(new Color(0, 0, 0, 0)); // Background transparan untuk efek rounded

        // Panel Utama dengan Sudut Melengkung
        JPanel contentPanel = new RoundedPanel(25);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Tentukan Warna & Teks Berdasarkan Hasil
        Color themeColor = isWin ? new Color(60, 179, 113) : new Color(220, 53, 69); // Hijau atau Merah
        String titleText = isWin ? "LUAR BIASA!" : "GAME OVER";
        String iconText = isWin ? "🏆" : "⏳";
        String descText = isWin ? "Semua level berhasil diselesaikan!" : "Waktu habis atau salah pilih.";

        // Hitung Rata-rata (Level 0 dihitung 1 agar tidak error bagi 0)
        double avgTime = totalTime / (levelReached == 0 ? 1 : levelReached);

        // --- KOMPONEN UI ---

        // 1. Ikon Besar
        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 2. Judul Status
        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(themeColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 3. Deskripsi Singkat
        JLabel descLabel = new JLabel(descText);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(Color.GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 4. Panel Statistik (Grid)
        JPanel statsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        statsPanel.setBackground(new Color(245, 245, 245)); // Abu sangat muda
        statsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        statsPanel.setMaximumSize(new Dimension(300, 120));
        
        addStat(statsPanel, "Level Selesai:", String.valueOf(levelReached));
        addStat(statsPanel, "Total Waktu:", String.format("%.1f detik", totalTime));
        addStat(statsPanel, "Rata-rata:", String.format("%.1f s / level", avgTime));

        // 5. Tombol Kembali
        JButton btnClose = new JButton("KEMBALI KE MENU");
        styleButton(btnClose, themeColor);
        btnClose.addActionListener(e -> dispose()); // Tutup dialog

        // --- SUSUN LAYOUT ---
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

    // Helper untuk menambah baris statistik
    private void addStat(JPanel panel, String label, String value) {
        JLabel l = new JLabel(label);
        l.setFont(new Font("Arial", Font.PLAIN, 14));
        l.setForeground(Color.DARK_GRAY);
        
        JLabel v = new JLabel(value, SwingConstants.RIGHT);
        v.setFont(new Font("Arial", Font.BOLD, 14));
        v.setForeground(Color.BLACK);
        
        panel.add(l);
        panel.add(v);
    }

    // Styling Tombol
    private void styleButton(JButton btn, Color color) {
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(300, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Class Internal untuk Panel Rounded
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