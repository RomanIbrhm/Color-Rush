import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}