package Commands.AbstractCommands;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;
import java.io.PrintStream;
import java.util.ArrayList;
public abstract class Command {
    /**
     * Имя команды.
     */
    private String name;

    /**
     * Результат команды, необходимый серверу для выполнения серверной части команды.
     */
    protected ArrayList<Object> result;

    /**
     * Конструктор класса.
     * @param name имя команды.
     */
    protected Command(String name) {
        this.name = name;
    }
    /**
     * Конструктор класса.
     */
    protected Command() {
    }
    /**
     * Метод, исполняющий команду.
     * @param args - аргументы команды.
     * @param printStream - поток вывода.
     * @throws CannotExecuteCommandException - исключение, выбрасываемое когда команда не может быть исполнена.
     */
    public abstract void execute(String[] args, PrintStream printStream, UserData userData)
            throws CannotExecuteCommandException;

    /**
     * Метод, возвращающий имя команды.
     * @return String - имя команды.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод, возвращающий данные, необходимые команде.
     * @return Аргументы, необходимые команде для исполнения на сервере.
     */
    public ArrayList<Object> getResult() {
        return result;
    }

    /**
     * Метод, возвращающий описание команды.
     * @return String - описание команды.
     */
    public String description() {
        return "у данной команды пока нет описания.";
    }

    @Override
    public String toString() {
        return "Имя :" + getName() + "\nСодержимое на исполнение серверу: " + result.stream().toString();
    }
}
