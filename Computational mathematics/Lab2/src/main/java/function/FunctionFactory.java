package function;

public class FunctionFactory {
    public static MathFunction getFunction(int i){
        switch (i){
            case 1 ->{
                return new MathFunction(x ->
                        4.45*x*x*x+7.81*x*x-9.62*x-8.17
                );

            }
            case 2 ->{
                return new MathFunction(x ->
                        x*x*x - x + 4
                );
            }
            case 3 ->{
                return new MathFunction(x ->
                        Math.cos(x) + 0.5
                );
            }
        }
        throw new IllegalArgumentException("Введите 1, 2 или 3!");
    }
}
