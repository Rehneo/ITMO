package Commands;
/**
 * Класс команды, которая выводит последние 12 команд (без их аргументов).
 */
public class HistoryCommand implements Command {
    /**
     * Поле, хранящее ссылку на объект класса CommandInvoker.
     */
    private CommandInvoker invoker;
    /**
     * Конструктор класса.
     * 
     * @param invoker - хранит ссылку на объект класса CommandInvoker.
     */
    public HistoryCommand(CommandInvoker invoker){
        this.invoker = invoker;
    }
    /**
     * Метод, исполняющий команду. Выводит список последних 12 использованных команд.
     */
    @Override
    public void execute() {
        System.out.println("История команд: ");
        for (String s: invoker.getHistory()){
            System.out.println(s);
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description() {
        return "Выводит последние 12 команд (без их аргументов)";
    }
    
}
