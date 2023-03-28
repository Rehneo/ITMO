package Commands;
import Commands.AbstractCommands.*;
import Collection.CollectionManager;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

/**
 * Класс команды, выводящей элементы коллекции в порядке возрастания.
 */
public class PrintAscendingCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Конструктор класса
     */
    public PrintAscendingCommand(){
        super("print_ascending");
    }
    /**
     * Конструктор класса
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public PrintAscendingCommand(CollectionManager manager){
        super("print_ascending");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, InvocationStatus invocationEnum, PrintStream printStream) throws CannotExecuteCommandException {
        if (invocationEnum.equals(InvocationStatus.CLIENT)) {
            if (args.length > 0) {
                throw new CannotExecuteCommandException("У введенной команды нет аргументов.");
            }
        } else if (invocationEnum.equals(InvocationStatus.SERVER)) {
            printStream.println(manager.printAscending());
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Выводит элементы коллекции в порядке возрастания.";
    }
}
