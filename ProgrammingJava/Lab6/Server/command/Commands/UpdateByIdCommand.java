package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Data.Ticket;
import Exceptions.CannotExecuteCommandException;
import java.io.PrintStream;
/**
 * Класс команды, обновляющей значение билета коллекции, id которого равен заданному"
 */
public class UpdateByIdCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    /**
     * Конструктор класса.
     */
    public UpdateByIdCommand(CollectionManager manager){
        super("update");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute(String[] args, PrintStream printStream) throws CannotExecuteCommandException {
        Ticket ticket = (Ticket) this.getResult().get(1);
        manager.updateById((Long) this.getResult().get(0), ticket, printStream);
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Обновляет значение билета коллекции, id которого равен заданному";
    }
}
