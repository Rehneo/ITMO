package Commands;

import Commands.AbstractCommands.Command;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

/**
 * Класс команды, которая выводит справку по доступным командам.
 */
public class HelpCommand extends Command {
    /**
     * Коллекция, содержащая объекты всех доступных в программе команд.
     */
    private final HashMap<String, Command> command;
    /**
     * Конструктор класса.
     */
    public HelpCommand(HashMap<String, Command> command) {
        super("help");
        this.command = command;
    }
    /**
     * Метод, исполняющий команду. Выводит список всех доступных команд с описанием.
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException {
        command.forEach((key, value) -> System.out.println(key + ": " + value.description()));
    }

    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description() {
        return "Выводит справку по доступным командам.";
    }
    
}
