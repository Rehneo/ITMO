import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ResponseReader {
    private final DatagramChannel datagramChannel;
    private static final Logger logger = LoggerFactory.getLogger(ResponseReader.class);
    public ResponseReader(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    public ByteBuffer receive() throws IOException, InterruptedException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        int waitingTime = 0;
        while (waitingTime < 10) {
            byteBuffer.clear();
            SocketAddress from = datagramChannel.receive(byteBuffer);
            if (from != null) {
                byteBuffer.flip();
                return byteBuffer;
            }
            Thread.sleep(500);
            waitingTime++;
        }
        logger.error("Сервер не отвечает. Завершение работы клиента");
        System.exit(0);
        return byteBuffer;
    }
}
