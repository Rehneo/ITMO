package Commands;
import Commands.AbstractCommands.*;
import Exceptions.CannotExecuteCommandException;
import java.io.PrintStream;
/**
 * Класс команды, завершающей работу программы.
 */
public class ExitCommand extends Command{
    /**
     * Конструктор класса.
     */
    public ExitCommand() {
        super("exit");
    }
    /**
     * Метод, завершающий работу клиента. При завершении выводит соответствующее сообщение.
     */
    @Override
    public void execute(String[] args, PrintStream printStream) throws CannotExecuteCommandException {
            if (args.length > 0) {
                throw new CannotExecuteCommandException("У введенной команды нет аргументов.");
            }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Завершает программу.";
    }
}
