package Commands;
import Commands.AbstractCommands.*;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

/**
 * Класс команды, выводящей в стандартный поток вывода все билеты коллекции в формате JSON.
 */
public class ShowCommand extends Command {
    /**
     * Конструктор класса.
     */
    public ShowCommand() {
        super("show");
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute(String[] args,PrintStream printStream) throws CannotExecuteCommandException {
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
        return "Выводит в стандартный поток вывода все билеты коллекции в формате JSON.";
    }
}
