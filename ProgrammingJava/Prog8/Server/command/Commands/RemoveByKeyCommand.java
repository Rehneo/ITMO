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
 * Класс команды, которая удаляет элемент из коллекции по его ключу.
 */
public class RemoveByKeyCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;

    private final CollectionDatabaseManager cdm;
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public RemoveByKeyCommand(CollectionManager manager, CollectionDatabaseManager cdm){
        super("remove_key");
        this.manager = manager;
        this.cdm = cdm;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException, SQLException {
            Integer id = (Integer) this.getResult().get(0);
            try {
                lock.lock();
                if (manager.containsKey(id)) {
                    Long realId = manager.getIdByKey(id);
                    if(cdm.isOwner(realId, userData)) {
                        cdm.deleteRowById(realId);
                        manager.removeKey(id);
                        printStream.println("Билет с ключом = " + id + " был удален.");
                    }else{
                        printStream.println("Вы не имеете право удалять данный билет");
                    }
                } else printStream.println("Билета с указанным ключом не существует.");
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
        return "Удаляет элемент из коллекции по его ключу.";
    }
    
}
