package serverTools;

import Commands.AbstractCommands.CommandContainer;
import Commands.CommandInvoker;
import Database.CollectionDatabaseManager;
import Database.UserData;
import Database.UserDatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

public class ServerCommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(ServerCommandHandler.class);
    private final CommandInvoker invoker;

    private final UserDatabaseManager userDatabaseManager;
    private final CollectionDatabaseManager cdm;

    public ServerCommandHandler(CommandInvoker invoker,CollectionDatabaseManager cdm, UserDatabaseManager userDatabaseManager){
        this.invoker = invoker;
        this.cdm = cdm;
        this.userDatabaseManager = userDatabaseManager;
    }

    public void execute(CommandContainer container, PrintStream printStream, UserData userData){
        if (invoker.executeServer(container.getName(), container.getResult(),printStream, userData)){
            logger.info("Была исполнена команда: " + container.getName());
        }else{
            logger.info("Не удалось исполнить команду " + container.getName());
        }
    }

    public CommandInvoker getInvoker(){
        return invoker;
    }

    public CollectionDatabaseManager getCdm(){
        return cdm;
    }

    public UserDatabaseManager getUserDatabaseManager(){
        return userDatabaseManager;
    }
}
