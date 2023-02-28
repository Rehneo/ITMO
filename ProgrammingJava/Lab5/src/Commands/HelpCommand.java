package Commands;

import java.util.HashMap;
/**
 * Класс команды, которая выводит справку по доступным командам.
 */
public class HelpCommand implements Command{
    /**
     * Коллекция, содержащая объекты всех доступных в программе команд без дополнительных аргументов.
     */
    private HashMap<String, Command> commandWithoutArgs;
    /**
     * Коллекция, содержащая объекты всех доступных в программе команд с дополнительными аргументами.
     */
    private HashMap<String, CommandWithArgs> commandWithArgs;
    /**
     * Конструктор класса.
     *
     * @param commandWithoutArgs - Коллекция, содержащая объекты всех доступных в программе команд без дополнительных аргументов.
     * @param commandWithArgs -  Коллекция, содержащая объекты всех доступных в программе команд с дополнительными аргументами.
     */
    public HelpCommand(HashMap<String, Command> commandWithoutArgs, HashMap<String, CommandWithArgs> commandWithArgs) {
        this.commandWithoutArgs = commandWithoutArgs;
        this.commandWithArgs = commandWithArgs;
    }
    /**
     * Метод, исполняющий команду. Выводит список всех доступных команд с описанием.
     */
    @Override
    public void execute() {
        for(String name: commandWithoutArgs.keySet()){
            System.out.println(name +": " +  commandWithoutArgs.get(name).description());
        }

        for(String name: commandWithArgs.keySet()){
            System.out.println(name +": " +  commandWithArgs.get(name).description());
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description() {
        return "Выводит справку по доступным командам.";
    }
    
}
