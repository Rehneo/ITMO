import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;

public abstract class Method {
    protected double[] a;
    protected double[] x;
    protected double[] y;
    protected final int n;
    protected double[] e;
    protected double[] initial_x;
    protected double[] initial_y;

    public Method(double[] x, double[] y){
        n = x.length;
        this.x = new double[n];
        this.y = new double[n];
        this.initial_x = x;
        this.initial_y = y;
        for(int i = 0; i < n; i++){
            this.x[i] = x[i];
            this.y[i] = y[i];
        }
        this.e = new double[n + 1];
    }

    public abstract Result solve();


    protected double deviation(MathFunction f) {
        double s = 0;
        for(int i = 0; i < n; i++){
            s += Math.pow(initial_y[i] - f.at(initial_x[i]), 2);
            e[i] = initial_y[i] - f.at(initial_x[i]);
        }
        return s;
    }

    protected double determination(MathFunction f){
        double s = 0;
        double fAvg = 0;
        for(int i = 0; i < n; i++){
            fAvg += f.at(initial_x[i]);
        }
        fAvg = fAvg/n;
        double numerator = 0;
        double denominator = 0;
        for(int i = 0; i < n; i++){
            numerator += Math.pow(initial_y[i] - f.at(initial_x[i]), 2);
            denominator += Math.pow(initial_y[i] - fAvg, 2);
        }
        return 1 - (numerator/denominator);
    }
    protected double[] solveLinearSystem(double[][] a, double[] b) {
        DecompositionSolver solver = new LUDecomposition(new Array2DRowRealMatrix(a)).getSolver();
        return solver.solve(new ArrayRealVector(b)).toArray();
    }
}
