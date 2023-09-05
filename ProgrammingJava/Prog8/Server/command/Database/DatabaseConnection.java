package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final String URL;
    private final String login;
    private final String password;

    public DatabaseConnection(String URL, String login, String password) {
        this.URL = URL;
        this.login = login;
        this.password = password;
    }

    public Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(URL, login, password);
    }
}
