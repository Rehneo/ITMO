package Commands;

import Collection.CollectionManager;
import Data.Ticket;
import Exceptions.InvalidInputDataException;
import Io.TicketFieldsReader;
import Io.User;
/**
 * Класс команды, которая удаляет все элементы, превышающие заданный.
 */
public class RemoveGreaterCommand implements CommandWithArgs {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Поле, хранящее ссылку на объект класса User.
     */
    private User user;
    /**
     * Поле, хранящее ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    private TicketFieldsReader ticketReader;
    /**
     * Массив, хранящий аргументы команды.
     */
    String[] args;
    /**
     * Конструктор класса
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     * @param user - Хранит ссылку на объект класса User.
     * @param ticketReader - Хранит ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    public RemoveGreaterCommand(CollectionManager manager, User user, TicketFieldsReader ticketReader){
        this.manager = manager;
        this.user = user;
        this.ticketReader = ticketReader;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute() {
        try {
            user.printText("Введите характеристики билета\n");
            Ticket ticket = ticketReader.read();
            manager.removeGreater(ticket);
            user.printText("Соответствующие билеты были успешно удалены\n");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Не указаны аргументы команды");
        } catch(NumberFormatException e){
            System.err.println("Формат аргумента не соответствует целочисленному" + e.getMessage());
        } catch (InvalidInputDataException e){
            System.err.println("В скрипте указаны неправильные данные. Не удалось удалить элементы.");
        }
    }
    /**
     * @param args -  аргументы команды.
     * @see CommandWithArgs
     */
    @Override
    public void getArgs(String[] args) {
        this.args = args;
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Удаляет из коллекции все билеты, превышающие заданный.";
    }
}
