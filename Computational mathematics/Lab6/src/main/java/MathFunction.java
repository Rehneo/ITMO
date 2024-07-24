import java.util.function.BiFunction;
import java.util.function.Function;

public class MathFunction {
    private final BiFunction<Double, Double, Double> f;

    private final Function<Double, Double> solution;

    public MathFunction(BiFunction<Double, Double, Double> f,
                        Function<Double, Double> solution) {
        this.f = f;
        this.solution = solution;
    }

    public double at(double x, double y){
        return f.apply(x, y);
    }

    public double solAt(double x){
        return solution.apply(x);
    }
}
