package method;

import function.MathFunction;

public class RectMiddle extends Method{
    public RectMiddle(MathFunction f, double a, double b, double eps) {
        super(f, a, b, eps);
    }

    @Override
    public double calculateIntegral(int n) {
        double s = 0;
        double h = (B - A)/n;
        double m;
        for(int i = 1; i <= n; i++){
            m = ((A + (i - 1) * h) + (A + i * h))/2;
            s += f.at(m);
        }
        return h*s;
    }
}
