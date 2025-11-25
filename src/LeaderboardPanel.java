import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class LeaderboardPanel extends JPanel {
    
    // Palet Warna (Sama dengan MenuPanel)
    private final Color BG_COLOR = new Color(220, 245, 235); // Hijau Mint
    private final Color CARD_BG = Color.WHITE;
    private final Color ACCENT_BLACK = Color.BLACK;
    
    private JPanel listPanel; // Tempat menampung kartu-kartu skor
    private GameDAO dao;

    public LeaderboardPanel(MainFrame frame) {
        dao = new GameDAO();
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // --- 1. HEADER (JUDUL) ---
        JLabel titleLabel = new JLabel("🏆 LEADERBOARD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 28));
        titleLabel.setForeground(ACCENT_BLACK);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // --- 2. AREA DAFTAR SKOR (SCROLLABLE) ---
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // Susunan Vertikal
        listPanel.setBackground(BG_COLOR); // Samakan dengan background utama
        
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null); // Hilangkan border garis
        scrollPane.getViewport().setBackground(BG_COLOR); // Background transparan
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll lebih halus
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. TOMBOL KEMBALI ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(BG_COLOR);
        bottomPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Custom Button (Mirip ModernButton di Menu)
        JButton btnBack = new RoundedButton("Kembali ke Menu");
        btnBack.setPreferredSize(new Dimension(200, 50));
        btnBack.addActionListener(e -> MainFrame.cardLayout.show(MainFrame.mainPanel, "Menu"));
        
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // --- LOGIKA LOAD DATA ---
    public void refreshData() {
        listPanel.removeAll(); // Hapus data lama
        List<String> ranks = dao.getLeaderboard();

        if (ranks.isEmpty()) {
            JLabel emptyLabel = new JLabel("Belum ada data permainan.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(emptyLabel);
        } else {
            // Loop data dan buat kartu untuk setiap user
            for (String rowData : ranks) {
                // rowData format: "1. username - Lvl 5 (Total: 40s | Avg: 8s)"
                listPanel.add(new RankCard(rowData));
                listPanel.add(Box.createVerticalStrut(10)); // Jarak antar kartu
            }
        }
        
        listPanel.revalidate();
        listPanel.repaint();
    }

    // ========================================================
    // CLASS: KARTU PERINGKAT (RANK CARD)
    // ========================================================
    class RankCard extends JPanel {
        public RankCard(String data) {
            setOpaque(false); // Agar background rounded tergambar
            setLayout(new BorderLayout());
            setMaximumSize(new Dimension(500, 80)); // Tinggi kartu fix
            setPreferredSize(new Dimension(500, 80));
            setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding dalam kartu

            // Parsing String Sederhana
            // Asumsi format: "1. Roman - Lvl 10 (Total: 1,0s | Avg: 0,1s)"
            String[] parts = data.split("-", 2); 
            String mainInfo = parts[0].trim(); // "1. Roman"
            String statsInfo = parts.length > 1 ? parts[1].trim() : ""; // "Lvl 10..."

            // 1. Kiri: Info Nama & Rank
            JLabel nameLabel = new JLabel(mainInfo);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            nameLabel.setForeground(new Color(50, 50, 50));

            // 2. Kanan/Bawah: Info Statistik
            JLabel statsLabel = new JLabel(statsInfo);
            statsLabel.setFont(new Font("Consolas", Font.PLAIN, 14)); // Font monospaced agar angka rapi
            statsLabel.setForeground(new Color(100, 100, 100));

            add(nameLabel, BorderLayout.NORTH);
            add(statsLabel, BorderLayout.SOUTH);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Warna Kartu (Putih Bersih)
            g2.setColor(Color.WHITE);
            // Gambar kotak dengan sudut melengkung
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            
            // Tambahkan Shadow/Border tipis
            g2.setColor(new Color(200, 200, 200));
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 20, 20));

            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ========================================================
    // CLASS: TOMBOL ROUNDED (Sederhana)
    // ========================================================
    class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(new Font("Arial", Font.BOLD, 14));
            setForeground(Color.WHITE);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()) {
                g2.setColor(new Color(40, 40, 40)); // Hitam pudar saat diklik
            } else if (getModel().isRollover()) {
                g2.setColor(new Color(60, 60, 60)); // Abu tua saat hover
            } else {
                g2.setColor(Color.BLACK); // Hitam (Sesuai tema Menu)
            }

            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            super.paintComponent(g);
            g2.dispose();
        }
    }
}