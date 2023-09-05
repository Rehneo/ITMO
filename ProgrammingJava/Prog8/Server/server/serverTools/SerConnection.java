package serverTools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramSocket;
import java.net.SocketException;

public class SerConnection {
    private static final Logger logger = LoggerFactory.getLogger(SerConnection.class);
    private DatagramSocket socket;
    public boolean create(Integer port){
        try{
            socket = new DatagramSocket(port);
            logger.info("Сервер готов к работе");
            return true;
        } catch (SocketException e){
            logger.warn("Сокет не может быть открыт. Возможно занят порт: " + port);
        }
        return false;
    }

    public DatagramSocket getSocket(){
        return socket;
    }
}
