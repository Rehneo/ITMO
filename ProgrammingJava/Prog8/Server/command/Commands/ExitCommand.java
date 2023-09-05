package Commands;
import Collection.CollectionManager;
import Commands.AbstractCommands.*;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;
import Exceptions.FileException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.locks.Lock;

/**
 * Класс команды, завершающей работу программы.
 */
public class ExitCommand extends Command{
    /**
     * Поле, содержащее путь к файлу, куда необходимо сохранить коллекцию.
     */
    private final String path;
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    public ExitCommand(CollectionManager manager,String path) {
        super("exit");
        this.path = path;
        this.manager = manager;
    }
    /**
     * Метод, завершающий работу клиента. При завершении выводит соответствующее сообщение.
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException {
        printStream.println("Работа клиента " + userData.getLogin() +" завершена.");
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Завершает работу клиента.";
    }
}
