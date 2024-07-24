import java.util.function.Function;

public class MathFunction {
    private final Function<Double, Double> f;

    private final String description;
    public MathFunction(Function<Double, Double> f, String description) {
        this.f = f;
        this.description = description;
    }

    public double at(double x){
        return f.apply(x);
    }

    @Override
    public String toString(){
        return description;
    }
}
