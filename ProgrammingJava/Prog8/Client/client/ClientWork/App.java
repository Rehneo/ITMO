package ClientWork;

import Commands.CommandInvoker;
import Database.UserData;
import Io.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {


    private static final Logger logger = LoggerFactory.getLogger(App.class);

    User user;

    CommandInvoker invoker;


    private UserData userData;

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
            boolean isConnected = authorize(requestSender,responseReader,inetSocketAddress);
            userData.setIsConnected(isConnected);
            if(!userData.getIsConnected() && !userData.isRegistration()){
                System.out.println("Неверный логин или пароль. Повторите попытку");
            }else if (userData.getIsConnected() && userData.isRegistration()){
                System.out.println("Авторизация прошла успешно!");
            }else if (userData.getIsConnected() && !userData.isRegistration()){
                System.out.println("Регистрация прошла успешно!");
            }
            while (!userData.getIsConnected()){
                isConnected = authorize(requestSender,responseReader,inetSocketAddress);
                userData.setIsConnected(isConnected);
                if(userData.getIsConnected() && userData.getIsNewUser()){
                    System.out.println("Регистрация прошла успешно!");
                } else if (userData.getIsConnected() && !userData.getIsNewUser()) {
                    System.out.println("Авторизация прошла успешно!");
                }else if(!userData.isRegistration()) {
                    System.out.println("Неверный логин или пароль. Повторите попытку");
                }
            }
            logger.info("Клиент готов к чтению команд.");
            isConnected = clConnection.isConnected();
            boolean isNeedInput = true;
            boolean isCommandAcceptable = false;
            String line;
            while (isConnected) {
                if (isNeedInput) {
                    System.out.println("Введите название команды:");
                    line = user.readLine();
                    isCommandAcceptable = handler.execute(line, userData);
                }
                try {
                    if (isCommandAcceptable) {
                        userData.setCommandContainer(invoker.getLastContainer());
                        requestSender.send(userData, inetSocketAddress);
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

    private boolean authorize(RequestSender requestSender, ResponseReader responseReader, InetSocketAddress inetSocketAddress) throws IOException, InterruptedException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Boolean isNewUser = null;
        System.out.println("Что вы желаете сделать?: :\n1) Авторизоваться\n2) Зарегистрироваться");
        int result = 0;
        while (isNewUser == null) {
            try {
                result = Integer.parseInt(scanner.nextLine().trim());
                switch (result) {
                    case 1 ->{ isNewUser = false;userData = new UserData(isNewUser);userData.setRegistration(false);}
                    case 2 -> {isNewUser = true;userData = new UserData(isNewUser);userData.setRegistration(true);}
                    default -> logger.info("Действие не распознано. Выберите способ еще раз.");
                }
            } catch (NumberFormatException ex) {
                logger.warn("Введите целое число.");
            }
        }
        String login = null;
        String password = null;
        System.out.println("Введите логин: ");
        while (login == null || login.equals("")) {
            login = scanner.next().trim();
            if(login.equals("")){
                System.out.println("Логин не может быть пустым. Повторите попытку");
            }
        }
        System.out.println("Введите пароль: ");
        while (password == null || password.equals("")) {
            password = scanner.next().trim();
            if(password.equals("")){
                System.out.println("Пароль не может быть пустым. Повторите попытку");
            }
        }
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
        userData.setLogin(login);
        userData.setPassword(passwordEncryptor.encryptPassword(password));
        requestSender.send(userData, inetSocketAddress);
        ByteBuffer byteBuffer = responseReader.receive();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array(), 0, byteBuffer.limit());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        userData = (UserData) objectInputStream.readObject();
        if(!userData.getIsConnected() && !userData.getIsNewUser() &&(result == 2)){
            System.out.println("Пользователь с таким login уже существует!");
        }
        return userData.getIsConnected();
    }
}
