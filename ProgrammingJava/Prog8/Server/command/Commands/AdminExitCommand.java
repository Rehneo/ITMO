package Commands;

import Commands.AbstractCommands.Command;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.util.concurrent.locks.Lock;

public class AdminExitCommand extends Command {
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException {
        if (args.length > 0) {
            throw new CannotExecuteCommandException("У введенной команды нет аргументов.");
        }else{
            System.out.println("Работа сервера завершена.");
            System.exit(0);
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Завершает работу программы.";
    }
}
