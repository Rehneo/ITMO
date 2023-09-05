package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Database.CollectionDatabaseManager;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;

public class AdminRemoveGreaterKeyCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;


    private final CollectionDatabaseManager cdm;
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public AdminRemoveGreaterKeyCommand(CollectionManager manager, CollectionDatabaseManager cdm){
        this.manager = manager;
        this.cdm = cdm;
    }
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException, SQLException {
        try {
            lock.lock();
            Long[] ids = manager.removeGreaterKey(Integer.parseInt(args[0]),printStream);
            if(ids != null && ids.length > 0){
                for(Long k:ids) {
                    cdm.deleteRowById(k);
                    manager.removeById(k);
                }
            }else{
                printStream.println("Нет билетов, ключ которых превышает заданный.");
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Не указаны аргументы команды.");
        } catch (NumberFormatException e) {
            System.err.println("Формат аргумента не соответствует целочисленному" + e.getMessage());
        }finally {
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
