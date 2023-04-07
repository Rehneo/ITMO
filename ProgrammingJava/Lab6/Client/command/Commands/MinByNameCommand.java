package Commands;
import Commands.AbstractCommands.*;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

/**
 * Класс команды, выводящей любой билет из коллекции, значение поля name которого является минимальным.
 */
public class MinByNameCommand extends Command{
    /**
     * Конструктор класса.
     */
    public MinByNameCommand() {
        super("min_by_name");
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args,PrintStream printStream) throws CannotExecuteCommandException {
            if (args.length > 0) {
                throw new CannotExecuteCommandException("У введенной команды нет аргументов. ");
            }
}
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Выводит любой билет из коллекции, значение поля name которого является минимальным.";
    }
}
