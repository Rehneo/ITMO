package Commands;
import Commands.AbstractCommands.*;
import Collection.CollectionManager;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

/**
 * Класс команды, которая выводит основную информацию о коллекции.
 */
public class InfoCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Конструктор без параметров.
     */
    public InfoCommand() {
        super("info");
    }
    /**
     * Конструктор класса.
     * @param manager - хранит ссылку на объект класса CollectionManager.
     */
    public InfoCommand(CollectionManager manager){
        super("info");
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
            printStream.println(manager.info());
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Выводит информацию о коллекции";
    }
}
