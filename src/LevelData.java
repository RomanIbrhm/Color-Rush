public class LevelData {
    public int id, gridSize, timeLimit;
    public String baseColorHex, targetColorHex;

    public LevelData(int id, int gridSize, String base, String target, int time) {
        this.id = id;
        this.gridSize = gridSize;
        this.baseColorHex = base;
        this.targetColorHex = target;
        this.timeLimit = time;
    }
}