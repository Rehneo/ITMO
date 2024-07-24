package function;

public enum FunctionType {
    FIRST("2x^3 - 9x^2 - 7x + 11"),
    SECOND("x^3 + 2x^2 - 3x - 12"),
    THIRD("sin(x)"),
    FOURTH("1/x");

    public final String functionName;
    FunctionType(String s) {
        this.functionName = s;
    }
}

