package exception;

public enum ErrorMessage {
    ILLEGAL_INTERVAL_SOLUTIONS("На заданном отрезке не выполняется условие f(a)*f(b) < 0, следовательно, на данном отрезке" +
            " либо корней нет, либо их несколько"),

    CONVERGENCE_EXCEPTION("Не выполняется достаточное условие сходимости метода простых итераций: |phi'(xn)| > 1");

    private final String message;
    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
