package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Data.Ticket;
import Database.CollectionDatabaseManager;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;
import Exceptions.InvalidInputDataException;
import Io.TicketFieldsReader;
import Io.User;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;

public class AdminRemoveGreaterCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;

    private final CollectionDatabaseManager cdm;
    /**
     * Поле, хранящее ссылку на объект класса User.
     */
    private final User user;
    /**
     * Поле, хранящее ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    private final TicketFieldsReader ticketReader;
    /**
     * Конструктор класса
     *
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     * @param user      - Хранит ссылку на объект класса User.
     * @param ticketReader - Хранит ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    public AdminRemoveGreaterCommand(CollectionManager manager, CollectionDatabaseManager cdm, User user, TicketFieldsReader ticketReader){
        this.manager = manager;
        this.user = user;
        this.ticketReader = ticketReader;
        this.cdm = cdm;
    }
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException, SQLException {
        try {
            user.printText("Введите характеристики билета\n");
            Ticket ticket = ticketReader.read();
            lock.lock();
            Long[] ids = manager.removeGreater(ticket,printStream);
            if(ids != null && ids.length > 0){
                for(Long k:ids){
                    cdm.deleteRowById(k);
                    manager.removeById(k);
                }
            }else{
                System.out.println("Нет билетов, превышающих заданный.");
            }
            user.printText("Соответствующие билеты были успешно удалены\n");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Не указаны аргументы команды");
        } catch(NumberFormatException e){
            System.err.println("Формат аргумента не соответствует целочисленному" + e.getMessage());
        } catch (InvalidInputDataException e){
            System.err.println("В скрипте указаны неправильные данные. Не удалось удалить элементы.");
        }finally {
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
