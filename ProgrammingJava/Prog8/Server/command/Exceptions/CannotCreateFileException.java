package Exceptions;
/**
 * Исключение, выбрасываемое когда невозможно создать файл.
 */
public class CannotCreateFileException extends FileException {

    public CannotCreateFileException() {
        super("Невозможно создать файл");
    }
    
}
