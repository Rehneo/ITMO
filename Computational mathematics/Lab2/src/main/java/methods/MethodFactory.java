package methods;

import function.MathFunction;

public class MethodFactory {

    public static Method getMethod(int i,double start, double end, double eps, MathFunction f){
        switch (i){
            case 1 -> {
                return new HalfDivision(start, end, eps, f);
            }
            case 2 -> {
                return new Secant(start, end, eps, f);
            }
            case 3 -> {
                return new Iteration(start, end, eps, f);
            }
            case 4->{
                return new Chord(start, end, eps ,f);
            }
        }
        throw new IllegalArgumentException("Введите 1, 2 или 3!");
    }
}
