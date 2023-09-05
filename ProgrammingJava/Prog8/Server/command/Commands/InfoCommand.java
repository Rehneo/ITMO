package Commands;
import Commands.AbstractCommands.*;
import Collection.CollectionManager;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.util.concurrent.locks.Lock;

/**
 * Класс команды, которая выводит основную информацию о коллекции.
 */
public class InfoCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    /**
     * Конструктор класса.
     * @param manager - хранит ссылку на объект класса CollectionManager.
     */
    public InfoCommand(CollectionManager manager){
        super("info");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException {
        printStream.println(manager.info());
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Выводит информацию о коллекции";
    }
}
