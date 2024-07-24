public class Quadratic extends Method {

    public Quadratic(double[] x, double[] y) {
        super(x, y);
    }

    @Override
    public Result solve() {
        double sx = 0, sx2 = 0, sx3 = 0, sx4 = 0;
        double sy = 0, sxy = 0, sx2y = 0;
        for(int i = 0; i < n; i++){
            sx += x[i];
            sx2 += Math.pow(x[i], 2);
            sx3 += Math.pow(x[i], 3);
            sx4 += Math.pow(x[i], 4);
            sy += y[i];
            sxy += x[i]*y[i];
            sx2y += Math.pow(x[i], 2) * y[i];
        }
        double[][] x_i = {{n, sx, sx2}, {sx, sx2, sx3}, {sx2, sx3, sx4}};
        double[] y_i = {sy, sxy, sx2y};
        double[] result = solveLinearSystem(x_i, y_i);
        double a0 = result[0], a1 = result[1], a2 = result[2];
        MathFunction f= new MathFunction(x -> a2*x*x + a1*x + a0);
        String function = a2 + "x^2" + " + " + a1 +"x" + " + " + a0;
        return new Result(MethodType.QUADRATIC, function, f, deviation(f), determination(f), false);
    }
}
