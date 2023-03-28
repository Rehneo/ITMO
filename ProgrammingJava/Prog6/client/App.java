import Commands.CommandInvoker;
import Io.User;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.NoSuchElementException;
public class App {
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
            boolean isConnected = true;
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
                        ByteBuffer byteBuffer = responseReader.receive();
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), StandardCharsets.UTF_8).trim() + "\n");
                        isNeedInput = true;
                    }
                } catch (IOException e) {
                    if (e instanceof PortUnreachableException) {
                        System.out.println("Порт " + port + " не доступен. Повторить отправку команды? y/n");
                    } else {
                        System.out.println("Сервер не отвечает. Повторить отправку команды? y/n");
                    }
                    String result = user.readLine().trim().toLowerCase(Locale.ROOT).split("\\s+")[0];
                    if (result.equals("n")) {
                        System.out.println("Завершение работы клиента");
                        isConnected = false;
                    } else {
                        isNeedInput = false;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nАварийное завершение работы.");
        } catch (SocketException e) {
            System.out.println("Ошибка подключения сокета к порту, или сокет не может быть открыт."
                    + e.getMessage() + "/n" + "localhost" + " ; " + port);
        } catch (IllegalArgumentException ex) {
            System.out.println("Указан недопустимый порт: " + port);
        } catch (Exception e) {
            System.out.println("Неизвестная ошибка." + e);
        }
    }
}
