package Commands;
import Commands.AbstractCommands.*;
import Collection.CollectionManager;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

/**
 * Класс команды, выводящей любой билет из коллекции, значение поля name которого является минимальным.
 */
public class MinByNameCommand extends Command{
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public MinByNameCommand(CollectionManager manager){
        super("min_by_name");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream) throws CannotExecuteCommandException {
        printStream.println(manager.minByName(printStream));
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
