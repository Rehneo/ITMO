package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Database.CollectionDatabaseManager;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;

public class AdminRemoveByKeyCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;

    private final CollectionDatabaseManager cdm;
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public AdminRemoveByKeyCommand(CollectionManager manager, CollectionDatabaseManager cdm){
        this.manager = manager;
        this.cdm = cdm;
    }
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException, SQLException {
        try {
            lock.lock();
            if (manager.containsKey(Integer.parseInt(args[0]))) {
                Long id = manager.getIdByKey(Integer.parseInt(args[0]));
                cdm.deleteRowById(id);
                manager.removeById(id);
                System.out.println("Билет был успешно удален");
            } else System.out.println("Билета с таким ключом не существует");
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
    @Override
    public String description(){
        return "Удаляет элемент из коллекции по его ключу.";
    }
}
