import Database.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serverTools.CommandExecutor;
import serverTools.ServerCommandHandler;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandProcessor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);
    private final ServerCommandHandler handler;
    private final ArrayList<UserData> commandList;

    private final HashMap<UserData, ByteArrayOutputStream> responseMap;
    ExecutorService serviceProcessor = Executors.newCachedThreadPool();
    public CommandProcessor(ServerCommandHandler handler, ArrayList<UserData> commandList,HashMap<UserData, ByteArrayOutputStream> responseMap){
        this.handler = handler;
        this.commandList = commandList;
        this.responseMap = responseMap;
    }
    @Override
    public void run() {
        while (true) {
            synchronized (commandList) {
                try {
                    while (commandList.isEmpty()) {
                        commandList.wait();
                    }
                    UserData [] commandArray = commandList.toArray(new UserData[0]);
                    for(UserData userData:commandArray){
                        startProcessing(userData);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Произошла ошибка при исполнении " + e.getMessage());
                }
            }
        }
    }
    private void startProcessing(UserData userData) {
        logger.info("Обработка запроса для " + userData.getPort() + " началась.");
        synchronized (commandList) {
            commandList.remove(userData);
        }
        CommandExecutor commandExecutor = new CommandExecutor(userData,handler, responseMap);
        serviceProcessor.submit(commandExecutor);
    }
}

