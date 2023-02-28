package Commands;

import Collection.CollectionManager;

/**
 * Класс команды, которая удаляет из коллекции все билеты, ключ которых превышает заданный.
 */
public class RemoveGreaterKeyCommand implements CommandWithArgs {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Массив, хранящий аргументы команды.
     */
    String[] args;
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public RemoveGreaterKeyCommand(CollectionManager manager){
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute() {
        try {
            manager.removeGreaterKey(Integer.parseInt(args[0]));
            System.out.println("Соответствующие билеты были успешно удалены");
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Не указаны аргументы команды.");
        } catch (NumberFormatException e) {
            System.err.println("Формат аргумента не соответствует целочисленному" + e.getMessage());
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
        return "Удаляет из коллекции все билеты, ключ которых превышает заданный.";
    }
}
