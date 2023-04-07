import Commands.CommandInvoker;

public class CommandHandler {
    private final CommandInvoker invoker;
    public CommandHandler(CommandInvoker invoker) {
        this.invoker = invoker;
    }

    public boolean execute(String firstCommandLine) {

        if (!invoker.executeClient(firstCommandLine, System.out)) {
            return false;
        } else {
            return !invoker.getLastContainer().getName().equals("help");
        }
    }
}
