package function;

public enum BinarySystemType {
    FIRST("x^3 + y^3 - 6x + 3 = 0\nx^3 - y^3 - 6y + 2 = 0"),
    SECOND("x^3 = y \nx^2 - y = 1");

    public final String SystemName;
    BinarySystemType(String s) {
        this.SystemName = s;
    }

}
