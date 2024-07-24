package method;

import function.MathFunction;

public class RectLeft extends Method{


    public RectLeft(MathFunction f, double a, double b, double eps) {
        super(f, a, b, eps);
    }

    @Override
    public double calculateIntegral(int n) {
        double h = (B - A)/n;
        double s = 0;
        for(int i = 0; i < n; i++){
            s += f.at(A + i * h);
        }
        return h*s;
    }
}
