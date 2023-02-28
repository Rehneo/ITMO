package Commands;

import Collection.CollectionManager;
/**
 * Класс команды, которая удаляет элемент из коллекции по его ключу.
 */
public class RemoveByKeyCommand implements CommandWithArgs{
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
    public RemoveByKeyCommand(CollectionManager manager){
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute() {
        try {
            if (manager.containsKey(Integer.parseInt(args[0]))) {
                manager.removeKey(Integer.parseInt(args[0]));
                System.out.println("Билет был успешно удален");
            } else System.out.println("Билета с таким ключом не существует");
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
        return "Удаляет элемент из коллекции по его ключу.";
    }
    
}
