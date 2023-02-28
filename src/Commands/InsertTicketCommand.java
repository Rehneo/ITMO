package Commands;

import Collection.CollectionManager;
import Data.Ticket;
import Exceptions.InvalidInputDataException;
import Io.TicketFieldsReader;
import Io.User;
/**
 * Класс команды, которая добавляет новый билет в коллекцию с заданным ключом.
 */
public class InsertTicketCommand implements CommandWithArgs{
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
     * 
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     * @param user      - Хранит ссылку на объект класса User.
     * @param ticketReader - Хранит ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    public InsertTicketCommand(CollectionManager manager, User user, TicketFieldsReader ticketReader){
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
            int k = Integer.parseInt(args[0]);
            if (!manager.containsKey(k)){
                user.printText("Введите характеристики билета\n");
                Ticket ticket = ticketReader.read();
                manager.insert(k, ticket);
                user.printText("Билет добавлен в коллекцию\n");
            } else user.printText("Билет с заданным ключом уже существует!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Не указаны аргументы команды");
        } catch(NumberFormatException e){
            System.err.println("Формат аргумента не соответствует целочисленному" + e.getMessage());
        } catch (InvalidInputDataException e){
            System.err.println("В скрипте указаны неправильные данные. Не удалось добавить новый элемент в коллекцию.");
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
        return "Добавляет новый билет в коллекцию с заданным ключом.";
    }
}
