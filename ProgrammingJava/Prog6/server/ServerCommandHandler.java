import Commands.AbstractCommands.CommandContainer;
import Commands.CommandInvoker;

import java.io.PrintStream;

public class ServerCommandHandler {
    private final CommandInvoker invoker;

    public ServerCommandHandler(CommandInvoker invoker){
        this.invoker = invoker;
    }

    public void execute(CommandContainer container, PrintStream printStream){
        if (invoker.executeServer(container.getName(), container.getResult(),printStream)){
            System.out.println("Была исполнена команда: " + container.getName());
        }else{
            System.out.println("Не удалось исполнить команду " + container.getName());
        }
    }
}
