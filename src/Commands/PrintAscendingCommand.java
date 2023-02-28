package Commands;

import Collection.CollectionManager;
/**
 * Класс команды, выводящей элементы коллекции в порядке возрастания.
 */
public class PrintAscendingCommand implements Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Конструктор класса
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public PrintAscendingCommand(CollectionManager manager){
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute() {
        manager.printAscending();
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Выводит элементы коллекции в порядке возрастания.";
    }
}
