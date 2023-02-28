package Commands;

import Collection.CollectionManager;
/**
 * Класс команды, которая выводит основную информацию о коллекции.
 */
public class InfoCommand implements Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Конструктор класса.
     * @param manager - хранит ссылку на объект класса CollectionManager.
     */
    public InfoCommand(CollectionManager manager){
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute() {
        manager.info();
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Выводит информацию о коллекции";
    }
}
