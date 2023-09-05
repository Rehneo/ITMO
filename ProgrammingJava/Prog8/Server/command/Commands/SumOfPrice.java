package Commands;
import Commands.AbstractCommands.*;
import Collection.CollectionManager;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.util.concurrent.locks.Lock;

/**
 * Класс команды, выводящей сумму значений поля price для всех билетов коллекции.
 */
public class SumOfPrice extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
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
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException {
            printStream.println(manager.sumOfPrice(printStream));
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
