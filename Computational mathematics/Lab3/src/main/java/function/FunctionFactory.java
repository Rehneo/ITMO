package function;

public class FunctionFactory {
    public static MathFunction getFunction(int i){
        switch (i){
            case 1 ->{
                return new MathFunction(x ->
                        2*x*x*x-9*x*x-7*x+11
                );

            }
            case 2 ->{
                return new MathFunction(x ->
                        x*x*x + 2*x*x -3*x-12
                );
            }
            case 3 ->{
                return new MathFunction(Math::sin);
            }
            case 4 ->{
                return new MathFunction(x -> 1/x, x -> x!=0);
            }
        }
        throw new IllegalArgumentException();
    }
}
