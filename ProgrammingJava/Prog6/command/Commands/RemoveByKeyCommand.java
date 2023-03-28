package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Commands.AbstractCommands.InvocationStatus;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Класс команды, которая удаляет элемент из коллекции по его ключу.
 */
public class RemoveByKeyCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Конструктор класса.
     */
    public RemoveByKeyCommand() {
        super("remove_key");
    }
    /**
     * Конструктор класса.
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     */
    public RemoveByKeyCommand(CollectionManager manager){
        super("remove_key");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, InvocationStatus invocationEnum, PrintStream printStream) throws CannotExecuteCommandException {
        if (invocationEnum.equals(InvocationStatus.CLIENT)) {
            result = new ArrayList<>();
            if (args.length != 1) {
                throw new CannotExecuteCommandException("Введены неверные аргументы команды. ");
            } else if (args[0] == null || args[0].equals("")) {
                throw new CannotExecuteCommandException("Не были введены аргументы для команды");
            }
            try {
                result.add(Integer.parseInt(args[0]));
            } catch (NumberFormatException e){
                throw new CannotExecuteCommandException("Ключ должен быть целочисленным");
            }
        } else if (invocationEnum.equals(InvocationStatus.SERVER)) {
            Integer id = (Integer) this.getResult().get(0);
            if (manager.containsKey(id)) {
                manager.removeKey(id);
                printStream.println("Билет с ключом = " + id + " был удален.");
            } else printStream.println("Билета с указанным ключом не существует.");
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Удаляет элемент из коллекции по его ключу.";
    }
    
}
