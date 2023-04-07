package Commands;
import Commands.AbstractCommands.*;
import Collection.CollectionManager;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

/**
 * Класс команды, выводящей элементы коллекции в порядке возрастания.
 */
public class PrintAscendingCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    /**
     * Конструктор класса
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public PrintAscendingCommand(CollectionManager manager){
        super("print_ascending");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream) throws CannotExecuteCommandException {
        printStream.println(manager.printAscending(printStream));
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
