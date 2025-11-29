import javax.swing.SwingUtilities;

public class GameTimer implements Runnable {
    private int timeRemaining;
    private boolean isRunning;
    private GamePanel gamePanel; 

    public GameTimer(GamePanel panel, int startTime) {
        this.gamePanel = panel;
        this.timeRemaining = startTime;
        this.isRunning = true;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void stopTimer() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning && timeRemaining > 0) {
            try {
                Thread.sleep(1000); 
                timeRemaining--;
                SwingUtilities.invokeLater(() -> gamePanel.updateTime(timeRemaining));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (timeRemaining <= 0 && isRunning) {
            SwingUtilities.invokeLater(() -> gamePanel.gameOver());
        }
    }
}