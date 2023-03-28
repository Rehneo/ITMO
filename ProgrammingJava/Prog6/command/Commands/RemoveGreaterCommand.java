package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Commands.AbstractCommands.InvocationStatus;
import Data.Ticket;
import Exceptions.CannotExecuteCommandException;
import Exceptions.InvalidInputDataException;
import Io.TicketFieldsReader;
import Io.User;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Класс команды, которая удаляет все элементы, превышающие заданный.
 */
public class RemoveGreaterCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Поле, хранящее ссылку на объект класса User.
     */
    private User user;
    /**
     * Поле, хранящее ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    private TicketFieldsReader ticketReader;
    public RemoveGreaterCommand(){
        super("remove_greater");
    }
    /**
     * Конструктор класса
     * @param ticketReader - Хранит ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    public RemoveGreaterCommand(TicketFieldsReader ticketReader){
        super("remove_greater");
        this.ticketReader = ticketReader;
    }


    public RemoveGreaterCommand(CollectionManager manager){
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, InvocationStatus invocationEnum, PrintStream printStream) throws CannotExecuteCommandException {
        if (invocationEnum.equals(InvocationStatus.CLIENT)) {
            result = new ArrayList<>();
            if (args.length > 0) {
                throw new CannotExecuteCommandException("У введенной команды нет аргументов.");
            }
            printStream.println("Введите значения полей для элемента коллекции:\n");
            Ticket ticket = null;
            try {
                ticket = ticketReader.read();
            } catch (InvalidInputDataException e) {
                throw new CannotExecuteCommandException("Введены неправильные аргументы");
            }
            super.result.add(ticket);
        } else if (invocationEnum.equals(InvocationStatus.SERVER)) {
            Ticket ticket = (Ticket) this.getResult().get(0);
            manager.removeGreater(ticket);
        }
    }

    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Удаляет из коллекции все билеты, превышающие заданный.";
    }
}
