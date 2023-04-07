package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Exceptions.CannotExecuteCommandException;
import java.io.PrintStream;

/**
 * Класс команды, которая удаляет из коллекции все билеты, ключ которых превышает заданный.
 */
public class RemoveGreaterKeyCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public RemoveGreaterKeyCommand(CollectionManager manager){
        super("remove_greater_key");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute(String[] args,PrintStream printStream) throws CannotExecuteCommandException {
            Integer id = (Integer) this.getResult().get(0);
            manager.removeGreaterKey(id,printStream);
    }

    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Удаляет из коллекции все билеты, ключ которых превышает заданный.";
    }
}
