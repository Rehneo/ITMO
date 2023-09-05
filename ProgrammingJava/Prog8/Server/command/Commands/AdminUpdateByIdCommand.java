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

public class AdminUpdateByIdCommand extends Command {
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
    public AdminUpdateByIdCommand(CollectionManager manager,CollectionDatabaseManager cdm, User user, TicketFieldsReader ticketReader){
        this.manager = manager;
        this.user = user;
        this.ticketReader = ticketReader;
        this.cdm = cdm;
    }

    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException, SQLException {
        try {
            Long k = Long.parseLong(args[0]);
            if (manager.containsId(k)){
                user.printText("Введите характеристики нового билета\n");
                Ticket ticket = ticketReader.read();
                lock.lock();
                if(manager.containsId(k)){
                    cdm.deleteRowById(k);
                    ticket.setId(k);
                    cdm.insertRow(ticket);
                    manager.updateById(k,ticket,printStream);
                    user.printText("Билет был успешно обновлен\n");
                }else{
                    user.printError("Кто-то удалил билет с указанным id");
                }
            } else user.printError("Билета с таким id не существует");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Не указаны аргументы команды");
        } catch(NumberFormatException e){
            System.err.println("Формат аргумента не соответствует целочисленному" + e.getMessage());
        } catch (InvalidInputDataException e){
            System.err.println("В скрипте указаны неправильные данные. Не удалось обновить значение элемента коллекции.");
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
        return "Обновляет значение билета коллекции, id которого равен заданному";
    }
}
