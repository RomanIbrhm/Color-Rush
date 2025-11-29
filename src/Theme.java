import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

public final class Theme {

    private Theme() {
        throw new UnsupportedOperationException("Class Theme tidak boleh di-instantiate");
    }

    public static final Color BG_COLOR = new Color(220, 245, 235);
    public static final Color TEXT_COLOR = new Color(50, 50, 50);
    public static final Color TEXT_MAIN = Color.BLACK;
    public static final Color BUTTON_GREEN = new Color(180, 220, 210);
    public static final Color HOVER1 = new Color(60, 60, 60);
    public static final Color HOVER2 = new Color(240, 240, 240);
    public static final Color HOVER3 = new Color(160, 200, 190);
    public static final Color WIN = new Color(60, 179, 113);
    public static final Color LOSE = new Color(220, 53, 69);
    
    public static final Font FONT_ICON = new Font("Segoe UI Emoji", Font.PLAIN, 40);
    public static final Font FONT_HEADER = new Font("Verdana", Font.BOLD, 28);
    public static final Font FONT_LABEL = new Font("Arial", Font.BOLD, 14);

    public static JButton createButton(String text, int width, int height, Color normalColor, Color hoverColor, Color textColor, int radius) {
        return new JButton(text) {
            private Color currentColor = normalColor;
            {
                setPreferredSize(new Dimension(width, height));
                setMaximumSize(new Dimension(width, height)); 
                setAlignmentX(Component.CENTER_ALIGNMENT);
                setFont(FONT_LABEL);
                setForeground(textColor);

                setBorderPainted(false);
                setFocusPainted(false);
                setContentAreaFilled(false);
                setCursor(new Cursor(Cursor.HAND_CURSOR));

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        currentColor = hoverColor;
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        currentColor = normalColor;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(currentColor);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

                g2.setColor(new Color(200, 200, 200));
                g2.draw(new RoundRectangle2D.Double(0.5, 0.5, getWidth()-1, getHeight()-1, radius, radius));

                super.paintComponent(g);
                g2.dispose();
            }
        };
    }

}
