package Commands;
import Commands.AbstractCommands.*;
import Collection.CollectionManager;
import Database.CollectionDatabaseManager;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;

/**
 * Класс команды, которая очищает коллекцию.
 */
public class ClearCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;

    private final CollectionDatabaseManager cdm;
    /**
     * Конструктор класа, предназначенный для сервера.
     * @param manager - менеджер коллекции
     */
    public ClearCommand(CollectionManager manager, CollectionDatabaseManager cdm) {
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
            Long [] ids = cdm.getAllIdsByOwner(userData);
            cdm.clearByOwner(userData);
            for(Long k:ids){
                manager.removeById(k);
            }
            printStream.println("Все билеты, принадлежащие прользователю " + userData.getLogin() + ", были удалены.");
        }finally {
            lock.unlock();
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Очищает коллекцию.";
    }
}
