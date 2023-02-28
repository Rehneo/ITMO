package Commands;

import Collection.CollectionManager;

/**
 * Класс команды, выводящей сумму значений поля price для всех билетов коллекции.
 */
public class SumOfPrice implements Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Конструктор класса.
     * @param manager
     */
    public SumOfPrice(CollectionManager manager){
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute() {
        manager.sumOfPrice();
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Выводит сумму значений поля price для всех билетов коллекции.";
    }
}
