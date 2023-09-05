package Commands;
import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Data.Ticket;
import Database.CollectionDatabaseManager;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;

/**
 * Класс команды, которая добавляет новый билет в коллекцию с заданным ключом.
 */
public class InsertTicketCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;

    private final CollectionDatabaseManager cdm;
    public InsertTicketCommand(CollectionManager manager, CollectionDatabaseManager cdm){
        super("insert");
        this.manager = manager;
        this.cdm = cdm;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException, SQLException {
        try {
            lock.lock();
            Ticket ticket = (Ticket) this.getResult().get(1);
            if(!manager.containsKey((Integer) this.getResult().get(0))){
                Long id = cdm.generateId();
                ticket.setId(id);
                cdm.insertRow(ticket);
                manager.insert((Integer) this.getResult().get(0), ticket,printStream);
                userData.setSuccess(true);
            }else{
                printStream.println("Билет с указанным ключом уже существует!.");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
            finally {
            lock.unlock();
        }
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
//Ticket ticket = (Ticket) this.getResult().get(1);
//        manager.insert((Integer) this.getResult().get(0), ticket,printStream);