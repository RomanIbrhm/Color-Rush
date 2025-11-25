import java.awt.Color;

public class NormalCell extends Cell {
    public NormalCell(int x, int y, int size, Color color) {
        super(x, y, size, color);
    }

    @Override
    public boolean onClick() {
        return false; // Salah tebak
    }
}