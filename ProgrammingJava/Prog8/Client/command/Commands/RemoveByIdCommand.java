package Commands;
import Commands.AbstractCommands.Command;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Класс команды, которая удаляет элемент из коллекции по его Id.
 */
public class RemoveByIdCommand extends Command {
    /**
     * Конструктор класса.
     */
    public RemoveByIdCommand() {
        super("remove_id");
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData) throws CannotExecuteCommandException {
        result = new ArrayList<>();
        if (args.length != 1) {
            throw new CannotExecuteCommandException("Введены неверные аргументы команды. ");
        } else if (args[0] == null || args[0].equals("")) {
            throw new CannotExecuteCommandException("Не были введены аргументы для команды");
        }
        try {
            result.add(Long.parseLong(args[0]));
        } catch (NumberFormatException e){
            throw new CannotExecuteCommandException("ID должен быть целочисленным");
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Удаляет элемент из коллекции по его ID.";
    }

}
