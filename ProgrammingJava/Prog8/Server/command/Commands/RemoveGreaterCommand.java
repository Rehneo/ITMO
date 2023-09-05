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
 * Класс команды, которая удаляет все элементы, превышающие заданный.
 */
public class RemoveGreaterCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;

    private final CollectionDatabaseManager cdm;
    public RemoveGreaterCommand(CollectionManager manager, CollectionDatabaseManager cdm){
        this.manager = manager;
        this.cdm = cdm;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException, SQLException {
        Ticket ticket = (Ticket) this.getResult().get(0);
        try {
            lock.lock();
            Long[] ids = manager.removeGreater(ticket,printStream);
            int counter = 0;
            if(ids != null && ids.length > 0){
                for(Long k:ids){
                    if(cdm.isOwner(k,userData)){
                        cdm.deleteRowById(k);
                        manager.removeById(k);
                        counter +=1;
                    }
                    if(counter == 0){
                        printStream.println("Нет билетов, принадлежащих вам и превышающих заданный");
                    }else{
                        printStream.println("Соответствующие билеты были удалены");
                    }
                }
            }else {
                printStream.println("Нет билетов, превышающих заданный.");
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
    public String description(){
        return "Удаляет из коллекции все билеты, превышающие заданный.";
    }
}
