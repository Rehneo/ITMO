package File;
import Exceptions.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * Класс работающий с файлами.
 */
public class FileManager {
    private InputStream inputStream;
    /**
     * путь к файлу, над которым осуществляется работа.
     */
    private String path;
    /**
     * Конструктор класса без параметров.
     */
    public FileManager(){
        path = null;
    }
    /**
     * Конструктор класса с параметрами.
     * @param path - путь к файлу.
     */
    public FileManager(String path){
        this.path = path;
    }
    /**
     * Метод, присваивающий путь объекту класса.
     *
     * @param path - путь к файлу.
     */
    public void setPath(String path){
        this.path = path;
    }
    /**
     * Метод, производящий чтение данных из файла, путь к которому содержится в поле path.
     *
     * @return строка, которая хранит все содержимое данного файла.
     * @throws IOException
     */
    public String read() throws IOException
    {
        String str = "";
        if (path == null) throw new FileException("Пустой путь");
        InputStreamReader reader = null;
        File file = new File(path);
        if (!file.exists()) throw new FileDoesntExistException();
        if(!file.canRead()) throw new FileException("Невозможно прочитать файл");
        if (file.isDirectory()) throw new FileException("Это директория! Необходимо указать путь к файлу");
        inputStream = new FileInputStream(file);
        reader = new InputStreamReader(inputStream);
        int currectSymbol;
        while ((currectSymbol = reader.read()) != -1) {
            str += ((char)currectSymbol);
        }
        reader.close();        
        return str;
    }
    /**
     * Метод, создающий файл.
     * @param file - файл, который необходимо создать.
     * @throws CannotCreateFileException
     */
    private void create(File file) throws CannotCreateFileException{
        try{
            file.createNewFile(); 
        } catch(IOException e){
            throw new CannotCreateFileException();
        }
    }
    /**
     * Метод, записывающий данные в файл, путь к которому содержится в поле path.
     *
     * @param str - данные, которые необходимо записать в файл.
     * @return true если запись произошла успешно, false если - нет.
     * @throws IOException
     */
    public boolean write(String str) throws IOException{
        boolean flag = true;
       
        if (path == null) throw new FileException("Пустой путь");

        File file = new File(path);

        if(!file.exists()) {
            System.out.println("Файла " + path +" не существует, создаётся новый файл по указанному пути");
            create(file);
        };
        if(!file.canWrite()) throw new FileException("Невозможно написать в файл (Недостаточно прав)");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(str);
        writer.close();
        return flag;
    }
}
