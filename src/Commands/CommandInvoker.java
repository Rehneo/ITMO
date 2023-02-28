package Commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

import Collection.CollectionManager;
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
     * Коллекция команд без дополнительных аргументов.
     */
    private HashMap<String, Command> commandsWithoutArgs;
    /**
     * Коллекция команд с дополнительными аргументами.
     */
    private HashMap<String, CommandWithArgs> commandsWithArgs;
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
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
     * Конструктор класса. 
     *
     * @param manager
     * @param user       
     * @param inputPath         
     * @param ticketReader
     */
    public CommandInvoker(CollectionManager manager, User user, String inputPath, TicketFieldsReader ticketReader){
        this.manager = manager;
        this.user = user;
        this.inputPath = inputPath;
        this.ticketReader = ticketReader;
        commandsWithArgs = new HashMap<>();
        commandsWithoutArgs = new HashMap<>();
        this.script = new ExecuteScriptCommand.Script();
        this.putCommands();
    }
    /**
     * Конструктор класса. 
     *
     * @param manager
     * @param user            
     * @param ticketReader
     * @param script
     */
    public CommandInvoker(CollectionManager manager, User user, TicketFieldsReader ticketReader, ExecuteScriptCommand.Script script){
        this.manager = manager;
        this.user = user;
        this.ticketReader = ticketReader;
        commandsWithArgs = new HashMap<>();
        commandsWithoutArgs = new HashMap<>();
        this.script = script;
        this.putCommands();
    }
    /**
     * Метод, добавляющий команды в соответствующие им коллекции.
     */
    private void putCommands() {
        commandsWithoutArgs.put("info", new InfoCommand(manager));
        commandsWithoutArgs.put("show", new ShowCommand(manager));
        commandsWithoutArgs.put("clear", new ClearCommand(manager));
        commandsWithoutArgs.put("save", new SaveCommand(manager, inputPath));
        commandsWithoutArgs.put("exit", new ExitCommand());
        commandsWithoutArgs.put("sum_of_price", new SumOfPrice(manager));
        commandsWithoutArgs.put("min_by_name", new MinByNameCommand(manager));
        commandsWithoutArgs.put("print_ascending", new PrintAscendingCommand(manager));
        commandsWithoutArgs.put("help", new HelpCommand(commandsWithoutArgs, commandsWithArgs));
        commandsWithoutArgs.put("history", new HistoryCommand(this));

        commandsWithArgs.put("insert", new InsertTicketCommand(manager, user, ticketReader));
        commandsWithArgs.put("update", new UpdateByIdCommand(manager, user, ticketReader));
        commandsWithArgs.put("remove_key", new RemoveByKeyCommand(manager));
        commandsWithArgs.put("execute_script", new ExecuteScriptCommand(manager, ticketReader, script));
        commandsWithArgs.put("remove_greater", new RemoveGreaterCommand(manager, user, ticketReader));
        commandsWithArgs.put("remove_greater_key", new RemoveGreaterKeyCommand(manager));
    }
    /**
     * Метод, распознающий и исполняющий команду.
     *
     * @param firstLine - строка, из которой распознается команда и её аргументы.
     */
    public void execute(String firstLine) {
        String[] words = firstLine.trim().split("\\s+");
        String[] args = Arrays.copyOfRange(words, 1, words.length);

        if (commandsWithArgs.containsKey(words[0].toLowerCase(Locale.ROOT))) {
            CommandWithArgs command;
            command = commandsWithArgs.get(words[0].toLowerCase(Locale.ROOT));
            command.getArgs(args);
            command.execute();
            history.add(words[0].toLowerCase(Locale.ROOT));
            if(history.size() > 12){
                history.remove();
            }
        } else if (commandsWithoutArgs.containsKey(words[0].toLowerCase(Locale.ROOT))) {
            Command command;
            command = commandsWithoutArgs.get(words[0].toLowerCase(Locale.ROOT));
            command.execute();
            history.add(words[0].toLowerCase(Locale.ROOT));
            if(history.size() > 12){
                history.remove();
            }
        } else if((firstLine.matches("\\s+")) || (firstLine.equals(""))){

        }else {
            System.err.println("Команда " + words[0] + " не распознана, для получения справки введите команду help");
        }
    }
    /**
     * Метод, возвращающий очередь, хранящую список последних использованных команд.
     */
    public Queue<String> getHistory(){
        return history;
    }
}
