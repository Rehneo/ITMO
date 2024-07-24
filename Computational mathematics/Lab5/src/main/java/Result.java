public class Result {
    private final double x;
    private final double y;

    private final MethodType type;

    public Result(double x, double y, MethodType type){
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    @Override
    public String toString(){
        String t = "";
        switch (type){
            case LAGRANGE -> t = "Многочлен Лагранжа: ";
            case NEWTON_DIVIDED -> t = "Многочлен Ньютона с разделенными разностями:  ";
            case NEWTON_FINITE -> t = "Многочлен Ньютона с конечными разностями:  ";
        }
        return t + "\n" +
                "Результат интерполяции: " + "\n"+
                "Координата X: " + x + "\n" +
                "Значение, вычисленное в X: " + y;
    }

}
