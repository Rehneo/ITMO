package method;

import function.MathFunction;

public class RectRight extends Method{
    public RectRight(MathFunction f, double a, double b, double eps) {
        super(f, a, b, eps);
    }

    @Override
    public double calculateIntegral(int n) {
        double h = (B - A)/n;
        double s = 0;
        for(int i = 1; i <= n; i++){
            s += f.at(A + i * h);
        }
        return h*s;
    }
}
