
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

public class ClConnection {
    private static final Logger logger = LoggerFactory.getLogger(ClConnection.class);
    private DatagramChannel dChannel;
    public void connect(InetSocketAddress inetServerAddress) throws IOException {
        try {
            dChannel= DatagramChannel.open();
            dChannel.configureBlocking(false);
            dChannel.connect(inetServerAddress);
        } catch (IllegalArgumentException ex) {
            logger.error("Указан недопустимый порт: " + inetServerAddress.getPort());
        } catch (SocketException ex) {
            logger.error("Установка соденинения не удалась\n" + ex);
        }
    }
    public DatagramChannel getChannel() {
        return dChannel;
    }
    public boolean isConnected(){
        return dChannel.isConnected();
    }

}