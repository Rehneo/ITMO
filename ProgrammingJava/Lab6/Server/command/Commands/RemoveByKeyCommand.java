package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Exceptions.CannotExecuteCommandException;
import java.io.PrintStream;

/**
 * Класс команды, которая удаляет элемент из коллекции по его ключу.
 */
public class RemoveByKeyCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public RemoveByKeyCommand(CollectionManager manager){
        super("remove_key");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream) throws CannotExecuteCommandException {
            Integer id = (Integer) this.getResult().get(0);
            if (manager.containsKey(id)) {
                manager.removeKey(id);
                printStream.println("Билет с ключом = " + id + " был удален.");
            } else printStream.println("Билета с указанным ключом не существует.");
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Удаляет элемент из коллекции по его ключу.";
    }
    
}
