package function;

public enum FunctionType {
    FIRST("4.45*x^3 + 7.81*x^2 - 9.62*x - 8.17"),
    SECOND("x^3 - x + 4"),
    THIRD("cos(x) + 0.5");

    public final String functionName;
    FunctionType(String s) {
        this.functionName = s;
    }
}
