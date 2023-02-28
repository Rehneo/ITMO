package Commands;

import Collection.CollectionManager;
/**
 * Класс команды, выводящей любой билет из коллекции, значение поля name которого является минимальным.
 */
public class MinByNameCommand implements Command{
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public MinByNameCommand(CollectionManager manager){
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute() {
        manager.minByName();
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Выводит любой билет из коллекции, значение поля name которого является минимальным.";
    }
}
