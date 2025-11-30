import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/color_rush";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL tidak ditemukan! Pastikan library .jar sudah dipasang.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Gagal koneksi ke Database!");
            e.printStackTrace();
            return null;
        }
    }

    public static void initialize() {
        String createDatabase = "CREATE DATABASE IF NOT EXISTS color_rush";
        String useDatabase = "USE color_rush";

        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INT NOT NULL AUTO_INCREMENT,
                username VARCHAR(50) NOT NULL UNIQUE,
                password VARCHAR(100) NOT NULL,
                PRIMARY KEY (id)
            );
        """;

       String createLeaderboardTable = """
            CREATE TABLE IF NOT EXISTS leaderboard (
                id INT AUTO_INCREMENT PRIMARY KEY,
                user_id INT NOT NULL,
                score INT NOT NULL,
                level_reached INT NOT NULL,
                total_time DOUBLE NOT NULL,
                played_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id)
            );
        """;


        String createLevelPoolTable = """
            CREATE TABLE IF NOT EXISTS level_pool (
                id INT NOT NULL AUTO_INCREMENT,
                level_stage INT NOT NULL,
                grid_size INT NOT NULL,
                base_color VARCHAR(10) NOT NULL,
                target_color VARCHAR(10) NOT NULL,
                time_limit INT NOT NULL,
                PRIMARY KEY (id)
            );
        """;

        String addUniqueConstraint = """
            ALTER TABLE level_pool 
            ADD UNIQUE(level_stage, grid_size, base_color, target_color);
        """;

        String insertLevelData = """
            INSERT IGNORE INTO level_pool (id, level_stage, grid_size, base_color, target_color, time_limit) VALUES
            (1, 1, 2, '#FF0000', '#FF6666', 15),
            (2, 1, 2, '#00FF00', '#66FF66', 15),
            (3, 1, 2, '#0000FF', '#6666FF', 15),
            (4, 1, 2, '#FFFF00', '#FFFF99', 15),
            (5, 1, 2, '#00FFFF', '#99FFFF', 15),
            (6, 1, 2, '#FF00FF', '#FF99FF', 15),
            (7, 1, 2, '#FFA500', '#FFCC66', 15),
            (8, 1, 2, '#800080', '#B366B3', 15),
            (9, 1, 2, '#008000', '#33CC33', 15),
            (10, 1, 2, '#000080', '#4D4DFF', 15),
            (11, 2, 3, '#B22222', '#E84545', 15),
            (12, 2, 3, '#2E8B57', '#4BB876', 15),
            (13, 2, 3, '#4682B4', '#70A4D4', 15),
            (14, 2, 3, '#D2691E', '#F4984B', 15),
            (15, 2, 3, '#9932CC', '#C26BE8', 15),
            (16, 2, 3, '#FF1493', '#FF63B1', 15),
            (17, 2, 3, '#FF4500', '#FF7A4D', 15),
            (18, 2, 3, '#20B2AA', '#5CCCCC', 15),
            (19, 2, 3, '#556B2F', '#8A9F5E', 15),
            (20, 2, 3, '#8B4513', '#BC7341', 15),
            (21, 3, 4, '#1E90FF', '#4DA6FF', 12),
            (22, 3, 4, '#32CD32', '#66E066', 12),
            (23, 3, 4, '#FFD700', '#FFE44D', 12),
            (24, 3, 4, '#8A2BE2', '#A95EF2', 12),
            (25, 3, 4, '#FF6347', '#FF8F7D', 12),
            (26, 3, 4, '#40E0D0', '#75EBE0', 12),
            (27, 3, 4, '#DA70D6', '#E99FE5', 12),
            (28, 3, 4, '#CD853F', '#E4AC76', 12),
            (29, 3, 4, '#708090', '#92A0AD', 12),
            (30, 3, 4, '#6B8E23', '#91B346', 12),
            (31, 4, 5, '#DC143C', '#E84A68', 12),
            (32, 4, 5, '#00008B', '#0000B8', 12),
            (33, 4, 5, '#006400', '#008500', 12),
            (34, 4, 5, '#8B008B', '#B300B3', 12),
            (35, 4, 5, '#FF8C00', '#FFAB40', 12),
            (36, 4, 5, '#9400D3', '#B82EE6', 12),
            (37, 4, 5, '#8B0000', '#B81414', 12),
            (38, 4, 5, '#2F4F4F', '#456666', 12),
            (39, 4, 5, '#483D8B', '#6359A3', 12),
            (40, 4, 5, '#00CED1', '#33E0E3', 12),
            (41, 5, 6, '#7B68EE', '#9284F2', 10),
            (42, 5, 6, '#00FA9A', '#33FCC0', 10),
            (43, 5, 6, '#C71585', '#D9429C', 10),
            (44, 5, 6, '#191970', '#25258E', 10),
            (45, 5, 6, '#696969', '#7D7D7D', 10),
            (46, 5, 6, '#A0522D', '#BA704C', 10),
            (47, 5, 6, '#556B2F', '#6D8244', 10),
            (48, 5, 6, '#808000', '#99991F', 10),
            (49, 5, 6, '#48D1CC', '#6CE3DF', 10),
            (50, 5, 6, '#C0C0C0', '#D6D6D6', 10),
            (51, 6, 7, '#4682B4', '#558DC0', 10),
            (52, 6, 7, '#D2691E', '#E07D38', 10),
            (53, 6, 7, '#5F9EA0', '#6EAAB0', 10),
            (54, 6, 7, '#BDB76B', '#CCC785', 10),
            (55, 6, 7, '#FF7F50', '#FF9169', 10),
            (56, 6, 7, '#6495ED', '#7AA3F0', 10),
            (57, 6, 7, '#DC143C', '#E63255', 10),
            (58, 6, 7, '#00FFFF', '#1AFFFF', 10),
            (59, 6, 7, '#000080', '#0A0A94', 10),
            (60, 6, 7, '#808080', '#8F8F8F', 10),
            (61, 7, 8, '#8B4513', '#96501E', 8),
            (62, 7, 8, '#2E8B57', '#389662', 8),
            (63, 7, 8, '#DAA520', '#E5B030', 8),
            (64, 7, 8, '#9932CC', '#A643D6', 8),
            (65, 7, 8, '#8FBC8F', '#9CC69C', 8),
            (66, 7, 8, '#483D8B', '#534896', 8),
            (67, 7, 8, '#2F4F4F', '#395959', 8),
            (68, 7, 8, '#00CED1', '#12D9DC', 8),
            (69, 7, 8, '#9400D3', '#A015DF', 8),
            (70, 7, 8, '#FF4500', '#FF5414', 8),
            (71, 8, 9, '#A9A9A9', '#B3B3B3', 7),
            (72, 8, 9, '#556B2F', '#5E7339', 7),
            (73, 8, 9, '#8B0000', '#960B0B', 7),
            (74, 8, 9, '#E9967A', '#F0A087', 7),
            (75, 8, 9, '#8FBC8F', '#99C499', 7),
            (76, 8, 9, '#483D8B', '#514693', 7),
            (77, 8, 9, '#2F4F4F', '#375757', 7),
            (78, 8, 9, '#00CED1', '#0DD8DB', 7),
            (79, 8, 9, '#9400D3', '#9D0FDC', 7),
            (80, 8, 9, '#FF1493', '#FF229D', 7),
            (81, 9, 10, '#FFFFFF', '#F5F5F5', 6),
            (82, 9, 10, '#F0FFF0', '#E0F0E0', 6),
            (83, 9, 10, '#F0FFFF', '#E0FFFF', 6),
            (84, 9, 10, '#FFF0F5', '#FFE0E5', 6),
            (85, 9, 10, '#FFFFF0', '#FFFFE0', 6),
            (86, 9, 10, '#F5F5DC', '#EBEBCC', 6),
            (87, 9, 10, '#E6E6FA', '#DCDCF0', 6),
            (88, 9, 10, '#FFFACD', '#EEE8AA', 6),
            (89, 9, 10, '#E0FFFF', '#D1EEEE', 6),
            (90, 9, 10, '#FFE4E1', '#EED5D2', 6),
            (91, 10, 11, '#000080', '#000085', 5),
            (92, 10, 11, '#800000', '#850000', 5),
            (93, 10, 11, '#006400', '#006900', 5),
            (94, 10, 11, '#4B0082', '#500087', 5),
            (95, 10, 11, '#8B008B', '#900090', 5),
            (96, 10, 11, '#FF0000', '#FF0500', 5),
            (97, 10, 11, '#00FF00', '#00F500', 5),
            (98, 10, 11, '#0000FF', '#0500FF', 5),
            (99, 10, 11, '#FFD700', '#FFDC00', 5),
            (100, 10, 11, '#008080', '#008585', 5);
        """;

        try (Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement()) {

            stmt.execute(createDatabase);
            stmt.execute(useDatabase);
            stmt.execute(createUsersTable);
            stmt.execute(createLeaderboardTable);
            stmt.execute(createLevelPoolTable);

            try {
                stmt.execute(addUniqueConstraint);
            } catch (SQLException ignored) {}

            stmt.execute(insertLevelData);

            System.out.println("✔ Database, tabel, dan seed data berhasil dibuat!");

        } catch (SQLException e) {
            System.err.println("❌ Error saat inisialisasi database: " + e.getMessage());
        }
    }

}