import Collection.CollectionManager;
import Commands.AbstractCommands.CommandContainer;
import Commands.CommandInvoker;
import Exceptions.FileException;
import File.FileManager;
import Io.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AppS {
    CollectionManager manager;

    FileManager fileManager;
    User user;

    CommandInvoker invoker;

    SerConnection serConnection;

    private boolean isConnected = false;

    AppS(){
        manager = new CollectionManager();
        fileManager = new FileManager();
        user = new User();
    }

    public void start(String path){
        fileManager = new FileManager(path);
        String data;
        try {
            data = fileManager.read();
            manager.deserialize(data);
            this.invoker = new CommandInvoker(manager,path);
            serConnection = new SerConnection();
            System.out.println("yes");
            Scanner scanner = new Scanner(System.in);
            do{
                System.out.print("Введите порт: ");
                int port = scanner.nextInt();
                if (port <= 0){
                    System.out.println("Введён неправильный порт.");
                }else{
                    isConnected = serConnection.create(port);
                }
            } while (!isConnected);
        } catch (FileException e){
            System.out.println(e.getMessage());
        } catch (IOException e){
            System.out.println("Не удаётся получить доступ к файлу");
        }
        try {
            cycle(invoker);
        } catch (NoSuchElementException | InterruptedException e){
            System.out.println(e.getMessage());
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
                Thread.sleep(1000);
                responseSender.send(byteArrayOutputStream.toString(), requestReader.getAddress(),requestReader.getPort());
            } catch (IOException e){
                System.out.println("Ошибка при чтении");
            } catch(ClassNotFoundException e){
                System.out.println("ошибка");
            }
        }

    }

    public CollectionManager getManager(){
        return manager;
    }
}
