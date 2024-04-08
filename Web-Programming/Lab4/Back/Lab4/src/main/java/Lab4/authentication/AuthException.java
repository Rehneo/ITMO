package Lab4.authentication;

public class AuthException extends Exception{
    private final AuthStatus status;


    public AuthException(AuthStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage(){
        return status.getMessage();
    }

    public AuthStatus getStatus(){
        return this.status;
    }
}
