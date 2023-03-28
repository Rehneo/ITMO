package Commands;
import Commands.AbstractCommands.*;
import Collection.CollectionManager;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

/**
 * Класс команды, выводящей любой билет из коллекции, значение поля name которого является минимальным.
 */
public class MinByNameCommand extends Command{
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Конструктор класса.
     */
    public MinByNameCommand() {
        super("min_by_name");
    }
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public MinByNameCommand(CollectionManager manager){
        super("min_by_name");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, InvocationStatus invocationEnum, PrintStream printStream) throws CannotExecuteCommandException {
        if (invocationEnum.equals(InvocationStatus.CLIENT)) {
            if (args.length > 0) {
                throw new CannotExecuteCommandException("У введенной команды нет аргументов. ");
            }
        } else if (invocationEnum.equals(InvocationStatus.SERVER)) {
            printStream.println(manager.minByName());
        }
    }

    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Выводит любой билет из коллекции, значение поля name которого является минимальным.";
    }
}
