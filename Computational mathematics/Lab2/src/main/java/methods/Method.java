package methods;

import exception.IllegalIntervalException;
import function.MathFunction;

import static exception.ErrorMessage.ILLEGAL_INTERVAL_SOLUTIONS;

public abstract class Method {
    protected final double eps;
    protected final double start;
    protected final double end;
    protected final MathFunction f;

    protected final int MAX_ITERATION = 10000;

    public Method(double start, double end, double eps, MathFunction f) {
        this.start = start;
        this.end = end;
        this.eps = eps;
        this.f = f;
        this.checkSolutions();
    }
    public abstract Solution solve();

    public void checkSolutions(){
        double delta = 0.1;
        if(f.at(start)*f.at(end) >= 0){
            throw new IllegalIntervalException(ILLEGAL_INTERVAL_SOLUTIONS);
        }
        boolean sign = (f.derivativeAt(start) > 0);

    }
}
