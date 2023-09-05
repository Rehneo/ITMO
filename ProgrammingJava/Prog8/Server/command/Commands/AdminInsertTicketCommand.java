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

public class AdminInsertTicketCommand extends Command {
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
    public AdminInsertTicketCommand(CollectionManager manager, CollectionDatabaseManager cdm, User user, TicketFieldsReader ticketReader){
        this.manager = manager;
        this.user = user;
        this.ticketReader = ticketReader;
        this.cdm = cdm;
    }
    /**
     * Метод, исполняющий команду.
     */

    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException, SQLException {
        try {
            lock.lock();
            int k = Integer.parseInt(args[0]);
            if (!manager.containsKey(k)){
                user.printText("Введите характеристики билета\n");
                Ticket ticket = ticketReader.read();
                Long id = cdm.generateId();
                ticket.setId(id);
                cdm.insertRow(ticket);
                manager.insert(k, ticket, printStream);
                user.printText("Билет добавлен в коллекцию\n");
            } else user.printText("Билет с заданным ключом уже существует!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Не указаны аргументы команды");
        } catch(NumberFormatException e){
            System.err.println("Формат аргумента не соответствует целочисленному" + e.getMessage());
        } catch (InvalidInputDataException e){
            System.err.println("В скрипте указаны неправильные данные. Не удалось добавить новый элемент в коллекцию.");
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
        return "Добавляет новый билет в коллекцию с заданным ключом.";
    }
}
