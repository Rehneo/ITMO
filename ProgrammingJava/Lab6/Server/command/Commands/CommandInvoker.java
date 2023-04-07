package Commands;

import java.io.PrintStream;
import java.util.*;
import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Exceptions.CannotExecuteCommandException;
/**
 * Класс, через который осуществляется исполнение команд.
 */
public class CommandInvoker {
    /**
     * Очередь, которая хранит список последних использованных команд.
     */
    private final Queue<String> history = new LinkedList<>();
    /**
     * Коллекция команд для сервера.
     */
    private final HashMap<String, Command> serCommands;
    private final CollectionManager manager;
    /**
     * Поле, содержащее путь к файлу, из/в котор(ого/ый) считывается/сохраняется коллекция.
     */
    private String inputPath;
    /**
     * Конструктор класса, предназначенный для сервера.
     */
    public CommandInvoker(CollectionManager manager, String inputPath) {
        this.serCommands = new HashMap<>();
        this.manager = manager;
        this.inputPath = inputPath;
        this.putServerCommands();
    }

    /**
     * Конструктор класса, предназначенный для сервера.
     */
    public CommandInvoker(CollectionManager manager) {
        this.serCommands = new HashMap<>();
        this.manager = manager;
        this.putServerCommands();
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
        serCommands.put("exit", new ExitCommand(manager,inputPath));
        serCommands.put("history", new HistoryCommand(this));

        serCommands.put("insert", new InsertTicketCommand(manager));
        serCommands.put("update", new UpdateByIdCommand(manager));
        serCommands.put("remove_key", new RemoveByKeyCommand(manager));
        serCommands.put("execute_script", new ExecuteScriptCommand(manager));
        serCommands.put("remove_greater", new RemoveGreaterCommand(manager));
        serCommands.put("remove_greater_key", new RemoveGreaterKeyCommand(manager));
    }
    public boolean executeServer(String firstCommandLine, ArrayList<Object> result, PrintStream printStream) {

        String[] words = firstCommandLine.trim().split("\\s+");
        String[] arguments = Arrays.copyOfRange(words, 1, words.length);
        try {
            if (serCommands.containsKey(words[0].toLowerCase(Locale.ROOT))) {
                Command command;
                command = serCommands.get(words[0].toLowerCase(Locale.ROOT));
                command.setResult(result);
                command.execute(arguments, printStream);
                if(!words[0].toLowerCase(Locale.ROOT).equals("history")) {
                    history.add(words[0].toLowerCase(Locale.ROOT));
                    if (history.size() > 12) {
                        history.remove();
                    }
                }
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
}
