package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Commands.AbstractCommands.CommandContainer;
import Exceptions.CannotExecuteCommandException;
import java.io.*;
import java.util.Arrays;
/**
 * Класс команды, которая считывает и исполняет скрипт.
 */
public class ExecuteScriptCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    /**
     * Конструктор класса.
     */
    public ExecuteScriptCommand(CollectionManager manager) {
        super("execute_script");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args,  PrintStream printStream) throws CannotExecuteCommandException {
        printStream.println("Файл, который исполняется скриптом: " + this.getResult().get(0));
        Object[] arr = result.toArray();
        arr = Arrays.copyOfRange(arr, 1, arr.length);
        CommandContainer[] containerArray = Arrays.copyOf(arr, arr.length, CommandContainer[].class);
        CommandInvoker commandInvoker = new CommandInvoker(manager);
        for (CommandContainer command : containerArray) {
            commandInvoker.executeServer(command.getName(), command.getResult(), printStream);
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
