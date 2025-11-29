import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private ArrayList<Cell> gridCells;
    private int currentLevelId = 1;
    private boolean isGameActive = false;
    
    private GameTimer gameTimer;
    private Thread timerThread;
    private double totalTimePlayed = 0.0; 
    private int currentLevelTimeLimit = 0; 
    
    private MainFrame mainFrame;
    private GameDAO dao;
    private GameSound gameSound;
    
    private JLabel infoLabel;      
    private JLabel countdownLabel;
    private JPanel canvas;         

    public GamePanel(MainFrame frame) {
        this.mainFrame = frame;
        this.dao = new GameDAO();
        this.gameSound = new GameSound();
        this.setLayout(new BorderLayout());

        infoLabel = new JLabel("Siap-siap...", SwingConstants.CENTER);
        infoLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setOpaque(true);
        infoLabel.setBackground(Color.BLACK);
        infoLabel.setPreferredSize(new Dimension(0, 50));
        add(infoLabel, BorderLayout.NORTH);

        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (isGameActive && gridCells != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    for (Cell cell : gridCells) {
                        cell.draw(g2);
                    }
                }
            }
        };

        canvas.setBackground(Theme.BG_COLOR);
        canvas.setLayout(new GridBagLayout()); 

        countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 120));
        countdownLabel.setForeground(Theme.BG_COLOR.darker().darker()); 
    
        canvas.add(countdownLabel); 

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isGameActive) return;
                checkClick(e.getX(), e.getY());
                canvas.repaint();
            }
        });

        add(canvas, BorderLayout.CENTER);
    }

    public void startGame() {
        currentLevelId = 1;
        totalTimePlayed = 0.0; 
        startCountdownAndLoadLevel();
    }

    private void startCountdownAndLoadLevel() {
        isGameActive = false; 
        gridCells = null;     
        canvas.repaint();    
        
        new Thread(() -> {
            try {
                SwingUtilities.invokeLater(() -> infoLabel.setText("Siap-siap..."));
                Thread.sleep(1000);

                for (int i = 3; i > 0; i--) {
                    final int count = i;
                    SwingUtilities.invokeLater(() -> {
                        countdownLabel.setText(String.valueOf(count));
                        countdownLabel.setVisible(true);
                        infoLabel.setText("Mulai dalam ...");
                    });
                    Thread.sleep(1000); 
                }

                SwingUtilities.invokeLater(() -> countdownLabel.setText("GO!"));
                Thread.sleep(500); 

                SwingUtilities.invokeLater(() -> {
                    countdownLabel.setVisible(false);
                    gameSound.playMusic("assets/sound_game.wav");
                    loadLevel(); 
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loadLevel() {
        LevelData data = dao.getLevel(currentLevelId);
        
        if (data == null) {
            handleGameFinish(true); 
            return;
        }

        this.currentLevelTimeLimit = data.timeLimit;

        gridCells = new ArrayList<>();
        int panelSize = Math.min(canvas.getWidth(), canvas.getHeight()) - 40; 
        int size = panelSize / data.gridSize;
        
        int startX = (canvas.getWidth() - (size * data.gridSize)) / 2;
        int startY = (canvas.getHeight() - (size * data.gridSize)) / 2;

        Color base = Color.decode(data.baseColorHex);
        Color target = Color.decode(data.targetColorHex);
        
        int targetIndex = (int) (Math.random() * (data.gridSize * data.gridSize));
        int index = 0;

        for (int row = 0; row < data.gridSize; row++) {
            for (int col = 0; col < data.gridSize; col++) {
                int x = startX + (col * size);
                int y = startY + (row * size);
                
                if (index == targetIndex) {
                    gridCells.add(new TargetCell(x, y, size, target));
                } else {
                    gridCells.add(new NormalCell(x, y, size, base));
                }
                index++;
            }
        }

        if (gameTimer != null) gameTimer.stopTimer();
        gameTimer = new GameTimer(this, data.timeLimit);
        timerThread = new Thread(gameTimer);
        timerThread.start();
        
        isGameActive = true;
        updateTime(data.timeLimit);
        canvas.repaint();
    }

    private void checkClick(int x, int y) {
        if (gridCells == null) return;

        for (Cell cell : gridCells) {
            if (cell.isClicked(x, y)) {
                if (cell.onClick()) {
                    int timeRemaining = gameTimer.getTimeRemaining(); 
                    int timeSpent = currentLevelTimeLimit - timeRemaining;
                    
                    totalTimePlayed += Math.max(0, timeSpent);

                    currentLevelId++;
                    loadLevel(); 

                } else {
                    gameOver();
                }
                return; 
            }
        }
    }

    public void updateTime(int time) {
        infoLabel.setText(String.format("LEVEL: %d  |  WAKTU: %ds  |  TOTAL: %.0fs", currentLevelId, time, totalTimePlayed));
    }

    public void gameOver() {
        totalTimePlayed += currentLevelTimeLimit;
        handleGameFinish(false); 
    }

    private void handleGameFinish(boolean isWin) {
        isGameActive = false;
        gameSound.stopMusic();
        if (gameTimer != null) gameTimer.stopTimer();

        int levelReached = currentLevelId - 1;

        dao.saveScore(MainFrame.currentUser, levelReached, totalTimePlayed);

        SwingUtilities.invokeLater(() -> {
            new ResultDialog(mainFrame, isWin, levelReached, totalTimePlayed).setVisible(true);
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Menu");
        });
    }
}