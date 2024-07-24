public enum MethodType{
    LINEAR("Линейная"),
    QUADRATIC("Квадратная"),
    CUBIC("Кубическая"),
    POWER("Степенная"),
    EXPONENTIAL("Показательная"),
    LOGARITHMIC("Логарифмическая");

    private final String name;
    MethodType(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
