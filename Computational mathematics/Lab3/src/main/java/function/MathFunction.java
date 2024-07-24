package function;

import java.util.function.Function;

public class MathFunction {
    private final Function<Double, Double> function;

    private Function<Double, Boolean> cond;


    public MathFunction(Function<Double, Double> function) {
        this.function = function;
    }

    public MathFunction(Function<Double, Double> function, Function<Double, Boolean> cond) {
        this.function = function;
        this.cond = cond;
    }


    public boolean check(double x){
        if(cond == null){
            return true;
        }else{
            return cond.apply(x);
        }
    }

    public double at(double x){
        return function.apply(x);
    }


}
