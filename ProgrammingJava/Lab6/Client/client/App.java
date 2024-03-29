import Commands.CommandInvoker;
import Io.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.NoSuchElementException;
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    User user;
    CommandInvoker invoker;
    private final int port;
    public App(Integer port){
        this.port = port;
        user = new User();
        invoker = new CommandInvoker(user);
    }
    public void start() {
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", port);
            ClConnection clConnection = new ClConnection();
            clConnection.connect(inetSocketAddress);
            RequestSender requestSender = new RequestSender(clConnection.getChannel());
            ResponseReader responseReader = new ResponseReader(clConnection.getChannel());
            CommandHandler handler = new CommandHandler(invoker);
            logger.info("Клиент готов к чтению команд.");
            boolean isConnected = clConnection.isConnected();
            boolean isNeedInput = true;
            boolean isCommandAcceptable = false;
            String line;
            while (isConnected) {
                if (isNeedInput) {
                    System.out.println("Введите название команды:");
                    line = user.readLine();
                    isCommandAcceptable = handler.execute(line);
                }
                try {
                    if (isCommandAcceptable) {
                        requestSender.send(invoker.getLastContainer(), inetSocketAddress);
                        logger.info("Данные были отправлены.");
                        ByteBuffer byteBuffer = responseReader.receive();
                        byteBuffer.flip();
                        logger.info("Данные были получены.");
                        System.out.println(new String(byteBuffer.array(), StandardCharsets.UTF_8).trim() + "\n");
                        if(invoker.getLastContainer().getName().equals("exit")){
                            System.exit(0);
                        }
                        isNeedInput = true;
                    }
                } catch (IOException e) {
                    if (e instanceof PortUnreachableException) {
                        System.out.println("Порт " + port + " не доступен. Повторить отправку команды? y/n");
                    } else {
                        System.out.println("Сервер не отвечает. Повторить отправку команды? y/n");
                    }
                    while (true) {
                        String result = user.readLine().trim().toLowerCase(Locale.ROOT).split("\\s+")[0];
                        if (result.equals("n")) {
                            logger.info("Завершение работы клиента");
                            isConnected = false;
                            break;
                        } else if(result.equals("y")) {
                            isNeedInput = false;
                            break;
                        } else{
                            System.out.println("Введите y или n");
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (NoSuchElementException e) {
            logger.error("\nАварийное завершение работы.");
        } catch (SocketException e) {
            logger.error("Ошибка подключения сокета к порту, или сокет не может быть открыт."
                    + e.getMessage() + "/n" + "localhost" + " ; " + port);
        } catch (IllegalArgumentException ex) {
            logger.error("Указан недопустимый порт: " + port);
        } catch (Exception e) {
            logger.error("Неизвестная ошибка." + e);
        }
    }
}
