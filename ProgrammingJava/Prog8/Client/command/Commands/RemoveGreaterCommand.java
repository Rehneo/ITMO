package Commands;
import Commands.AbstractCommands.Command;
import Data.Ticket;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;
import Exceptions.InvalidInputDataException;
import Io.TicketFieldsReader;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Класс команды, которая удаляет все элементы, превышающие заданный.
 */
public class RemoveGreaterCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    private final TicketFieldsReader ticketReader;
    /**
     * Конструктор класса
     * @param ticketReader - Хранит ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    public RemoveGreaterCommand(TicketFieldsReader ticketReader){
        super("remove_greater");
        this.ticketReader = ticketReader;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData) throws CannotExecuteCommandException {
            result = new ArrayList<>();
            if (args.length > 0) {
                throw new CannotExecuteCommandException("У введенной команды нет аргументов.");
            }
            printStream.println("Введите значения полей для элемента коллекции:\n");
            Ticket ticket;
            try {
                ticket = ticketReader.read();
            } catch (InvalidInputDataException e) {
                throw new CannotExecuteCommandException("Введены неправильные аргументы");
            }
            ticket.setOwner(userData.getLogin());
            super.result.add(ticket);
    }

    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Удаляет из коллекции все билеты, превышающие заданный.";
    }
}
