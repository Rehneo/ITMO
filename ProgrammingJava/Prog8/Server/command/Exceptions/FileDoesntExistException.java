package Exceptions;
/**
 * Исключение, выбрасываемое когда не существует файла по указанному пути.
 */
public class FileDoesntExistException extends FileException{

    public FileDoesntExistException() {
        super("Невозможно найти файл");
    }
    
}
