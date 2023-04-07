import Commands.AbstractCommands.CommandContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestReader {
    private static final Logger logger = LoggerFactory.getLogger(RequestReader.class);
    private final DatagramSocket socket;

    private byte[] bytes = new byte[4096];

    private InetAddress address;

    private int port;

    private final DatagramPacket packet;

    private CommandContainer container;

    public RequestReader(DatagramSocket socket){
        this.socket = socket;
        packet = new DatagramPacket(bytes, bytes.length);
    }

    public void read() throws IOException, ClassNotFoundException {
        socket.receive(packet);

        bytes = packet.getData();

        address = packet.getAddress();
        port = packet.getPort();

        String s = new String(bytes);

        s = s.replace("\0", "");

        byte[] byteArray = s.getBytes(StandardCharsets.UTF_8);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(byteArray));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        logger.info("Получен пакет с командой от " + address.getHostAddress() + ":" + port);
        container = (CommandContainer) objectInputStream.readObject();
        logger.info("Контейнер с командой получен");
    }

    public CommandContainer getContainer(){
        return container;
    }

    public InetAddress getAddress(){
        return address;
    }

    public int getPort(){
        return port;
    }
}
