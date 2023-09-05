package ClientWork;

import Commands.AbstractCommands.CommandContainer;
import Database.UserData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Base64;

public class RequestSender {
    private final DatagramChannel datagramChannel;
    public RequestSender(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }
    public void send(UserData userData, InetSocketAddress inetSocketAddress) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
        byteBuffer.clear();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(userData);
        byteBuffer.put(Base64.getEncoder().withoutPadding().encode(byteArrayOutputStream.toByteArray()));
        byteBuffer.flip();
        datagramChannel.send(byteBuffer, inetSocketAddress);
    }
}

