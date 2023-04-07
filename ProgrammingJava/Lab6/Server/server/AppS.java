import Collection.CollectionManager;
import Commands.AbstractCommands.CommandContainer;
import Commands.CommandInvoker;
import Exceptions.FileException;
import File.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class AppS {
    private static final Logger logger = LoggerFactory.getLogger(AppS.class);
    CollectionManager manager;

    FileManager fileManager;
    CommandInvoker invoker;

    SerConnection serConnection;

    private boolean isConnected = false;

    AppS(){
        manager = new CollectionManager();
        fileManager = new FileManager();
    }
    public void start(String path){

        fileManager = new FileManager(path);
        String data;
        try {
            data = fileManager.read();
            manager.deserialize(data);
            this.invoker = new CommandInvoker(manager,path);
            logger.info("Все неповреждённые элементы коллекции из файла: " + path + " были загружены.");
            serConnection = new SerConnection();
            Scanner scanner = new Scanner(System.in);
            do{
                System.out.print("Введите порт: ");
                try {
                    int port = Integer.parseInt(scanner.nextLine().trim());
                    if ((port <= 999) || (port >= 10000)) {
                        logger.error("Введён неправильный порт.");
                    } else {
                        isConnected = serConnection.create(port);
                    }
                }catch (NumberFormatException e){
                    logger.error("Введён неправильный порт.");
                }
            } while (!isConnected);
            logger.info("Порт установлен.");
        } catch (FileException e){
            logger.warn(e.getMessage());
        } catch (IOException e){
            logger.info("Не удаётся получить доступ к файлу: " + path);
            System.exit(-1);
        }
        try {
            cycle(invoker);
        } catch (NoSuchElementException | InterruptedException e){
            logger.warn(e.getMessage());
            logger.warn("Работа сервера завершена.");
        }

    }

    public void cycle(CommandInvoker invoker) throws InterruptedException {
        RequestReader requestReader = new RequestReader(serConnection.getSocket());
        ResponseSender responseSender = new ResponseSender(serConnection.getSocket());
        ServerCommandHandler handler = new ServerCommandHandler(invoker);

        while(isConnected){
            try {
                requestReader.read();
                CommandContainer container = requestReader.getContainer();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(byteArrayOutputStream);
                handler.execute(container, printStream);
                Thread.sleep(500);
                responseSender.send(byteArrayOutputStream.toString(), requestReader.getAddress(),requestReader.getPort());
                logger.info("Пакет был отправлен " + requestReader.getAddress().getHostAddress() + ":" + requestReader.getPort());
            } catch (IOException e){
                logger.warn("Ошибка при чтении: " + e.getMessage());
            } catch(ClassNotFoundException e){
                logger.error("Неизвестная ошибка: " + e);
            }
        }

    }

    public CollectionManager getManager(){
        return manager;
    }
}
