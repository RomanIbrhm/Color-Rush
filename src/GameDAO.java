import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    public boolean login(String username, String password) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return false;

        try {
            String checkQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(checkQuery);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            return rs.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String register(String username, String password) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return "Database Error";

        try {
            String checkQuery = "SELECT * FROM users WHERE username = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkQuery);
            psCheck.setString(1, username);
            if (psCheck.executeQuery().next()) {
                return "Username sudah dipakai!";
            }

            String regQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement psReg = conn.prepareStatement(regQuery);
            psReg.setString(1, username);
            psReg.setString(2, password);
            psReg.executeUpdate();
            
            return "Sukses";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Gagal Register: " + e.getMessage();
        }
    }
    

    public LevelData getLevel(int stage) {
    LevelData levelData = null;
    Connection conn = DBConnection.getConnection();
    if (conn == null) return null;

    try {
        String query = "SELECT * FROM level_pool WHERE level_stage = ? ORDER BY RAND() LIMIT 1";
        
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, stage);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            levelData = new LevelData(
                rs.getInt("level_stage"), 
                rs.getInt("grid_size"),
                rs.getString("base_color"),
                rs.getString("target_color"),
                rs.getInt("time_limit")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return levelData;
}

    public void saveScore(String username, int score, double totalTime) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return;
            String query = "INSERT INTO leaderboard (username, score, total_time) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setInt(2, score);
            ps.setDouble(3, totalTime);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<String> getLeaderboard() {
        List<String> list = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return list;
            
            String query = """
                SELECT u.username, l.score, l.total_time 
                FROM leaderboard l
                JOIN users u ON l.user_id = u.id
                ORDER BY l.score DESC, l.total_time ASC
                LIMIT 10
            """;
            
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            int rank = 1;
            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");
                double totalTime = rs.getDouble("total_time");

                double avgTime = (score > 0) ? (totalTime / score) : 0.0;

                list.add(String.format(
                    "%d. %s - Lvl %d (Total: %.1fs | Avg: %.1fs)",
                    rank, username, score, totalTime, avgTime
                ));
                
                rank++;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }

}