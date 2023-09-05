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
 * Класс команды, обновляющей значение билета коллекции, id которого равен заданному"
 */
public class UpdateByIdCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;

    private final CollectionDatabaseManager cdm;
    /**
     * Конструктор класса.
     */
    public UpdateByIdCommand(CollectionManager manager, CollectionDatabaseManager cdm){
        super("update");
        this.manager = manager;
        this.cdm = cdm;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException, SQLException {
        Ticket ticket = (Ticket) this.getResult().get(1);
        Long id = (Long) this.getResult().get(0);
        try {
            lock.lock();
            if(manager.containsId(id)){
                if(cdm.isOwner(id,userData)){
                    cdm.deleteRowById(id);
                    ticket.setId(id);
                    ticket.setOwner(userData.getLogin());
                    cdm.insertRow(ticket);
                    manager.updateById(id,ticket,printStream);
                    userData.setSuccess(true);
                }else {
                    printStream.println("Вы не имеете право изменять данный билет");
                }
            }else{
                printStream.println("Билета с указанным id уже не существует!");
            }
        }catch (Exception e){
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
        return "Обновляет значение билета коллекции, id которого равен заданному";
    }
}
