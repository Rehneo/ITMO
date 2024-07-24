import java.util.function.Function;

public class MathFunction {
    private final Function<Double, Double> f;

    public MathFunction(Function<Double, Double> f) {
        this.f = f;
    }

    public double at(double x){
        return f.apply(x);
    }
}
