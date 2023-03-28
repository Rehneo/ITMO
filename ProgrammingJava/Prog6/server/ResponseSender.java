import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ResponseSender {
    private final DatagramSocket socket;

    public ResponseSender(DatagramSocket socket){
        this.socket = socket;
    }

    public void send(String s, InetAddress address, int port) throws IOException {
        byte[] bytes = new byte[4096];
        DatagramPacket packet = new DatagramPacket(bytes,bytes.length,address,port);

        byte[] byteArray = s.getBytes(StandardCharsets.UTF_8);
        if (byteArray.length > 4096){
            System.out.println("Размер пакета слишком большой");
            return;
        } else{
            System.arraycopy(byteArray,0,bytes,0,byteArray.length);
        }
        socket.send(packet);
    }
}
