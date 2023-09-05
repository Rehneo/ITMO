package Commands;
import Commands.AbstractCommands.*;
import Collection.CollectionManager;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.util.concurrent.locks.Lock;

/**
 * Класс команды, выводящей в стандартный поток вывода все билеты коллекции в формате JSON.
 */
public class ShowCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    /**
     * Конструктор класса.
     */
    public ShowCommand(CollectionManager manager){
        super("show");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException {
            printStream.println(manager.show(printStream));
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
