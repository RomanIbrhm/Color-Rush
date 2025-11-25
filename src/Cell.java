import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Cell {
    protected int x, y, size;
    protected Color color;
    protected Rectangle bounds;

    public Cell(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.bounds = new Rectangle(x, y, size, size);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, size, size);
    }

    public boolean isClicked(int mouseX, int mouseY) {
        return bounds.contains(mouseX, mouseY);
    }

    public abstract boolean onClick(); 
}