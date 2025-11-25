import java.awt.Color;

public class TargetCell extends Cell {
    public TargetCell(int x, int y, int size, Color color) {
        super(x, y, size, color);
    }

    @Override
    public boolean onClick() {
        return true; // Benar
    }
}