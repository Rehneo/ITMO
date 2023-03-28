package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Commands.AbstractCommands.InvocationStatus;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Класс команды, которая удаляет из коллекции все билеты, ключ которых превышает заданный.
 */
public class RemoveGreaterKeyCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Конструктор класса.
     */
    public RemoveGreaterKeyCommand() {
        super("remove_greater_key");
    }
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public RemoveGreaterKeyCommand(CollectionManager manager){
        super("remove_greater_key");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute(String[] args, InvocationStatus invocationEnum, PrintStream printStream) throws CannotExecuteCommandException {
        if (invocationEnum.equals(InvocationStatus.CLIENT)) {
            result = new ArrayList<>();
            if (args.length != 1) {
                throw new CannotExecuteCommandException("Количество аргументов у данной команды равно 1.");
            }
            if (!(args[0] == null || args[0].equals(""))) {
                try {
                    result.add(Integer.parseInt(args[0]));
                } catch (NumberFormatException e){
                    throw new CannotExecuteCommandException("Ключ должен быть целочисленным");
                }
            } else {
                throw new CannotExecuteCommandException("Не были введены аргументы для команды");
            }
        } else if (invocationEnum.equals(InvocationStatus.SERVER)) {
            Integer id = (Integer) this.getResult().get(0);
            manager.removeGreaterKey(id);
            printStream.println("Билеты, у которых ключ больше " + id + ", были удалены");
        }
    }

    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Удаляет из коллекции все билеты, ключ которых превышает заданный.";
    }
}
