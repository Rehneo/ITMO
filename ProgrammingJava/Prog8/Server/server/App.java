import Collection.CollectionManager;
import Commands.CommandInvoker;
import Database.CollectionDatabaseManager;
import Database.DatabaseConnection;
import Database.UserData;
import Database.UserDatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serverTools.RequestReader;
import serverTools.ResponseSender;
import serverTools.SerConnection;
import serverTools.ServerCommandHandler;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private Connection connection;

    private final Scanner scanner = new Scanner(System.in);

    private final HashMap<UserData, ByteArrayOutputStream> responseMap = new HashMap<>();
    private final ArrayList<UserData> commandList = new ArrayList<>();

    public void start(Integer port){
        try {
            this.createDatabaseConnection();
            UserDatabaseManager userDatabaseManager = new UserDatabaseManager(connection);
            CollectionDatabaseManager collectionDatabaseManager = new CollectionDatabaseManager(connection);
            CollectionManager collectionManager = new CollectionManager(collectionDatabaseManager.loadFromDatabase());
            logger.info("Коллекция была успешна загружена в память из базы данных");
            Lock lock = new ReentrantLock();
            CommandInvoker invoker = new CommandInvoker(collectionManager,collectionDatabaseManager,lock);
            UserData admin = new UserData(false);
            admin.setLogin("admin");
            Thread adminThread = new Thread(new Runnable()
            {
                public void run() //Этот метод будет выполняться в побочном потоке
                {
                    while (true){
                        String line = scanner.nextLine().trim();
                        invoker.executeAdmin(line, System.out, admin);
                    }
                }
            });
            SerConnection serConnection = new SerConnection();
            serConnection.create(port);
            RequestReader requestReader = new RequestReader(serConnection.getSocket());
            ResponseSender responseSender = new ResponseSender(serConnection.getSocket());
            ServerCommandHandler serverCommandHandler = new ServerCommandHandler(invoker,collectionDatabaseManager,userDatabaseManager);
            Thread commandProcessor = new Thread(new CommandProcessor(serverCommandHandler,commandList,responseMap));
            Thread responseProducer = new Thread(new ResponseProducer(responseSender,responseMap));
            adminThread.start();
            Thread thread1 = new Thread(new RequestProducer(new RequestReader(serConnection.getSocket()),serverCommandHandler,commandList));
            thread1.start();
            Thread thread2 = new Thread(new RequestProducer(new RequestReader(serConnection.getSocket()),serverCommandHandler,commandList));
            thread2.start();
            Thread thread3 = new Thread(new RequestProducer(new RequestReader(serConnection.getSocket()),serverCommandHandler,commandList));
            thread3.start();
            Thread thread4 = new Thread(new RequestProducer(requestReader,serverCommandHandler,commandList));
            thread4.start();
            commandProcessor.start();
            responseProducer.start();
        } catch (SQLException e){
            logger.error("Возникла ошибка при работе с базой данных. Завершение работы сервера...");
            System.exit(-10);
        }
    }


    public void createDatabaseConnection(){
        Scanner scanner = new Scanner(System.in);
        String jdbcLocalUrl = "jdbc:postgresql://localhost:5433/studs";
        String login = "";
        String password = "";
        try {
            scanner = new Scanner(new FileReader("C:\\Users\\Microsoft\\Desktop\\Current\\Programming\\Prog7\\pgpass.txt"));
        } catch (FileNotFoundException e) {
            logger.error("Не найден файл pgpass. Завершение работы сервера...");
            System.exit(-1);
        }
        try {
            login = scanner.nextLine().trim();
            password = scanner.nextLine().trim();
        }catch (NoSuchElementException e){
            logger.error("Не найдены данные для входа. Завершение работы сервера...");
            System.exit(-1);
        }
        DatabaseConnection databaseConnection = new DatabaseConnection(jdbcLocalUrl, login,password);
        try {
            connection = databaseConnection.connectToDatabase();
            logger.info("Соединение с базой данных studs установлено");
        }catch (SQLException e){
            logger.error("Не удалось соединиться с базой данных. Завершение работы сервера...");
            System.exit(-1);
        }
    }
}
