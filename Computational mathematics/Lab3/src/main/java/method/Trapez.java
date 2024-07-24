package method;

import function.MathFunction;

public class Trapez extends Method{

    public Trapez(MathFunction f, double a, double b, double eps) {
        super(f, a, b, eps);
    }

    @Override
    public double calculateIntegral(int n) {
        double h = (B - A)/n;
        double s = 0;
        for(int i = 1; i <= n; i++){
            s += (f.at(A + (i -1) * h) + f.at(A + i * h))/2;
        }
        return s*h;
    }
}
