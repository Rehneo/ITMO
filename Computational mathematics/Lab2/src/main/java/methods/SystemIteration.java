package methods;

import exception.IllegalIntervalException;
import function.MathBinaryFunction;

import static exception.ErrorMessage.CONVERGENCE_EXCEPTION;

public class SystemIteration {
    private final double eps;
    private double x;
    private double y;
    private final MathBinaryFunction f1;
    private final MathBinaryFunction f2;

    private final MathBinaryFunction f = new MathBinaryFunction((x,y) -> x*x*x + y*y*y -6*x + 3);
    private final  MathBinaryFunction g = new MathBinaryFunction((x,y) -> x*x*x - y*y*y-6*y+2);
    //FIRST("x^3 + y^3 - 6x + 3 = 0\nx^3 - y^3 - 6y + 2 = 0"),
    //SECOND("x^3 = y \nx^2 - y = 1");
    protected final int MAX_ITERATION = 10000;

    public SystemIteration(double eps, double x0, double y0, boolean system) {
        this.eps = eps;
        this.x = x0;
        this.y = y0;
        if(!system){
            f1 = new MathBinaryFunction((x,y)->(Math.pow(x, 3) + Math.pow(y, 3) + 3) / 6);
            f2 = new MathBinaryFunction((x,y)->(Math.pow(x, 3) - Math.pow(y, 3) + 2) / 6);
        }else {
            f1 = new MathBinaryFunction((x,y)->(Math.cbrt(y)));
            f2 = new MathBinaryFunction((x,y)->(x*x - 1));
        }
    }

    public SystemSolution solve(){
        if(!ifConvergence(x,y)){
            System.out.println(CONVERGENCE_EXCEPTION.getMessage());
        }
        int n = 0;
        double xn;
        double yn;
        while (n < MAX_ITERATION){
            xn = f1.at(x,y);
            yn = f2.at(x,y);
            n++;
            if(Math.abs(xn - x) < eps && Math.abs(yn - y) < eps){
                return new SystemSolution(xn, yn, n, f.at(xn,yn), g.at(xn,yn));
            }
            x = xn;
            y = yn;
        }
        throw new IllegalArgumentException("Не удалось достичь указанной точности");
    }

    private boolean ifConvergence(double x, double y){
        return !(Math.abs(f1.derivativeXat(x, y)) > 1) && !(Math.abs(f1.derivativeYat(x, y)) > 1) &&
                !(Math.abs(f2.derivativeXat(x, y)) > 1) && !(Math.abs(f2.derivativeYat(x, y)) > 1);
    }
}
