package Commands;
import Commands.AbstractCommands.Command;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Класс команды, которая удаляет из коллекции все билеты, ключ которых превышает заданный.
 */
public class RemoveGreaterKeyCommand extends Command {
    /**
     * Конструктор класса.
     */
    public RemoveGreaterKeyCommand() {
        super("remove_greater_key");
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute(String[] args, PrintStream printStream) throws CannotExecuteCommandException {
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
    }

    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Удаляет из коллекции все билеты, ключ которых превышает заданный.";
    }
}
