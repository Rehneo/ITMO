package Commands;
import Commands.AbstractCommands.*;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

/**
 * Класс команды, которая очищает коллекцию.
 */
public class ClearCommand extends Command {
    public ClearCommand() {
        super("clear");
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream) throws CannotExecuteCommandException {
        if (args.length > 0) {
                throw new CannotExecuteCommandException("У введенной команды нет аргументов.");
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Очищает коллекцию.";
    }
}
