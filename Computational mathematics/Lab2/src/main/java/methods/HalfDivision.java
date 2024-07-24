package methods;
import function.MathFunction;

//Метод половинного деления
public class HalfDivision extends Method{

    public HalfDivision(double start, double end, double eps, MathFunction f) {
        super(start, end, eps, f);
    }

    @Override
    public Solution solve() {
        double a = start;
        double b = end;
        double x;
        int n = 0;
        while (n < MAX_ITERATION){
            x = (a + b) / 2;
            if(f.at(a)*f.at(x) > 0){
                a = x;
            }else{
                b = x;
            }
            n++;
            if( (Math.abs(a - b) <= eps) & (Math.abs(f.at(x)) <= eps)){
                x = (a+b) / 2;
                return new Solution(x, n, Math.abs(f.at(x)));
            }
        }
        throw new IllegalArgumentException("Не удалось достичь указанной точности");
    }

}
