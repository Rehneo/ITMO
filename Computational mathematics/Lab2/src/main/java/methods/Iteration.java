package methods;
import exception.IllegalIntervalException;
import function.MathFunction;

import static exception.ErrorMessage.CONVERGENCE_EXCEPTION;

//Метод простой итерации
public class Iteration extends Method{
    private final MathFunction phi;
    public Iteration(double start, double end, double eps, MathFunction f) {
        super(start, end, eps, f);
        double fS = f.derivativeAt(start);
        double fE = f.derivativeAt(end);
        double m = Math.max(Math.abs(fS), Math.abs(fE));
        if(Math.abs(fS) > Math.abs(fE)){
            if(fS > 0){
                phi = new MathFunction(x -> (x + (-1/m) * f.at(x)));
            }else{
                phi = new MathFunction(x -> (x + (1/m) * f.at(x)));
            }
        }else{
            if(fE > 0){
                phi = new MathFunction(x -> (x + (-1/m) * f.at(x)));
            }else{
                phi = new MathFunction(x -> (x + (1/m) * f.at(x)));
            }
        }
    }

    @Override
    public Solution solve() throws IllegalIntervalException {
        double x0 = f.derivativeSecondAt(start)*f.at(start) > 0?start : end;
        int n = 0;
        double xn = x0;
        double x_next;
        System.out.println("Вывод значения производных функции: ");
        System.out.println(phi.derivativeAt(start) + " " + phi.derivativeAt(end));
        while(n < MAX_ITERATION){
            if(Math.abs(phi.derivativeAt(xn)) > 1){
                System.out.println(CONVERGENCE_EXCEPTION.getMessage());
            }
            x_next = phi.at(xn);
            n++;
            if((Math.abs(x_next - xn) < eps) && Math.abs(f.at(x_next)) < eps){
                return new Solution(x_next, n, Math.abs(f.at(x_next)));
            }
            xn = x_next;
        }
        throw new IllegalArgumentException("Не удалось достичь указанной точности");
    }


}
