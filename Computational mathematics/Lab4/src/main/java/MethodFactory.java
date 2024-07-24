public class MethodFactory {
    public static Method createMethod(int i, double[] x, double[] y){
        switch (i){
            case 0 -> {return new Linear(x, y);}
            case 1 -> {return new Quadratic(x, y);}
            case 2 -> {return new Cubic(x, y);}
            case 3 -> {return new Power(x, y);}
            case 4 -> {return new Exponential(x, y);}
            case 5 -> {return new Logarithmic(x, y);}
        }
        return new Linear(x, y);
    }
}
