package Exceptions;
/**
 * Базовый класс для всех исключений, связанных с датой.
 */
public class InvalidDataException extends Exception {
    public InvalidDataException(String message){
        super(message);
    }
}
