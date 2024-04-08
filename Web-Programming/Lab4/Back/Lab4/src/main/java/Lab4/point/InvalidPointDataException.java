package Lab4.point;
public class InvalidPointDataException extends Exception{
    private final PointValidationStatus status;


    public InvalidPointDataException(PointValidationStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage(){
        return status.getMessage();
    }

    public PointValidationStatus getStatus(){
        return this.status;
    }
}
