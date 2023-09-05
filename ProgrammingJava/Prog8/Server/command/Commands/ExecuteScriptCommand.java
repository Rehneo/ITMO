package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Commands.AbstractCommands.CommandContainer;
import Database.CollectionDatabaseManager;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;
import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

/**
 * Класс команды, которая считывает и исполняет скрипт.
 */
public class ExecuteScriptCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;

    private final CollectionDatabaseManager cdm;
    /**
     * Конструктор класса.
     */
    public ExecuteScriptCommand(CollectionManager manager, CollectionDatabaseManager cdm) {
        super("execute_script");
        this.manager = manager;
        this.cdm = cdm;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException, SQLException {
        printStream.println("Файл, который исполняется скриптом: " + this.getResult().get(0));
        Object[] arr = result.toArray();
        arr = Arrays.copyOfRange(arr, 1, arr.length);
        CommandContainer[] containerArray = Arrays.copyOf(arr, arr.length, CommandContainer[].class);
        CommandInvoker commandInvoker = new CommandInvoker(manager,cdm,lock);
        for (CommandContainer command : containerArray) {
            commandInvoker.executeServer(command.getName(), command.getResult(), printStream, userData);
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Считывает и исполняет скрипт из указанного файла.";
    }
    
}
