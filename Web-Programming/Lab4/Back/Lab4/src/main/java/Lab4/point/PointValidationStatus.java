package Lab4.point;

public enum PointValidationStatus {
    OK("Данные точки корректны"),
    INVALID_DATA("Радиус области и координаты точки должны быть числом"),
    INVALID_X_RANGE("Координата X должна быть на отрезке [-5, 3]"),
    INVALID_Y_RANGE("Координата Y должна быть в интервале (-5, 5)"),
    INVALID_R_RANGE("Радиус области должен быть на отрезке [1, 5]");

    private final String message;

    PointValidationStatus(String s) {
        this.message = s;
    }

    public String getMessage(){
        return this.message;
    }
}
