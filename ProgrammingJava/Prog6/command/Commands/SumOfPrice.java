package Commands;
import Commands.AbstractCommands.*;
import Collection.CollectionManager;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

/**
 * Класс команды, выводящей сумму значений поля price для всех билетов коллекции.
 */
public class SumOfPrice extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Конструктор класса.
     */
    public SumOfPrice() {
        super("sum_of_price");
    }
    /**
     * Конструктор класса.
     */
    public SumOfPrice(CollectionManager manager){
        super("sum_of_price");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute(String[] args, InvocationStatus invocationEnum, PrintStream printStream) throws CannotExecuteCommandException {
        if (invocationEnum.equals(InvocationStatus.CLIENT)) {
            if (args.length > 0) {
                throw new CannotExecuteCommandException("У введенной команды нет аргументов.");
            }
        } else if (invocationEnum.equals(InvocationStatus.SERVER)) {
            printStream.println(manager.sumOfPrice());
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
