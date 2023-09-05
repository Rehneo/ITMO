package serverTools;

import Database.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestReader {
    private static final Logger logger = LoggerFactory.getLogger(RequestReader.class);
    private final DatagramSocket socket;

    private byte[] bytes = new byte[4096];
    private final DatagramPacket packet;

    public RequestReader(DatagramSocket socket){
        this.socket = socket;
        packet = new DatagramPacket(bytes, bytes.length);
    }

    public UserData read() throws IOException, ClassNotFoundException{
        socket.receive(packet);
        bytes = packet.getData();
        String s = new String(bytes);
        s = s.replace("\0", "");
        byte[] byteArray = s.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(byteArray));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        logger.info("Получен пакет с командой от " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
        UserData userData = (UserData) objectInputStream.readObject();
        userData.setInetAddress(packet.getAddress());
        userData.setPort(packet.getPort());
        System.out.println("yes");
        return userData;
    }

}
