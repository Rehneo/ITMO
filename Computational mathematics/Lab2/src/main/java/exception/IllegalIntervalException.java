package exception;

public class IllegalIntervalException extends IllegalArgumentException{

    public IllegalIntervalException(ErrorMessage errorMessage){
        super(errorMessage.getMessage());
    }
}
