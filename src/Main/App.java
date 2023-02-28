package Main;
import java.io.IOException;
import Collection.CollectionManager;
import Commands.CommandInvoker;
import Exceptions.FileException;
import File.FileManager;
import Io.TicketFieldsReader;
import Io.User;
/**
 * Класс, через который производится запуск программы.
 */
public class App {
    /**
     * менеджер коллекций
     */
    CollectionManager manager;
    /**
     * менеджер файлов
     */
    FileManager fileManager;
    /**
     * хранит ссылку на объект, производящий чтение и вывод команд
     */
    User user;
    /**
     * хранит ссылку на объект,исполняющий выбранные команды.
     */
    CommandInvoker invoker;
    /**
     * хранит ссылку на объект, производящий чтение полей класса Ticket.
     */
    TicketFieldsReader ticketreader;
    /**
     * Метод, выполняющий запуск программы.
     *
     * @param path путь к файлу, из/в котор(ого/ый) читаются/сохраняются объекты класса Ticket.
     */
    public void start(String path){
        manager = new CollectionManager();

        fileManager = new FileManager(path);

        user = new User();

        ticketreader = new TicketFieldsReader(user);

        invoker = new CommandInvoker(manager, user, path, ticketreader);
        String data;
        try {
            data = fileManager.read();
            manager.deserialize(data);
            user.printText("Все неповреждённые билеты были успешно загружены в коллекцию.\n");
        } catch (FileException e){
            System.err.println(e.getMessage());
        } catch (IOException e){
            System.err.println("Не удаётся получить доступ к файлу");
        }
        cycle();
    }

    /**
     * Метод, выполняющий чтение команд из строки ввода.
     */
    public void cycle() {
        user.printText("Программа была запущена.\n");
        while (true) {
            user.printText("\nВведите название команды:\n");
            user.printText(">");
            String line = user.readLine();
            invoker.execute(line);
        }
    }
}
