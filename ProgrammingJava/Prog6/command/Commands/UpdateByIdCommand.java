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
 * Класс команды, обновляющей значение билета коллекции, id которого равен заданному"
 */
public class UpdateByIdCommand extends Command {
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
    public UpdateByIdCommand(){
        super("update");
    }
    /**
     * Конструктор класса.
     */
    public UpdateByIdCommand(TicketFieldsReader ticketReader){
        super("update");
        this.ticketReader = ticketReader;
    }
    /**
     * Конструктор класса.
     */
    public UpdateByIdCommand(CollectionManager manager){
        super("update");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute(String[] args, InvocationStatus invocationEnum, PrintStream printStream) throws CannotExecuteCommandException {
        if (invocationEnum.equals(InvocationStatus.CLIENT)) {
            if (args == null || args.length != 1) {
                throw new CannotExecuteCommandException("Количество аргументов у данной команды должно равняться 1.");
            }
            try {
                Long.parseLong(args[0]);
            } catch (NumberFormatException e){
                throw new CannotExecuteCommandException("id билета должно быть целочисленным");
            }
            result = new ArrayList<>();
            if (!(args[0] == null || args[0].equals(""))) {
                printStream.println("Введите значения полей для элемента коллекции:\n");
                Ticket ticket;
                try {
                    ticket = ticketReader.read();
                } catch (InvalidInputDataException e) {
                    throw new CannotExecuteCommandException("Введены неправильные аргументы");
                }
                super.result.add(Long.parseLong(args[0]));
                super.result.add(ticket);
            } else
                throw new CannotExecuteCommandException("Введены неправильные аргументы:= " + args[0]);
        } else if (invocationEnum.equals(InvocationStatus.SERVER)) {
            Ticket ticket = (Ticket) this.getResult().get(1);
            Long id = (Long) this.getResult().get(0);
            if (manager.containsId(id)) {
                manager.updateById((Long) this.getResult().get(0), ticket, printStream);
                printStream.println("Билет с id = " + id + " был обновлён.");
            } else printStream.println("Билета с указанным id не существует.");
        }
    }

    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Обновляет значение билета коллекции, id которого равен заданному";
    }
}
