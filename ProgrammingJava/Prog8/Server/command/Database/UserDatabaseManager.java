package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class UserDatabaseManager {
    private final Connection connection;

    public UserDatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public void addUser(UserData userData){
        String login = userData.getLogin();
        String password = userData.getPassword();
        int[] color = getRandomColor();
        String sql = "INSERT INTO TICKET_USERS (LOGIN, PASSWORD,red,green,blue) VALUES (?, ?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ps.setInt(3,color[0]);
            ps.setInt(4,color[1]);
            ps.setInt(5,color[2]);
            ps.executeUpdate();
            ps.close();
            userData.setColor(color);
        }catch (SQLException e){
            System.out.println("Пользователь с указанным login уже существует");
        }
    }

    public void connectUser(UserData userData){
        String login = userData.getLogin();
        String sql = "SELECT * FROM TICKET_USERS WHERE LOGIN = ?";
        userData.setColor(UserDatabaseManager.getColor(login, connection));
        userData.setIsConnected(true);
    }
    public void verifyLogin(UserData userData) {
        String login = userData.getLogin();
        String sql = "SELECT * FROM TICKET_USERS WHERE LOGIN = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            userData.setIsNewUser(!rs.isBeforeFirst());
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Ошибка подключения к базе данных. Команда verify_user.");
            throw new RuntimeException(ex);
        }
    }

    public void verifyUser(UserData userData) {
        String login = userData.getLogin();
        String password = userData.getPassword();
        String sql = "SELECT * FROM TICKET_USERS WHERE LOGIN = ? AND PASSWORD = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            userData.setIsNewUser(!rs.isBeforeFirst());
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Ошибка подключения к базе данных. Команда verify_user.");
            throw new RuntimeException(ex);
        }
    }

    private int[] getRandomColor(){
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new int[]{red,green,blue};
    }

    public static int[] getColor(String login, Connection connection){
        try{
            String sql = "SELECT * FROM TICKET_USERS WHERE LOGIN = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            int red = 0, green = 0, blue = 0;
            while (rs.next()) {
                red = rs.getInt(3);
                green = rs.getInt(4);
                blue = rs.getInt(5);
            }
            return new int[]{red,green,blue};
        }catch (SQLException e){
            System.out.println("Ошибка подключения к базе данных. Команда getColor.");
            throw new RuntimeException(e);
        }
    }
}
