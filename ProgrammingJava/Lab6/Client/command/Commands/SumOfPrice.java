package Commands;
import Commands.AbstractCommands.*;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

/**
 * Класс команды, выводящей сумму значений поля price для всех билетов коллекции.
 */
public class SumOfPrice extends Command {
    /**
     * Конструктор класса.
     */
    public SumOfPrice() {
        super("sum_of_price");
    }
    /**
     * Метод, исполняющий команду
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
        return "Выводит сумму значений поля price для всех билетов коллекции.";
    }
}
