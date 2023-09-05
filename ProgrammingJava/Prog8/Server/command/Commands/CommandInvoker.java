package Commands;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.Lock;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Data.Ticket;
import Database.CollectionDatabaseManager;
import Database.UserData;
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
    private final HashMap<Integer, Queue<String>> historyTest = new HashMap<>();

    private final Queue<String> adminHistory = new LinkedList<>();

    /**
     * Коллекция команд для сервера.
     */
    private final HashMap<String, Command> serCommands;

    private final HashMap<String,Command> admCommands;
    private final CollectionManager manager;
    private final CollectionDatabaseManager cdm;

    private Lock lock;
    /**
     * Поле, содержащее путь к файлу, из/в котор(ого/ый) считывается/сохраняется коллекция.
     */
    private String inputPath;
    private final User user;
    private final TicketFieldsReader ticketReader;
    /**
     * Поле, хранящее объект класса ExecuteScript.Script.
     */
    AdminExecuteScriptCommand.Script script;
    public CommandInvoker(CollectionManager manager,CollectionDatabaseManager cdm, User user, TicketFieldsReader ticketReader, AdminExecuteScriptCommand.Script script){
        this.manager = manager;
        this.cdm = cdm;
        this.user = user;
        this.ticketReader = ticketReader;
        this.script = script;
        this.serCommands = new HashMap<>();
        this.admCommands = new HashMap<>();
        this.putAdminCommands();
    }
    /**
     * Конструктор класса, предназначенный для сервера.
     */
    public CommandInvoker(CollectionManager manager,CollectionDatabaseManager cdm, String inputPath) {
        this.serCommands = new HashMap<>();
        this.admCommands = new HashMap<>();
        this.user = new User();
        this.ticketReader = new TicketFieldsReader(user);
        this.manager = manager;
        this.cdm = cdm;
        this.inputPath = inputPath;
        this.script = new AdminExecuteScriptCommand.Script();
        this.putServerCommands();
        this.putAdminCommands();
    }

    /**
     * Конструктор класса, предназначенный для сервера.
     */
    public CommandInvoker(CollectionManager manager, CollectionDatabaseManager cdm, Lock lock) {
        this.admCommands = new HashMap<>();
        this.serCommands = new HashMap<>();
        this.user = new User();
        this.ticketReader = new TicketFieldsReader(user);
        this.script = new AdminExecuteScriptCommand.Script();
        this.manager = manager;
        this.cdm = cdm;
        this.lock = lock;
        this.putServerCommands();
        this.putAdminCommands();
    }
    /**
     * Метод, добавляющий серверные команды в соответствующую коллекцию.
     */
    private void putServerCommands() {
        serCommands.put("info", new InfoCommand(manager));
        serCommands.put("show", new ShowCommand(manager));
        serCommands.put("clear", new ClearCommand(manager,cdm));
        serCommands.put("save", new SaveCommand(manager, inputPath));
        serCommands.put("sum_of_price", new SumOfPrice(manager));
        serCommands.put("min_by_name", new MinByNameCommand(manager));
        serCommands.put("help", new HelpCommand(serCommands));
        serCommands.put("print_ascending", new PrintAscendingCommand(manager));
        serCommands.put("exit", new ExitCommand(manager,inputPath));
        serCommands.put("history", new HistoryCommand(this));

        serCommands.put("insert", new InsertTicketCommand(manager,cdm));
        serCommands.put("update", new UpdateByIdCommand(manager,cdm));
        serCommands.put("remove_key", new RemoveByKeyCommand(manager,cdm));
        serCommands.put("remove_id",new RemoveByIdCommand(manager,cdm));
        serCommands.put("execute_script", new ExecuteScriptCommand(manager,cdm));
        serCommands.put("remove_greater", new RemoveGreaterCommand(manager,cdm));
        serCommands.put("remove_greater_key", new RemoveGreaterKeyCommand(manager,cdm));
    }

    private void putAdminCommands() {
        admCommands.put("info", new InfoCommand(manager));
        admCommands.put("show", new ShowCommand(manager));
        admCommands.put("clear", new ClearCommand(manager,cdm));
        admCommands.put("save", new SaveCommand(manager, inputPath));
        admCommands.put("sum_of_price", new SumOfPrice(manager));
        admCommands.put("min_by_name", new MinByNameCommand(manager));
        admCommands.put("help", new HelpCommand(admCommands));
        admCommands.put("print_ascending", new PrintAscendingCommand(manager));
        admCommands.put("history", new AdminHistoryCommand(this));
        admCommands.put("exit", new AdminExitCommand());

        admCommands.put("insert", new AdminInsertTicketCommand(manager,cdm, user,ticketReader));
        admCommands.put("remove_greater", new AdminRemoveGreaterCommand(manager,cdm,user,ticketReader));
        admCommands.put("remove_greater_key", new AdminRemoveGreaterKeyCommand(manager,cdm));
        admCommands.put("update", new AdminUpdateByIdCommand(manager,cdm,user,ticketReader));
        admCommands.put("remove_key", new AdminRemoveByKeyCommand(manager,cdm));
        admCommands.put("remove_id",new AdminRemoveByIdCommand(manager,cdm));
        admCommands.put("execute_script", new AdminExecuteScriptCommand(manager,cdm,script));
    }
    public boolean executeServer(String firstCommandLine, ArrayList<Object> result, PrintStream printStream, UserData userData) {

        String[] words = firstCommandLine.trim().split("\\s+");
        String[] arguments = Arrays.copyOfRange(words, 1, words.length);
        try {
            if (serCommands.containsKey(words[0].toLowerCase(Locale.ROOT))) {
                Command command;
                command = serCommands.get(words[0].toLowerCase(Locale.ROOT));
                command.setResult(result);
                command.setPort(userData.getPort());
                if(!historyTest.containsKey(userData.getPort())){
                    historyTest.put(userData.getPort(),new LinkedList<>());
                }
                command.execute(arguments, printStream,userData,lock);
                if(!words[0].toLowerCase(Locale.ROOT).equals("history")){
                    historyTest.get(userData.getPort()).add(words[0].toLowerCase(Locale.ROOT));
                    if(historyTest.get(userData.getPort()).size() > 12){
                        historyTest.get(userData.getPort()).remove();
                    }
                }
                if(words[0].toLowerCase(Locale.ROOT).equals("exit")){
                    historyTest.remove(userData.getPort());
                }
                return true;
            }
        } catch (NullPointerException e) {
            System.out.println("Команда " + words[0] + " не распознана, для получения справки введите команду help");
            e.printStackTrace();
        } catch (CannotExecuteCommandException e) {
            System.out.println(e.getMessage());
        }catch (SQLException e){
            System.out.println("Ошибка доступа к базе данных " + e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public void executeAdmin(String firstCommandLine,PrintStream printStream, UserData userData) {

        String[] words = firstCommandLine.trim().split("\\s+");
        String[] arguments = Arrays.copyOfRange(words, 1, words.length);
        try {
            if (admCommands.containsKey(words[0].toLowerCase(Locale.ROOT))) {
                Command command;
                command = admCommands.get(words[0].toLowerCase(Locale.ROOT));
                command.execute(arguments, printStream, userData,lock);
                if(!words[0].toLowerCase(Locale.ROOT).equals("history")) {
                    adminHistory.add(words[0].toLowerCase(Locale.ROOT));
                    if (adminHistory.size() > 12) {
                        adminHistory.remove();
                    }
                }
            }else if((firstCommandLine.matches("\\s+")) || (firstCommandLine.equals(""))) {
            }else {
                System.out.println("Команда " + words[0] + " не распознана, для получения справки введите команду help");
            }
        } catch (NullPointerException e) {
            System.out.println("Команда " + words[0] + " не распознана, для получения справки введите команду help");
            e.printStackTrace();
        } catch (CannotExecuteCommandException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e){
            System.out.println("Ошибка доступа к базе данных " + e.getMessage());
        }
    }

    /**
     * Метод, возвращающий очередь, хранящую список последних использованных команд.
     */
    public Queue<String> getHistory(int port){
        return historyTest.get(port);
    }

    public Queue<String> getAdminHistory(){
        return adminHistory;
    }
}
