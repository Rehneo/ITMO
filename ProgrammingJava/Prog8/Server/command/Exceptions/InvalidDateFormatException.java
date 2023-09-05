package Exceptions;
/**
 * Исключение, выбрасываемое когда формат даты не соответствует заданному формату.
 */
public class InvalidDateFormatException extends InvalidDataException {
    public InvalidDateFormatException(){
        super("Формат даты должен быть yyyy-MM-dd HH:mm");
    }
}
