import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    // --- Variabel Logika Game ---
    private ArrayList<Cell> gridCells;
    private int currentLevelId = 1;
    private boolean isGameActive = false;
    
    // --- Variabel Waktu ---
    private GameTimer gameTimer;
    private Thread timerThread;
    private double totalTimePlayed = 0.0; // Total detik yang dihabiskan user
    private int currentLevelTimeLimit = 0; // Batas waktu level saat ini
    
    // --- Dependensi ---
    private MainFrame mainFrame;
    private GameDAO dao;
    
    // --- Komponen GUI ---
    private JLabel infoLabel;      // Header Info (Level, Waktu)
    private JLabel countdownLabel; // Angka Hitung Mundur Besar
    private JPanel canvas;         // Area Gambar Kotak

    public GamePanel(MainFrame frame) {
        this.mainFrame = frame;
        this.dao = new GameDAO();
        this.setLayout(new BorderLayout());

        // 1. HEADER INFO (Atas)
        infoLabel = new JLabel("Siap-siap...", SwingConstants.CENTER);
        infoLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setOpaque(true);
        infoLabel.setBackground(new Color(40, 40, 40));
        infoLabel.setPreferredSize(new Dimension(0, 50));
        add(infoLabel, BorderLayout.NORTH);

        // 2. AREA TENGAH (Canvas + Countdown)
        // Kita gunakan GridBagLayout pada canvas agar countdown label bisa ditaruh persis di tengah
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Hanya gambar grid jika game aktif
                if (isGameActive && gridCells != null) {
                    // Gunakan Graphics2D untuk rendering lebih halus
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    for (Cell cell : gridCells) {
                        cell.draw(g2);
                    }
                }
            }
        };
        canvas.setBackground(Color.DARK_GRAY);
        canvas.setLayout(new GridBagLayout()); // Layout untuk menengahkan Label Countdown

        // Label Countdown (Awalnya Sembunyi)
        countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 120));
        countdownLabel.setForeground(new Color(255, 215, 0)); // Emas
        
        // Efek bayangan teks sederhana (Opsional, lewat UI Manager atau custom painting)
        canvas.add(countdownLabel); // Tambahkan ke tengah canvas

        // Event Listener Klik Mouse
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

    // --- METHOD UTAMA: MEMULAI GAME ---
    public void startGame() {
        currentLevelId = 1;
        totalTimePlayed = 0.0; // Reset total waktu
        startCountdownAndLoadLevel();
    }

    // --- LOGIKA COUNTDOWN (3.. 2.. 1.. GO!) ---
    private void startCountdownAndLoadLevel() {
        isGameActive = false; // Matikan interaksi mouse
        gridCells = null;     // Hapus grid lama
        canvas.repaint();     // Bersihkan layar
        
        // Jalankan di Thread terpisah agar GUI tidak macet (freeze)
        new Thread(() -> {
            try {
                for (int i = 3; i > 0; i--) {
                    final int count = i;
                    SwingUtilities.invokeLater(() -> {
                        countdownLabel.setText(String.valueOf(count));
                        countdownLabel.setVisible(true);
                        infoLabel.setText("Mulai dalam " + count + "...");
                    });
                    Thread.sleep(1000); // Tunggu 1 detik
                }

                // Tampilkan GO!
                SwingUtilities.invokeLater(() -> countdownLabel.setText("GO!"));
                Thread.sleep(500); 

                // Sembunyikan Label & Mulai Level
                SwingUtilities.invokeLater(() -> {
                    countdownLabel.setVisible(false);
                    loadLevel(); 
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // --- LOGIKA LOAD LEVEL DARI DATABASE ---
    //    // CONTOH SEMENTARA TAHAP 1 (Di dalam GamePanel.java)
    //private void loadLevel() {
    //    // Hardcode level untuk tes logika game
    //    int gridSize = 2 + (currentLevelId / 2); // Grid bertambah tiap 2 level
    //    Color base = Color.RED;
    //    Color target = new Color(255, 100, 100); // Merah muda
    //    int timeLimit = 10;
    //    
    //    // ... Sisa logika generate gridCells sama ...
    //}
    private void loadLevel() {
        // Ambil data soal random dari DB
        LevelData data = dao.getLevel(currentLevelId);
        
        // --- CEK KEMENANGAN (Jika data null, berarti level habis) ---
        if (data == null) {
            handleGameFinish(true); // User Menang
            return;
        }

        // Simpan batas waktu level ini (untuk perhitungan skor nanti)
        this.currentLevelTimeLimit = data.timeLimit;

        // Setup Ukuran Grid Responsif
        gridCells = new ArrayList<>();
        int panelSize = Math.min(canvas.getWidth(), canvas.getHeight()) - 40; // Padding 40px
        int size = panelSize / data.gridSize;
        
        // Hitung koordinat awal (Offset) agar grid berada di tengah canvas
        int startX = (canvas.getWidth() - (size * data.gridSize)) / 2;
        int startY = (canvas.getHeight() - (size * data.gridSize)) / 2;

        Color base = Color.decode(data.baseColorHex);
        Color target = Color.decode(data.targetColorHex);
        
        // Tentukan posisi target secara acak
        int targetIndex = (int) (Math.random() * (data.gridSize * data.gridSize));
        int index = 0;

        // Loop pembuatan kotak
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

        // Mulai Timer Level
        if (gameTimer != null) gameTimer.stopTimer();
        gameTimer = new GameTimer(this, data.timeLimit);
        timerThread = new Thread(gameTimer);
        timerThread.start();
        
        isGameActive = true;
        updateTime(data.timeLimit);
        canvas.repaint();
    }

    // --- LOGIKA KLIK USER ---
    private void checkClick(int x, int y) {
        if (gridCells == null) return;

        for (Cell cell : gridCells) {
            if (cell.isClicked(x, y)) {
                if (cell.onClick()) {
                    // --- JAWABAN BENAR ---
                    // Hitung waktu terpakai: Batas Waktu - Sisa Waktu
                    int timeRemaining = gameTimer.getTimeRemaining(); 
                    int timeSpent = currentLevelTimeLimit - timeRemaining;
                    
                    // Tambahkan ke total (Minimal 0 agar tidak minus)
                    totalTimePlayed += Math.max(0, timeSpent);

                    // Lanjut ke level berikutnya
                    currentLevelId++;
                    loadLevel(); 

                } else {
                    // --- JAWABAN SALAH ---
                    gameOver();
                }
                return; // Keluar loop setelah klik terdeteksi
            }
        }
    }

    // --- UPDATE GUI WAKTU (Dipanggil oleh Thread Timer) ---
    public void updateTime(int time) {
        // Tampilkan info lengkap di header
        infoLabel.setText(String.format("LEVEL: %d  |  WAKTU: %ds  |  TOTAL: %.0fs", 
            currentLevelId, time, totalTimePlayed));
    }

    // --- GAME OVER (Waktu Habis / Salah Klik) ---
    public void gameOver() {
        // Penalti: Jika kalah, waktu level ini dianggap habis terpakai
        totalTimePlayed += currentLevelTimeLimit;
        
        handleGameFinish(false); // User Kalah
    }

    // --- MENANGANI HASIL AKHIR (MENANG / KALAH) ---
    private void handleGameFinish(boolean isWin) {
        isGameActive = false;
        if (gameTimer != null) gameTimer.stopTimer();

        // Level terakhir yang sukses diselesaikan
        // Jika Menang: currentLevelId - 1 (Karena ID sudah naik tapi data level habis)
        // Jika Kalah: currentLevelId - 1 (Karena level saat ini gagal)
        int levelReached = currentLevelId - 1;

        // Simpan Skor ke Database
        dao.saveScore(MainFrame.currentUser, levelReached, totalTimePlayed);

        // Tampilkan RESULT DIALOG (Panel Baru)
        SwingUtilities.invokeLater(() -> {
            new ResultDialog(mainFrame, isWin, levelReached, totalTimePlayed).setVisible(true);
            
            // Setelah dialog ditutup, kembali ke menu
            MainFrame.cardLayout.show(MainFrame.mainPanel, "Menu");
        });
    }
}