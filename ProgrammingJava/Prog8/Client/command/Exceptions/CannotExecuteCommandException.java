package Exceptions;
/**
 * Исключение, выбрасываемое когда команда не может быть исполнена.
 */
public class CannotExecuteCommandException extends Exception {
    public CannotExecuteCommandException(String m) {
        super(m);
    }
}
