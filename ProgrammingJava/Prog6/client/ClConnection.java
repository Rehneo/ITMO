import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

public class ClConnection {
    private DatagramChannel dChannel;
    public void connect(InetSocketAddress inetServerAddress) throws IOException {
        try {
            dChannel= DatagramChannel.open();
            dChannel.configureBlocking(false);
            dChannel.connect(inetServerAddress);
        } catch (IllegalArgumentException ex) {
            System.out.println("Указан недопустимый порт: " + inetServerAddress.getPort());
        } catch (SocketException ex) {
            System.out.println("Установка соденинения не удалась\n" + ex);
        }
    }
    public DatagramChannel getChannel() {
        return dChannel;
    }
}
