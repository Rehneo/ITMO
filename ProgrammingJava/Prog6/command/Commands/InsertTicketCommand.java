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
 * Класс команды, которая добавляет новый билет в коллекцию с заданным ключом.
 */
public class InsertTicketCommand extends Command {
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
    public InsertTicketCommand(){
        super("insert");
    }
    /**
     * Конструктор класса
     * @param ticketReader - Хранит ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    public InsertTicketCommand(TicketFieldsReader ticketReader){
        super("insert");
        this.ticketReader = ticketReader;
    }
    public InsertTicketCommand(CollectionManager manager){
        super("insert");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, InvocationStatus invocationEnum, PrintStream printStream) throws CannotExecuteCommandException {
        if (invocationEnum.equals(InvocationStatus.CLIENT)) {
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
                try {
                    ticket = ticketReader.read();
                } catch (InvalidInputDataException e) {
                    throw new CannotExecuteCommandException("Введены неправильные аргументы команды");
                }
                super.result.add(Integer.parseInt(args[0]));
                super.result.add(ticket);
            } else
                throw new CannotExecuteCommandException("Введены неправильные аргументы:= " + args[0]);
        } else if (invocationEnum.equals(InvocationStatus.SERVER)) {
            Ticket ticket = (Ticket) this.getResult().get(1);
            Integer key = (Integer) this.getResult().get(0);
            if (!manager.containsKey(key)) {
                manager.insert((Integer) this.getResult().get(0), ticket, printStream);
                printStream.println("Билет с ключом = " + key + " был добавлен в коллекцию.");
            } else printStream.println("Билет с указанным ключом уже существует!.");
        }
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
