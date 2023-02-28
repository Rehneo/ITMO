package Commands;

import java.io.IOException;

import Collection.CollectionManager;
import Exceptions.FileException;
/**
 * Класс команды, сохраняющей коллекцию в файл.
 */
public class SaveCommand implements Command{
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
        this.manager = manager;
        this.path = path;
    }
    /**
     * Метод, исполняющий команду
     */
    @Override
    public void execute() {
        try {
            manager.save(path);
            System.out.println("Коллекция была сохранена в " + path);
        } catch (FileException e) {
            System.err.println(e.getMessage());
        } catch (IOException e){
            System.err.println("Не удаётся получить доступ к файлу");
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Сохраняет коллекцию в файл.";
    }
}
