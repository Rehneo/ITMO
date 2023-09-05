package Commands;

import Commands.AbstractCommands.Command;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

public class HistoryCommand extends Command {
    public HistoryCommand(){
        super("history");
    }
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData) throws CannotExecuteCommandException {
        if (args.length > 0) {
            throw new CannotExecuteCommandException("У введенной команды нет аргументов.");
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description() {
        return "Выводит последние 12 команд (без их аргументов)";
    }
}
