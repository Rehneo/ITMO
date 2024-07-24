package methods;

import function.MathFunction;

public class Chord extends Method{
    public Chord(double start, double end, double eps, MathFunction f) {
        super(start, end, eps, f);
    }

    @Override
    public Solution solve() {
        double a = start;
        double b = end;
        double x;
        double x_prev = a;
        int n = 0;
        while (n < MAX_ITERATION){
            x = (a*f.at(b) - b*f.at(a))/(f.at(b) - f.at(a));
            n++;
            if( (Math.abs(x - x_prev) <= eps)){
                return new Solution(x, n, Math.abs(f.at(x)));
            }
            if(f.at(a)*f.at(x) > 0){
                a = x;
            }else{
                b = x;
            }
            x_prev = x;
        }
        throw new IllegalArgumentException("Не удалось достичь указанной точности");
    }
}
