package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Data.Ticket;
import Exceptions.CannotExecuteCommandException;
import java.io.PrintStream;

/**
 * Класс команды, которая удаляет все элементы, превышающие заданный.
 */
public class RemoveGreaterCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    public RemoveGreaterCommand(CollectionManager manager){
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args,  PrintStream printStream) throws CannotExecuteCommandException {
        Ticket ticket = (Ticket) this.getResult().get(0);
        manager.removeGreater(ticket,printStream);
    }

    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Удаляет из коллекции все билеты, превышающие заданный.";
    }
}
