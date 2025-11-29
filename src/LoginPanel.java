import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoginPanel extends JPanel {

    public LoginPanel(MainFrame frame) {
        setLayout(new GridBagLayout()); 
        setBackground(Theme.BG_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(Theme.BG_COLOR);
        
        JLabel logoIcon = new JLabel("🎨");
        logoIcon.setFont(Theme.FONT_ICON);
        logoIcon.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel titleLabel = new JLabel("COLOR HUNTER");
        titleLabel.setFont(Theme.FONT_HEADER);
        titleLabel.setForeground(Theme.TEXT_MAIN);

        headerPanel.add(logoIcon);
        headerPanel.add(Box.createHorizontalStrut(10));
        headerPanel.add(titleLabel);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0); 
        add(headerPanel, gbc);

        addLabel("Username : ", gbc, 1);
        
        JTextField userField = new RoundedTextField(20);
        gbc.gridy = 2;
        gbc.ipady = 10; 
        add(userField, gbc);

        addLabel("Password : ", gbc, 3);
        
        JPasswordField passField = new RoundedPasswordField(20);
        gbc.gridy = 4;
        gbc.ipady = 10;
        add(passField, gbc);

        JButton btnLogin = Theme.createButton("MASUK", 200, 25, Color.BLACK, Theme.HOVER1, Color.WHITE, 20);
        gbc.gridy = 5;
        gbc.insets = new Insets(30, 0, 10, 0); 
        gbc.ipady = 15; 
        add(btnLogin, gbc);

        JButton btnRegister = Theme.createButton("DAFTAR", 200, 25, Color.WHITE, Theme.HOVER2, Theme.TEXT_MAIN, 20);
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(btnRegister, gbc);
        
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
                userField.setText(""); passField.setText(""); 
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

    private void addLabel(String text, GridBagConstraints gbc, int yPos) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.FONT_LABEL);
        label.setForeground(Theme.TEXT_COLOR);
        gbc.gridy = yPos;
        gbc.insets = new Insets(15, 5, 5, 0); 
        gbc.ipady = 0;
        add(label, gbc);
    }

    class RoundedTextField extends JTextField {
        public RoundedTextField(int columns) {
            super(columns);
            setOpaque(false); 
            setFont(Theme.FONT_LABEL);
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

    class RoundedPasswordField extends JPasswordField {
        public RoundedPasswordField(int columns) {
            super(columns);
            setOpaque(false);
            setFont(Theme.FONT_LABEL);
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
}