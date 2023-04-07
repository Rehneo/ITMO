package Commands;

import Commands.AbstractCommands.Command;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;

public class HistoryCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CommandInvoker.
     */
    private final CommandInvoker invoker;
    /**
     * Конструктор класса.
     * @param invoker - хранит ссылку на объект класса CommandInvoker.
     */
    public HistoryCommand(CommandInvoker invoker){
        this.invoker = invoker;
    }
    @Override
    public void execute(String[] args, PrintStream printStream) throws CannotExecuteCommandException {
        printStream.println("История команд: ");
        for (String s: invoker.getHistory()){
            printStream.println(s);
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description() {
        return "Выводит последние 12 команд (без их аргументов)";
    }
}
