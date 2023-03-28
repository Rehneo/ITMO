import java.net.DatagramSocket;
import java.net.SocketException;

public class SerConnection {
    private DatagramSocket socket;

    public boolean create(Integer port){
        try{
            socket = new DatagramSocket(port);
            return true;
        } catch (SocketException e){
            System.out.println("Сокет не может быть открыт. Возможно занят порт: " + port);
            return false;
        }
    }

    public DatagramSocket getSocket(){
        return socket;
    }
}
