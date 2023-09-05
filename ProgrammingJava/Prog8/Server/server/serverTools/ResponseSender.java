package serverTools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ResponseSender implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(ResponseSender.class);
    private final DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] bytes;

    public ResponseSender(DatagramSocket socket){
        this.socket = socket;
    }

    public ResponseSender(DatagramSocket socket,InetAddress address, int port,byte[] bytes ){
        this.socket =socket;
        this.address =address;
        this.port =port;
        this.bytes = bytes;
    }

    public void run() {
        if (bytes.length > 8192){
            logger.warn("Размер пакета превышает допустимый.");
        } else{
            byte[] byteArr = new byte[bytes.length];
            System.arraycopy(bytes, 0, byteArr, 0,byteArr.length);
            DatagramPacket packet =  new DatagramPacket(byteArr,byteArr.length,address,port);
            try {
                socket.send(packet);
            } catch (IOException e) {
                logger.warn("Произошла ошибка при отправке " + e.getMessage());
            }
        }
    }

    public DatagramSocket getSocket(){
        return socket;
    }
}
