import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;

class RankCard extends JPanel {
        public RankCard(String data) {
            setOpaque(false); 
            setLayout(new BorderLayout());
            setMaximumSize(new Dimension(700, 80)); 
            setPreferredSize(new Dimension(500, 70));
            setBorder(new EmptyBorder(10, 20, 10, 20)); 

            String[] parts = data.split("-", 2); 
            String mainInfo = parts[0].trim(); 
            String statsInfo = parts.length > 1 ? parts[1].trim() : "";

            JLabel nameLabel = new JLabel(mainInfo);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            nameLabel.setForeground(new Color(50, 50, 50));

            JLabel statsLabel = new JLabel(statsInfo);
            statsLabel.setFont(new Font("Consolas", Font.PLAIN, 14)); 
            statsLabel.setForeground(new Color(100, 100, 100));

            add(nameLabel, BorderLayout.NORTH);
            add(statsLabel, BorderLayout.SOUTH);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.WHITE);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            
            g2.setColor(new Color(200, 200, 200));
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 20, 20));

            g2.dispose();
            super.paintComponent(g);
        }
    }