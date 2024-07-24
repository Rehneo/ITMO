package method;

import function.MathFunction;

public class Simpson extends Method{
    public Simpson(MathFunction f, double a, double b, double eps) {
        super(f, a, b, eps);
    }

    @Override
    public double calculateIntegral(int n) {
        n = 2 * n;
        double h = (B - A) / n;
        double s = f.at(A) + f.at(B);
        for(int i = 1; i < n; i+=2){
            s += 4 * (f.at(A + i*h));
        }
        for(int i = 2; i < n; i+=2){
            s+= 2 * (f.at(A + i * h));
        }
        return (s*h)/3;
    }
}
