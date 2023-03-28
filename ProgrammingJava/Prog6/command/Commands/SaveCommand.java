package Commands;
import Commands.AbstractCommands.*;
import java.io.IOException;
import java.io.PrintStream;

import Collection.CollectionManager;
import Exceptions.CannotExecuteCommandException;
import Exceptions.FileException;
/**
 * Класс команды, сохраняющей коллекцию в файл.
 */
public class SaveCommand extends Command{
    /**
     * Поле, содержащее путь к файлу, куда необходимо сохранить коллекцию.
     */
    private String path;
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Конструктор класса
     * @param manager - Хранит ссылку на объект класса CollectionManager.
     * @param path - Содержит путь к файлу, куда необходимо сохранить коллекцию.
     */
    public SaveCommand(CollectionManager manager, String path){
        super("save");
        this.manager = manager;
        this.path = path;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute(String[] args, InvocationStatus invocationEnum, PrintStream printStream) throws CannotExecuteCommandException {
        if (invocationEnum.equals(InvocationStatus.CLIENT)) {
            throw new CannotExecuteCommandException("У данной команды для клиента нет выполнения.");
        } else if (invocationEnum.equals(InvocationStatus.SERVER)) {
            try {
                manager.save(path);
                printStream.println("Коллекция " + manager.getClass().getSimpleName() + " была сохранена в " + path);
            } catch (FileException e) {
                System.err.println(e.getMessage());
            } catch (IOException e){
                System.err.println("Не удаётся получить доступ к файлу");
            }
        }
    }

    /**
     * @return Описание команды.
     * @see Command
     */
    @Override
    public String description(){
        return "Сохраняет коллекцию в файл.";
    }
}
