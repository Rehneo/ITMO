package Commands;
import Commands.AbstractCommands.*;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

/**
 * Класс команды, выводящей элементы коллекции в порядке возрастания.
 */
public class PrintAscendingCommand extends Command {
    /**
     * Конструктор класса
     */
    public PrintAscendingCommand(){
        super("print_ascending");
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
        return "Выводит элементы коллекции в порядке возрастания.";
    }
}
