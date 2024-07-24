package function;

import java.util.function.BiFunction;

public class MathBinaryFunction{
    private final BiFunction<Double, Double, Double> function;
    public static final double delta = 1e-6;

    public MathBinaryFunction(BiFunction<Double, Double, Double> function) {
        this.function = function;
    }

    public double at(double x, double y){
        return this.function.apply(x,y);
    }

    public double derivativeXat(double x, double y){
       return (this.at(x + delta, y) - this.at(x,y)) / delta;
    }
    public double derivativeYat(double x, double y){
        return (this.at(x, y + delta) - this.at(x,y)) / delta;
    }
}
