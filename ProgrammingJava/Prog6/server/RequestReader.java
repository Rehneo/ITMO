import Commands.AbstractCommands.CommandContainer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestReader {
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
        container = (CommandContainer) objectInputStream.readObject();


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
