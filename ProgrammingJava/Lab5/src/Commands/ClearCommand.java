package Commands;

import Collection.CollectionManager;
/**
 * Класс команды, которая очищает коллекцию.
 */
public class ClearCommand implements Command{
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public ClearCommand(CollectionManager manager){
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     * @see Command
     */
    @Override
    public void execute() {
        manager.clear();
        System.out.println("Коллекция была успешно очищена.");
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Очищает коллекцию.";
    }
}
