package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Database.CollectionDatabaseManager;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;

/**
 * Класс команды, которая удаляет из коллекции все билеты, ключ которых превышает заданный.
 */
public class RemoveGreaterKeyCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    private final CollectionDatabaseManager cdm;
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public RemoveGreaterKeyCommand(CollectionManager manager, CollectionDatabaseManager cdm){
        super("remove_greater_key");
        this.manager = manager;
        this.cdm = cdm;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException, SQLException {
            Integer id = (Integer) this.getResult().get(0);
            try {
                lock.lock();
                Long[] ids = manager.removeGreaterKey(id,printStream);
                int counter = 0;
                if(ids != null && ids.length > 0){
                    for(Long k:ids){
                        if(cdm.isOwner(k,userData)){
                            cdm.deleteRowById(k);
                            manager.removeById(k);
                            counter++;
                        }
                    }
                    if(counter == 0){
                        printStream.println("Нет билетов, принадлежащих вам и ключ которых превышает заданный");
                    }else{
                        printStream.println("Соответствующие билеты были удалены");
                    }
                }else{
                    printStream.println("Нет билетов, ключ которых превышает заданный.");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                System.out.println("DONE-12");
                lock.unlock();
            }

    }

    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Удаляет из коллекции все билеты, ключ которых превышает заданный.";
    }
}
