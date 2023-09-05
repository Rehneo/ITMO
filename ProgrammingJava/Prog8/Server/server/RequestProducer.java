import Database.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serverTools.RequestReader;
import serverTools.ServerCommandHandler;

import java.io.IOException;
import java.util.ArrayList;
public class RequestProducer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestProducer.class);
    private final RequestReader requestReader;
    private final ServerCommandHandler handler;
    private final ArrayList<UserData> commandList;
    public  RequestProducer(RequestReader requestReader, ServerCommandHandler handler,ArrayList<UserData> commandList ){
        this.handler = handler;
        this.requestReader = requestReader;
        this.commandList = commandList;
    }
    @Override
    public void run() {
        while (true) {
            try {
                UserData userData = requestReader.read();
                findNextStep(userData);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Произошла ошибка при чтении запроса " + e.getMessage());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void findNextStep(UserData userData) {
        if(!userData.isRegistration()) {
            if (!userData.getIsConnected()) {
                if (userData.getIsNewUser()) {
                    handler.getUserDatabaseManager().addUser(userData);
                }
                if (!userData.getIsConnected()) {
                    handler.getUserDatabaseManager().verifyUser(userData);
                }
                if (!userData.getIsNewUser()) {
                    handler.getUserDatabaseManager().connectUser(userData);
                }
            }
        }else{
            if (!userData.getIsConnected()) {
                if (userData.getIsNewUser()) {
                    handler.getUserDatabaseManager().verifyLogin(userData);
                }
                if (userData.getIsNewUser()) {
                    handler.getUserDatabaseManager().addUser(userData);
                    handler.getUserDatabaseManager().connectUser(userData);
                }
            }
        }
        synchronized (commandList) {
            commandList.add(userData);
            commandList.notify();
        }
    }
}
