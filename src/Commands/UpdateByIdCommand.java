package Commands;

import Collection.CollectionManager;
import Data.Ticket;
import Exceptions.InvalidInputDataException;
import Io.TicketFieldsReader;
import Io.User;

/**
 * Класс команды, обновляющей значение билета коллекции, id которого равен заданному"
 */
public class UpdateByIdCommand implements CommandWithArgs {
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
     * Конструктор класса.
     * @param manager
     * @param user
     * @param ticketReader
     */
    public UpdateByIdCommand(CollectionManager manager, User user, TicketFieldsReader ticketReader){
        this.manager = manager;
        this.user = user;
        this.ticketReader = ticketReader;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute() {
        try {
            Long k = Long.parseLong(args[0]);
            if (manager.containsId(k)){
                user.printText("Введите характеристики нового билета\n");
                Ticket ticket = ticketReader.read();
                manager.updateById(k,ticket);
                user.printText("Билет был успешно обновлен\n");
            } else user.printError("Билета с таким id не существует");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Не указаны аргументы команды");
        } catch(NumberFormatException e){
            System.err.println("Формат аргумента не соответствует целочисленному" + e.getMessage());
        } catch (InvalidInputDataException e){
            System.err.println("В скрипте указаны неправильные данные. Не удалось обновить значение элемента коллекции.");
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
        return "Обновляет значение билета коллекции, id которого равен заданному";
    }
}
