package Commands;
import Commands.AbstractCommands.*;
import Collection.CollectionManager;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

/**
 * Класс команды, которая очищает коллекцию.
 */
public class ClearCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    /**
     * Конструктор класа, предназначенный для сервера.
     * @param manager - менеджер коллекции
     */
    public ClearCommand(CollectionManager manager) {
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream) throws CannotExecuteCommandException {
            manager.clear(printStream);
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Очищает коллекцию.";
    }
}
