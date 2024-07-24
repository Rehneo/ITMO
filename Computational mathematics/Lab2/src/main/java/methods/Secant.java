package methods;
import function.MathFunction;

//Метод секущих
public class Secant extends Method {

    public Secant(double start, double end, double eps, MathFunction f) {
        super(start, end, eps, f);
    }

    @Override
    public Solution solve() {
        double x0 = end;
        double x1 = (start + end) / 2;
        int n = 0;
        double x2;
        while (n < MAX_ITERATION){
            x2 = x1 - ((x1 - x0)/(f.at(x1) - f.at(x0))) * f.at(x1);
            if((Math.abs(f.at(x2)) <= eps)){
                return new Solution(x2, n, Math.abs(f.at(x2)));
            }
            n++;
            x0 = x1;
            x1 = x2;
        }
        throw new IllegalArgumentException("Не удалось достичь указанной точности");
    }
}
