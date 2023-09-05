package Exceptions;
/**
 * Исключение, выбрасываемое когда было введено некорректное значение поля.
 */
public class InvalidInputDataException extends Exception{
    public InvalidInputDataException(){
    }

    public InvalidInputDataException(String message){
        super(message);
    }
}
