package ClientWork;

import Commands.CommandInvoker;
import Data.Ticket;
import Database.UserData;
import Io.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class DataSender {

    private CommandInvoker invoker;

    private UserData userData;

    private InetSocketAddress inetSocketAddress;

    private ClConnection clConnection;

    private RequestSender requestSender;

    private ResponseReader responseReader;

    private CommandHandler handler;

    private final int port;
    public DataSender(int port) {
        this.port = port;
    }

    public void connect() throws IOException {
        invoker = new CommandInvoker(new User());
        inetSocketAddress = new InetSocketAddress("localhost", port);
        clConnection = new ClConnection();
        clConnection.connect(inetSocketAddress);
        requestSender = new RequestSender(clConnection.getChannel());
        responseReader = new ResponseReader(clConnection.getChannel());
        handler = new CommandHandler(invoker);
        clConnection.isConnected();
    }



    public boolean authorize(String login, String password, boolean isRegister) throws IOException, InterruptedException, ClassNotFoundException {
        userData = new UserData(isRegister);
        userData.setRegistration(isRegister);
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
        userData.setLogin(login);
        userData.setPassword(passwordEncryptor.encryptPassword(password));
        requestSender.send(userData, inetSocketAddress);
        ByteBuffer byteBuffer = responseReader.receive();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array(), 0, byteBuffer.limit());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        userData = (UserData) objectInputStream.readObject();
        return userData.getIsConnected();
    }

    public void sendUserData() throws IOException, InterruptedException, ClassNotFoundException {
        requestSender.send(userData, inetSocketAddress);
        ByteBuffer byteBuffer = responseReader.receive();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array(), 0, byteBuffer.limit());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        userData = (UserData) objectInputStream.readObject();
        byteBuffer.flip();
    }

    public UserData getUserData(){
        return userData;
    }


    public void removeFromDatabase(int id) throws IOException, InterruptedException, ClassNotFoundException {
        handler.execute("remove_id" +" " + id, userData);
        userData.setCommandContainer(invoker.getLastContainer());
        sendUserData();
    }

    public void insertTicket(Ticket ticket, int key) throws IOException, InterruptedException, ClassNotFoundException {
        userData.setAddTicket(ticket);
        handler.execute("insert" +" " + key, userData);
        userData.setCommandContainer(invoker.getLastContainer());
        sendUserData();
    }

    public void updateTicket(Ticket ticket, long id) throws IOException, InterruptedException, ClassNotFoundException {
        userData.setAddTicket(ticket);
        handler.execute("update"+" " + id,userData);
        userData.setCommandContainer(invoker.getLastContainer());
        sendUserData();
    }
}
