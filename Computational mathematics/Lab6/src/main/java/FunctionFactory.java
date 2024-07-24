import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionFactory {

    public static MathFunction get(int i, double x0, double y0){
        BiFunction<Double, Double, Double> c;
        Function<Double,Double> sol;
        switch (i){
            case 2 -> {
                c = (x, y) -> ((-y - Math.pow(x, 2) - 2 * x - 2) / (-Math.exp(x)));
                sol = (x) -> (c.apply(x0, y0) * Math.exp(x) - Math.pow(x, 2) - 2 * x - 2);
                return new MathFunction((x, y) -> (Math.pow(x, 2) + y), sol);
            }
            case 3 ->{
                c = (x, y) -> (y * Math.exp(Math.cos(x)));
                sol = (x) -> (c.apply(x0, y0)/Math.exp(Math.cos(x)));
                return new MathFunction((x, y) -> (Math.sin(x) * y), sol);
            }
            default -> {
                c = (x, y) -> (-Math.exp(x) / y - x * Math.exp(x));
                sol = (x) -> (-Math.exp(x) / (x * Math.exp(x) + c.apply(x0, y0)));
                return new MathFunction((x,y)-> (y + (1 + x)*Math.pow(y,2)), sol);
            }
        }
    }
}
