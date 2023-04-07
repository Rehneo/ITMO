package Commands;
import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Data.Ticket;
import Exceptions.CannotExecuteCommandException;
import java.io.PrintStream;

/**
 * Класс команды, которая добавляет новый билет в коллекцию с заданным ключом.
 */
public class InsertTicketCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    public InsertTicketCommand(CollectionManager manager){
        super("insert");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream) throws CannotExecuteCommandException {
        Ticket ticket = (Ticket) this.getResult().get(1);
        manager.insert((Integer) this.getResult().get(0), ticket,printStream);
    }

    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Добавляет новый билет в коллекцию с заданным ключом.";
    }
}
