package Commands;

import Commands.AbstractCommands.Command;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;

import java.io.PrintStream;
import java.util.concurrent.locks.Lock;

public class AdminHistoryCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CommandInvoker.
     */
    private final CommandInvoker invoker;
    /**
     * Конструктор класса.
     *
     * @param invoker - хранит ссылку на объект класса CommandInvoker.
     */
    public AdminHistoryCommand(CommandInvoker invoker){
        this.invoker = invoker;
    }
    /**
     * Метод, исполняющий команду. Выводит список последних 12 использованных команд.
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException {
        System.out.println("История команд: ");
        for (String s: invoker.getAdminHistory()){
            System.out.println(s);
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
