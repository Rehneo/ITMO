import Commands.AbstractCommands.CommandContainer;
import Commands.CommandInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

public class ServerCommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(AppS.class);
    private final CommandInvoker invoker;

    public ServerCommandHandler(CommandInvoker invoker){
        this.invoker = invoker;
    }

    public void execute(CommandContainer container, PrintStream printStream){
        if (invoker.executeServer(container.getName(), container.getResult(),printStream)){
            logger.info("Была исполнена команда: " + container.getName());
        }else{
            logger.info("Не удалось исполнить команду " + container.getName());
        }
    }
}
