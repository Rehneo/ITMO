package serverTools;

import Data.Ticket;
import Database.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class CommandExecutor implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    private final UserData userData;
    private final ServerCommandHandler handler;
    private final HashMap<UserData, ByteArrayOutputStream> responseMap;

    public CommandExecutor(UserData userData, ServerCommandHandler handler, HashMap<UserData, ByteArrayOutputStream> responseMap) {
        this.userData = userData;
        this.handler = handler;
        this.responseMap = responseMap;
    }


    @Override
    public void run() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if(userData.getCommandContainer() != null){
                PrintStream printStream = new PrintStream(byteArrayOutputStream);
                userData.setSuccess(false);
                if(handler.getInvoker().executeServer(userData.getCommandContainer().getName(),userData.getCommandContainer().getResult(),
                        printStream,userData)){
                    if(userData.getIsConnected()){
                        userData.setTickets(handler.getCdm().loadFromDatabase().values().toArray(new Ticket[0]));
                    }
                    userData.setInfo(byteArrayOutputStream.toString(StandardCharsets.UTF_8));
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(userData);
                    objectOutputStream.close();
                    logger.info("Была исполнена команда " + userData.getCommandContainer().getName());
                }else {
                    logger.info("Не удалось исполнить команду " + userData.getCommandContainer().getName());
                }
            }else {
                if(userData.getIsConnected()){
                    userData.setTickets(handler.getCdm().loadFromDatabase().values().toArray(new Ticket[0]));
                }
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(userData);
                objectOutputStream.close();
            }
            synchronized (responseMap){
                responseMap.put(userData,byteArrayOutputStream);
                responseMap.notify();
            }
        }catch (IOException e){
            logger.error("Возникла ошибка при отправки ответа клиенту " + e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
