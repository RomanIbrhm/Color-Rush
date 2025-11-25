import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class MenuPanel extends JPanel {

    // Palet Warna (Tema Print.in / Mint Modern)
    private final Color BG_COLOR = new Color(220, 245, 235); // Hijau Mint Muda
    private final Color ACCENT_BLACK = Color.BLACK;          // Untuk tombol utama (Aktif)
    private final Color ACCENT_GREEN = new Color(180, 220, 210); // Untuk tombol sekunder (Pasif)
    private final Color TEXT_MAIN = Color.BLACK;

    public MenuPanel(MainFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Layout vertikal
        setBackground(BG_COLOR);
        setBorder(new EmptyBorder(40, 40, 40, 40)); // Margin/Padding pinggir panel

        // --- 1. HEADER (LOGO & JUDUL) ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Header Rata Tengah
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setMaximumSize(new Dimension(500, 80));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Logo Simpel (Emoji)
        JLabel logoIcon = new JLabel("🎨");
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        
        JLabel titleText = new JLabel("COLOR RUSH");
        titleText.setFont(new Font("Verdana", Font.BOLD, 28));
        titleText.setForeground(TEXT_MAIN);
        
        headerPanel.add(logoIcon);
        headerPanel.add(Box.createHorizontalStrut(10)); // Jarak
        headerPanel.add(titleText);

        // --- 2. TOMBOL MENU (Custom ModernButton) ---
        // Parameter: (Teks, Ikon, ApakahTombolUtama?)
        ModernButton btnStart = new ModernButton("Mulai Permainan", "🎮", true);
        ModernButton btnRank = new ModernButton("Leaderboard", "🏆", false);
        ModernButton btnLogout = new ModernButton("Logout", "🔙", false);
        ModernButton btnExit = new ModernButton("Keluar Aplikasi", "✖", false);

        // --- MENYUSUN LAYOUT KE PANEL ---
        add(headerPanel);
        add(Box.createVerticalStrut(40)); // Jarak header ke menu
        
        add(btnStart);
        add(Box.createVerticalStrut(15)); // Jarak antar tombol
        add(btnRank);
        add(Box.createVerticalStrut(15));
        add(btnLogout);
        add(Box.createVerticalStrut(15));
        add(btnExit);

        // ==========================================
        // LOGIKA AKSI TOMBOL (Action Listeners)
        // ==========================================
        
        // 1. Tombol Mulai
        btnStart.addActionListener(e -> {
            GamePanel gp = (GamePanel) frame.mainPanel.getComponent(2); 
            gp.startGame(); 
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Game");
        });

        // 2. Tombol Leaderboard
        btnRank.addActionListener(e -> {
            LeaderboardPanel lp = (LeaderboardPanel) frame.mainPanel.getComponent(3);
            lp.refreshData();
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Leaderboard");
        });

        // 3. Tombol Logout
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Yakin ingin logout?", 
                "Konfirmasi Logout", 
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                MainFrame.currentUser = ""; // Hapus data user aktif
                MainFrame.cardLayout.show(MainFrame.mainPanel, "Login");
            }
        });

        // 4. Tombol Keluar
        btnExit.addActionListener(e -> {
            System.exit(0);
        });
    }

    // ========================================================
    // CLASS CUSTOM BUTTON (Rounded & Centered)
    // ========================================================
    class ModernButton extends JButton {
        private boolean isActive;
        private Color normalColor;
        private Color hoverColor;
        private Color pressedColor;

        public ModernButton(String text, String icon, boolean isActive) {
            super(icon + "  " + text); // Set Teks dengan Ikon
            this.isActive = isActive;
            
            // Setup Font & Layout Dasar
            setFont(new Font("Arial", Font.BOLD, 16));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false); // Matikan background bawaan Swing
            
            // --- BAGIAN UTAMA: RATA TENGAH ---
            setHorizontalAlignment(SwingConstants.CENTER); 
            // ---------------------------------

            // Padding seimbang (Kiri Kanan sama) agar pas di tengah
            setBorder(new EmptyBorder(15, 20, 15, 20)); 
            
            // Ukuran Tombol
            setMaximumSize(new Dimension(500, 60)); 
            setAlignmentX(Component.CENTER_ALIGNMENT);

            // Pengaturan Warna
            if (isActive) {
                // Gaya Tombol Utama (Hitam)
                setForeground(Color.WHITE);
                normalColor = ACCENT_BLACK;
                hoverColor = new Color(60, 60, 60);
            } else {
                // Gaya Tombol Sekunder (Hijau Pudar)
                setForeground(TEXT_MAIN);
                normalColor = ACCENT_GREEN; 
                hoverColor = new Color(160, 200, 190);
            }
            pressedColor = hoverColor.darker();

            // Efek Hover Mouse (Ganti warna saat disentuh kursor)
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { setBackground(hoverColor); repaint(); }
                public void mouseExited(MouseEvent e) { setBackground(normalColor); repaint(); }
            });
            
            setBackground(normalColor); // Warna awal
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Tentukan warna saat ini (Ditekan / Hover / Normal)
            if (getModel().isPressed()) {
                g2.setColor(pressedColor);
            } else if (getModel().isRollover()) {
                g2.setColor(hoverColor);
            } else {
                g2.setColor(getBackground());
            }

            // Gambar Persegi Panjang dengan Sudut Melengkung (Rounded)
            // Arc Width/Height = 20 memberikan efek melengkung yang halus
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

            super.paintComponent(g); // Gambar teks & ikon di atas background
            g2.dispose();
        }
    }
}