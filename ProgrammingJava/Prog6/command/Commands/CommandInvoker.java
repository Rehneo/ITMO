package Commands;

import java.io.PrintStream;
import java.util.*;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Commands.AbstractCommands.CommandContainer;
import Commands.AbstractCommands.InvocationStatus;
import Exceptions.CannotExecuteCommandException;
import Io.TicketFieldsReader;
import Io.User;
/**
 * Класс, через который осуществляется исполнение команд.
 */
public class CommandInvoker {
    /**
     * Очередь, которая хранит список последних использованных команд.
     */
    private Queue<String> history = new LinkedList<>();
    /**
     * Коллекция команд для клиента.
     */
    private HashMap<String, Command> clCommands;

    /**
     * Коллекция команд для сервера.
     */
    private HashMap<String, Command> serCommands;
    private CollectionManager manager;
    /**
     * Поле, хранящее ссылку на объект класса User.
     */
    private User user;
    /**
     * Поле, хранящее объект класса ExecuteScript.Script.
     */
    ExecuteScriptCommand.Script script;
    /**
     * Поле, содержащее путь к файлу, из/в котор(ого/ый) считывается/сохраняется коллекция.
     */
    private String inputPath;
    /**
     * Поле, хранящее ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    private TicketFieldsReader ticketReader;
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

    /**
     * Конструктор класса, предназначенный для сервера.
     */
    public CommandInvoker(CollectionManager manager, String inputPath) {
        this.serCommands = new HashMap<>();
        this.manager = manager;
        this.inputPath = inputPath;
        script = new ExecuteScriptCommand.Script();
        this.putServerCommands();
        System.out.println("Элементы коллекции для сервера были загружены");
    }

    /**
     * Конструктор класса, предназначенный для сервера.
     */
    public CommandInvoker(CollectionManager manager) {
        this.serCommands = new HashMap<>();
        this.manager = manager;
        this.putServerCommands();
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

        clCommands.put("insert", new InsertTicketCommand(ticketReader));
        clCommands.put("update", new UpdateByIdCommand(ticketReader));
        clCommands.put("remove_key", new RemoveByKeyCommand());
        clCommands.put("execute_script", new ExecuteScriptCommand(user, ticketReader, script));
        clCommands.put("remove_greater", new RemoveGreaterCommand(ticketReader));
        clCommands.put("remove_greater_key", new RemoveGreaterKeyCommand());
    }

    /**
     * Метод, добавляющий серверные команды в соответствующую коллекцию.
     */
    private void putServerCommands() {
        serCommands.put("info", new InfoCommand(manager));
        serCommands.put("show", new ShowCommand(manager));
        serCommands.put("clear", new ClearCommand(manager));
        serCommands.put("save", new SaveCommand(manager, inputPath));
        serCommands.put("sum_of_price", new SumOfPrice(manager));
        serCommands.put("min_by_name", new MinByNameCommand(manager));
        serCommands.put("help", new HelpCommand(serCommands));
        serCommands.put("print_ascending", new PrintAscendingCommand(manager));

        serCommands.put("insert", new InsertTicketCommand(manager));
        serCommands.put("update", new UpdateByIdCommand(manager));
        serCommands.put("remove_key", new RemoveByKeyCommand(manager));
        serCommands.put("execute_script", new ExecuteScriptCommand(manager));
        serCommands.put("remove_greater", new RemoveGreaterCommand(manager));
        serCommands.put("remove_greater_key", new RemoveGreaterKeyCommand(manager));
    }


    public boolean executeClient(String firstCommandLine, PrintStream printStream) {

        String[] words = firstCommandLine.trim().split("\\s+");
        String[] arguments = Arrays.copyOfRange(words, 1, words.length);

        try {
            if (clCommands.containsKey(words[0].toLowerCase(Locale.ROOT))) {
                Command command;
                command = clCommands.get(words[0].toLowerCase(Locale.ROOT));
                command.execute(arguments, InvocationStatus.CLIENT, printStream);
                lastCommandContainer = new CommandContainer(command.getName(), command.getResult());
                return true;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (CannotExecuteCommandException ex) {
            printStream.println(ex.getMessage());
        }
        return false;
    }

    public boolean executeServer(String firstCommandLine, ArrayList<Object> result, PrintStream printStream) {

        String[] words = firstCommandLine.trim().split("\\s+");
        String[] arguments = Arrays.copyOfRange(words, 1, words.length);
        try {
            if (serCommands.containsKey(words[0].toLowerCase(Locale.ROOT))) {
                Command command;
                command = serCommands.get(words[0].toLowerCase(Locale.ROOT));

                command.setResult(result);
                command.execute(arguments, InvocationStatus.SERVER, printStream);
                return true;
            }
        } catch (NullPointerException e) {
            System.out.println("Команда " + words[0] + " не распознана, для получения справки введите команду help");
            e.printStackTrace();
        } catch (CannotExecuteCommandException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    /**
     * Метод, возвращающий очередь, хранящую список последних использованных команд.
     */
    public Queue<String> getHistory(){
        return history;
    }

    public CommandContainer getLastContainer() {
        return lastCommandContainer;
    }
}
