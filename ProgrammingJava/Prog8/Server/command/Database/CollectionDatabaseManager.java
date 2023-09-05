package Database;

import Data.Coordinates;
import Data.Event;
import Data.Ticket;
import Data.TicketType;
import Database.UserData;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeMap;

public class CollectionDatabaseManager {
    private final Connection connection;

    public CollectionDatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public void insertRow(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO TICKETS(ID,NAME,COORDINATE_X,COORDINATE_Y, CREATION_DATE, PRICE, REFUNDABLE, TICKET_TYPE, EVENT_ID, EVENT_NAME, EVENT_DESCRIPTION, OWNER)" +
                " VALUES (?,?,?,?,?,?,?,?::tickettypes,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1,ticket.getId());
        ps.setString(2, ticket.getName());
        ps.setFloat(3,ticket.getCoordinatesX());
        ps.setFloat(4,ticket.getCoordinatesY());
        ps.setTimestamp(5, Timestamp.valueOf(ticket.getCreationDate()));
        if (ticket.getPrice() != null){
            ps.setLong(6, ticket.getPrice());
        }else{
            ps.setNull(6, Types.NULL);
        }
        ps.setBoolean(7,ticket.isRefundable());
        ps.setString(8, ticket.getType().name());
        ps.setLong(9, ticket.getEventId());
        ps.setString(10, ticket.getEventName());
        if(ticket.getEventDescription() != null){
            ps.setString(11, ticket.getEventDescription());
        }else{
            ps.setNull(11, Types.NULL);
        }
        ps.setString(12,ticket.getOwner());
        ps.executeUpdate();
        ps.close();
    }
    public void deleteRowById(Long id) throws SQLException {
        String sql = "DELETE FROM TICKETS WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1,id);
        ps.executeUpdate();
        ps.close();
    }

    public boolean containsId(Long id) throws SQLException {
        String sql = "SELECT * FROM TICKETS WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.isBeforeFirst();
    }

    public boolean isOwner(Long id, UserData userData) throws SQLException {
        String sql = "SELECT * FROM TICKETS WHERE ID = ? AND OWNER = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ps.setString(2, userData.getLogin());
        ResultSet rs = ps.executeQuery();
        return rs.isBeforeFirst();
    }

    public void clearByOwner(UserData userData) throws SQLException {
        String sql = "DELETE FROM TICKETS WHERE OWNER = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, userData.getLogin());
        ps.close();
    }
    public Long generateId() throws SQLException {
        Statement stat = connection.createStatement();
        ResultSet rs = stat.executeQuery("SELECT nextval('ID_GENERATOR')");
        Long id = null;
        while (rs.next()) {
            id = rs.getLong(1);
        }
        return id;
    }

    public Long[] getAllIdsByOwner(UserData userData) throws SQLException {
        String sql = "SELECT * FROM TICKETS WHERE OWNER = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, userData.getLogin());
        ResultSet rs = ps.executeQuery();
        ArrayList<Long> ids = new ArrayList<>();
        while (rs.next()) {
            ids.add(rs.getLong(1));
        }
        return ids.toArray(new Long[0]);
    }

    private Ticket createTicketFromCurrentRow(ResultSet resultSet) throws SQLException{
        Long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        Float x = resultSet.getFloat(3);
        Float y = resultSet.getFloat(4);
        LocalDateTime creationDate = resultSet.getTimestamp(5).toLocalDateTime();
        Long price = resultSet.getLong(6);
        boolean refundable = resultSet.getBoolean(7);
        TicketType type = TicketType.valueOf(resultSet.getString(8));
        Long eventId = resultSet.getLong(9);
        String eventName = resultSet.getString(10);
        String description = resultSet.getString(11);
        String owner = resultSet.getString(12);

        return new Ticket(id,name,new Coordinates(x,y),creationDate,price,refundable,type,new Event(eventId,eventName,description),owner);
    }

    public TreeMap<Integer,Ticket> loadFromDatabase() throws SQLException {
        TreeMap<Integer, Ticket> treeMap = new TreeMap<>();
        String sql = "SELECT * FROM TICKETS";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                Ticket ticket = this.createTicketFromCurrentRow(rs);
                ticket.setColor(UserDatabaseManager.getColor(ticket.getOwner(),connection));
                treeMap.put(Math.toIntExact(ticket.getId()),ticket);
            }
        }
        return treeMap;
    }
}
