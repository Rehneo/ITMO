package ClientWork;

import Commands.CommandInvoker;
import Database.UserData;

public class CommandHandler {
    private final CommandInvoker invoker;
    public CommandHandler(CommandInvoker invoker) {
        this.invoker = invoker;
    }

    public boolean execute(String firstCommandLine, UserData userData) {

        if (!invoker.executeClient(firstCommandLine, System.out, userData)) {
            return false;
        } else {
            return !invoker.getLastContainer().getName().equals("help");
        }
    }
}
