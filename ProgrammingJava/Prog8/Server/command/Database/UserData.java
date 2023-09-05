package Database;

import java.io.Serializable;
import java.net.InetAddress;
import Commands.AbstractCommands.CommandContainer;
import Data.Ticket;

public class UserData implements Serializable {
    private String login;
    private String password;
    private boolean isConnected;

    private boolean isSuccess;
    private boolean isNewUser;

    private Ticket addTicket;

    private Ticket[] tickets;

    private String info;

    private boolean isRegistration;
    private InetAddress userAddress;
    private Integer port;

    private int[] color = {0,0,0};

    private CommandContainer userCommandContainer;

    public UserData(boolean isNewUser){
        this.isNewUser = isNewUser;
        this.isConnected = false;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public boolean getIsNewUser(){
        return isNewUser;
    }
    public void setIsNewUser(boolean value) {
        this.isNewUser = value;
    }

    public CommandContainer getCommandContainer() {
        return userCommandContainer;
    }

    public void setCommandContainer(CommandContainer userCommandContainer) {
        this.userCommandContainer = userCommandContainer;
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean bool) {
        this.isConnected = bool;
    }

    public InetAddress getInetAddress() {
        return userAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.userAddress = inetAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }


    public boolean isRegistration(){
        return isRegistration;
    }



    public void setRegistration(boolean b){
        isRegistration =b;
    }



    public Ticket[] getTickets(){
        return tickets;
    }

    public void setTickets(Ticket[] tickets){
        this.tickets = tickets;
    }


    public String getInfo(){
        return info;
    }

    public void setInfo(String info){
        this.info = info;
    }



    public Ticket getAddTicket(){
        return addTicket;
    }


    public void setAddTicket(Ticket ticket){
        addTicket = ticket;
    }

    public int[] getColor(){
        return color;
    }

    public void setColor(int[] color){
        this.color = color;
    }

    public boolean isSuccess(){
        return isSuccess;
    }

    public void setSuccess(boolean b){
        isSuccess = b;
    }
}
