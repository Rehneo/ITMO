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
 * Класс команды, которая добавляет новый билет в коллекцию с заданным ключом.
 */
public class InsertTicketCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    private final TicketFieldsReader ticketReader;
    /**
     * Конструктор класса
     * @param ticketReader - Хранит ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    public InsertTicketCommand(TicketFieldsReader ticketReader){
        super("insert");
        this.ticketReader = ticketReader;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData) throws CannotExecuteCommandException {
            if (args == null || args.length != 1) {
                throw new CannotExecuteCommandException("Количество аргументов у данной команды должно равняться 1.");
            }
            try {
                Integer.parseInt(args[0]);
            } catch (NumberFormatException e){
                throw new CannotExecuteCommandException("Ключ должен быть целочисленным");
            }
            result = new ArrayList<>();
            if (!(args[0] == null || args[0].equals(""))) {
                printStream.println("Введите значения полей для элемента коллекции:\n");
                Ticket ticket;
                ticket = userData.getAddTicket();
                ticket.setOwner(userData.getLogin());
                super.result.add(Integer.parseInt(args[0]));
                super.result.add(ticket);
            } else
                throw new CannotExecuteCommandException("Введены неправильные аргументы:= " + args[0]);
    }

    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Добавляет новый билет в коллекцию с заданным ключом.";
    }
}
