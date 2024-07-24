public class Result {
    private final MethodType type;
    private final double deviation;

    private double correlation;

    private final String function;

    private final double determination;

    private double[] r;

    private MathFunction f;

    private boolean isNan = false;

    public Result(MethodType type, String function, MathFunction f, double deviation, double correlation, double[] r, double determination){
        this.type = type;
        this.deviation = deviation;
        this.correlation = correlation;
        this.function = function;
        this.r = r;
        this.f = f;
        this.determination = determination;
    }

    public Result(MethodType type, String function, MathFunction f,  double deviation, double determination, boolean isNan){
        this.type = type;
        this.deviation = deviation;
        this.function = function;
        this.f = f;
        this.determination = determination;
        this.isNan = isNan;
    }

    public MathFunction getF(){
        return this.f;
    }
    public double[] getR(){
        return this.r;
    }

    public double getDeviation(){
        return this.deviation;
    }

    public MethodType getType(){
        return this.type;
    }

    @Override
    public String toString(){
        String s;
        if(!isNan){
            s = "Аппроксимация: " + type.getName() +  "\n" +
                    "Функция: " + function + "\n" +
                    "Отклонение: " + deviation + "\n" +
                    "Достоверность: " + determination;
        }else {
            s = "Аппроксимация: " + type.getName() +  "\n" +
                    "Введенные данные меньше нуля! Поэтому логарифм взять то не удалось!" + "\n";
        }
        if(type == MethodType.LINEAR){
            s+= "\n" + "Корреляция: " + correlation;
        }
        return s;
    }
}
