package function;

import java.util.function.Function;

public class MathFunction {
    private final Function<Double, Double> function;

    public static final double delta = 1e-6;

    public MathFunction(Function<Double, Double> function) {
        this.function = function;
    }
    public double at(double x){
        return function.apply(x);
    }

    public double derivativeAt(double x){
        return (this.at(x + delta) - this.at(x)) / delta;
    }

    public double derivativeSecondAt(double x){
        return (this.derivativeAt(x + delta) - this.derivativeAt(x))/ delta;
    }
}
