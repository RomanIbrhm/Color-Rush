import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class LoginPanel extends JPanel {

    // Palet Warna (Sama dengan Menu & Leaderboard)
    private final Color BG_COLOR = new Color(220, 245, 235); // Hijau Mint
    private final Color TEXT_COLOR = new Color(50, 50, 50);
    private final Color ACCENT_BLACK = Color.BLACK;

    public LoginPanel(MainFrame frame) {
        setLayout(new GridBagLayout()); // Gunakan GridBag agar posisi tepat di tengah
        setBackground(BG_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Jarak antar elemen
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // --- 1. HEADER (LOGO & JUDUL) ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(BG_COLOR);
        
        JLabel logoIcon = new JLabel("🎨");
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        
        JLabel titleLabel = new JLabel("COLOR RUSH");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 28));
        titleLabel.setForeground(ACCENT_BLACK);

        headerPanel.add(logoIcon);
        headerPanel.add(Box.createHorizontalStrut(10));
        headerPanel.add(titleLabel);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0); // Jarak lebih besar di bawah judul
        add(headerPanel, gbc);

        // --- 2. INPUT USERNAME ---
        addLabel("Username", gbc, 1);
        
        JTextField userField = new RoundedTextField(20);
        gbc.gridy = 2;
        gbc.ipady = 10; // Tinggi input field
        add(userField, gbc);

        // --- 3. INPUT PASSWORD ---
        addLabel("Password", gbc, 3);
        
        JPasswordField passField = new RoundedPasswordField(20);
        gbc.gridy = 4;
        gbc.ipady = 10;
        add(passField, gbc);

        // --- 4. TOMBOL LOGIN (Hitam Full) ---
        RoundedButton btnLogin = new RoundedButton("MASUK", true);
        gbc.gridy = 5;
        gbc.insets = new Insets(30, 0, 10, 0); // Jarak agak jauh dari input
        gbc.ipady = 15; // Tombol lebih tinggi
        add(btnLogin, gbc);

        // --- 5. TOMBOL REGISTER (Outline/Putih) ---
        RoundedButton btnRegister = new RoundedButton("DAFTAR AKUN BARU", false);
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(btnRegister, gbc);

        // ==========================================
        // LOGIKA TOMBOL (Sama seperti sebelumnya)
        // ==========================================
        
        btnLogin.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            GameDAO dao = new GameDAO();
            
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username/Password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (dao.login(user, pass)) {
                MainFrame.currentUser = user;
                MainFrame.cardLayout.show(MainFrame.mainPanel, "Menu");
                userField.setText(""); passField.setText(""); // Reset
            } else {
                JOptionPane.showMessageDialog(this, "Login Gagal! Cek username/password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegister.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            GameDAO dao = new GameDAO();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Isi username dan password untuk daftar!", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String result = dao.register(user, pass);
            if (result.equals("Sukses")) {
                JOptionPane.showMessageDialog(this, "Registrasi Berhasil! Silakan Login.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                userField.setText(""); passField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, result, "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Helper untuk menambah Label
    private void addLabel(String text, GridBagConstraints gbc, int yPos) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
        gbc.gridy = yPos;
        gbc.insets = new Insets(15, 5, 5, 0); // Jarak atas bawah label
        gbc.ipady = 0;
        add(label, gbc);
    }

    // ========================================================
    // CLASS CUSTOM: ROUNDED TEXT FIELD
    // ========================================================
    class RoundedTextField extends JTextField {
        public RoundedTextField(int columns) {
            super(columns);
            setOpaque(false); // Agar background rounded tergambar
            setFont(new Font("Arial", Font.PLAIN, 14));
            // Margin di dalam teks (Padding)
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Background Putih Rounded
            g2.setColor(Color.WHITE);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            
            // Border Tipis Abu-abu
            g2.setColor(new Color(200, 200, 200));
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 20, 20));
            
            super.paintComponent(g);
            g2.dispose();
        }
    }

    // ========================================================
    // CLASS CUSTOM: ROUNDED PASSWORD FIELD
    // ========================================================
    class RoundedPasswordField extends JPasswordField {
        public RoundedPasswordField(int columns) {
            super(columns);
            setOpaque(false);
            setFont(new Font("Arial", Font.PLAIN, 14));
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            g2.setColor(new Color(200, 200, 200));
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 20, 20));
            super.paintComponent(g);
            g2.dispose();
        }
    }

    // ========================================================
    // CLASS CUSTOM: ROUNDED BUTTON (Login Style)
    // ========================================================
    class RoundedButton extends JButton {
        private boolean isPrimary;
        private Color normalColor;
        private Color hoverColor;

        public RoundedButton(String text, boolean isPrimary) {
            super(text);
            this.isPrimary = isPrimary;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(new Font("Arial", Font.BOLD, 14));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            if (isPrimary) {
                // Tombol LOGIN: Hitam -> Abu Tua
                setForeground(Color.WHITE);
                normalColor = ACCENT_BLACK;
                hoverColor = new Color(60, 60, 60);
            } else {
                // Tombol DAFTAR: Putih/Mint -> Abu Muda
                setForeground(ACCENT_BLACK);
                normalColor = Color.WHITE;
                hoverColor = new Color(240, 240, 240);
            }

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { 
                    normalColor = hoverColor; 
                    repaint(); 
                }
                public void mouseExited(MouseEvent e) { 
                    normalColor = isPrimary ? ACCENT_BLACK : Color.WHITE; 
                    repaint(); 
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(normalColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

            // Jika tombol sekunder (Register), beri border tipis
            if (!isPrimary) {
                g2.setColor(new Color(200, 200, 200));
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 20, 20));
            }

            super.paintComponent(g);
            g2.dispose();
        }
    }
}