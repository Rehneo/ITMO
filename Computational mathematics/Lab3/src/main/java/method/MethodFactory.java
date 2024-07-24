package method;

import function.MathFunction;

public class MethodFactory {
    public static Method getMethod(MathFunction f, double A, double B, double eps, int i){
        switch (i){
            case 1 -> {
                return new RectLeft(f, A, B, eps);
            }
            case 2 -> {
                return new RectRight(f, A, B, eps);
            }
            case 3 -> {
                return new RectMiddle(f, A, B, eps);
            }
            case 4->{
                return new Trapez(f, A, B, eps);
            }
            case 5->{
                return new Simpson(f, A, B, eps);
            }
        }
        throw new IllegalArgumentException();
    }
}
