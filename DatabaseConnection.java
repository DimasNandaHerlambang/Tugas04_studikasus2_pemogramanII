import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "http://localhost/phpmyadmin/index.php?route=/server/databases";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}