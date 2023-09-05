package Commands;

import java.io.PrintStream;
import java.util.*;
import Commands.AbstractCommands.Command;
import Commands.AbstractCommands.CommandContainer;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;
import Io.TicketFieldsReader;
import Io.User;
/**
 * Класс, через который осуществляется исполнение команд.
 */
public class CommandInvoker {
    /**
     * Коллекция команд для клиента.
     */
    private final HashMap<String, Command> clCommands;
    /**
     * Поле, хранящее ссылку на объект класса User.
     */
    private final User user;
    /**
     * Поле, хранящее объект класса ExecuteScript.Script.
     */
    ExecuteScriptCommand.Script script;
    /**
     * Поле, хранящее ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    private final TicketFieldsReader ticketReader;

    /**
     * Контейнер с командой.
     */
    private CommandContainer lastCommandContainer;
    /**
     * Конструктор класса.
     *
     * @param user - читает данные из указанного потока.
     */
    public CommandInvoker(User user) { //для клиента
        this.clCommands = new HashMap<>();
        this.user = user;
        this.ticketReader = new TicketFieldsReader(user);
        this.script = new ExecuteScriptCommand.Script();
        this.putClientCommands();
    }
    /**
     * Конструктор класса, предназначенный для исполнения скрипта на клиенте.
     */
    public CommandInvoker(User user, TicketFieldsReader ticketReader, ExecuteScriptCommand.Script script) {
        this.clCommands = new HashMap<>();
        this.user = user;
        this.ticketReader = ticketReader;
        this.script = script;
        this.putClientCommands();
    }
    private void putClientCommands() {
        clCommands.put("info", new InfoCommand());
        clCommands.put("show", new ShowCommand());
        clCommands.put("clear", new ClearCommand());
        clCommands.put("exit", new ExitCommand());
        clCommands.put("sum_of_price", new SumOfPrice());
        clCommands.put("min_by_name", new MinByNameCommand());
        clCommands.put("help", new HelpCommand(clCommands));
        clCommands.put("print_ascending", new PrintAscendingCommand());
        clCommands.put("history",new HistoryCommand());

        clCommands.put("insert", new InsertTicketCommand(ticketReader));
        clCommands.put("update", new UpdateByIdCommand(ticketReader));
        clCommands.put("remove_key", new RemoveByKeyCommand());
        clCommands.put("remove_id", new RemoveByIdCommand());
        clCommands.put("execute_script", new ExecuteScriptCommand(user, script));
        clCommands.put("remove_greater", new RemoveGreaterCommand(ticketReader));
        clCommands.put("remove_greater_key", new RemoveGreaterKeyCommand());
    }

    public boolean executeClient(String firstCommandLine, PrintStream printStream, UserData userData) {

        String[] words = firstCommandLine.trim().split("\\s+");
        String[] arguments = Arrays.copyOfRange(words, 1, words.length);

        try {
            if (clCommands.containsKey(words[0].toLowerCase(Locale.ROOT))) {
                Command command;
                command = clCommands.get(words[0].toLowerCase(Locale.ROOT));
                command.execute(arguments,printStream, userData);
                lastCommandContainer = new CommandContainer(command.getName(), command.getResult());
                return true;
            }
            else if((firstCommandLine.matches("\\s+")) || (firstCommandLine.equals(""))){

            }else{
                printStream.println("Команда " + words[0] + " не распознана, для получения справки введите команду help");
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (CannotExecuteCommandException ex) {
            printStream.println(ex.getMessage());
        }
        return false;
    }
    public CommandContainer getLastContainer() {
        return lastCommandContainer;
    }
}
