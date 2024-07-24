public class Cubic extends Method{

    public Cubic(double[] x, double[] y) {
        super(x, y);
    }

    @Override
    public Result solve() {
        double sx = 0, sx2 = 0, sx3 = 0, sx4 = 0, sx5 = 0, sx6 = 0;
        double sy = 0, sxy = 0, sx2y = 0, sx3y = 0;
        for(int i = 0; i < n; i++){
            sx += x[i];
            sx2 += Math.pow(x[i], 2);
            sx3 += Math.pow(x[i], 3);
            sx4 += Math.pow(x[i], 4);
            sx5 += Math.pow(x[i], 5);
            sx6 += Math.pow(x[i], 6);
            sy += y[i];
            sxy += x[i]*y[i];
            sx2y += Math.pow(x[i], 2) * y[i];
            sx3y += Math.pow(x[i], 3) * y[i];
        }
        double[][] x_i = {
                {n, sx, sx2, sx3},
                {sx, sx2, sx3, sx4},
                {sx2, sx3, sx4, sx5},
                {sx3, sx4, sx5, sx6}
        };
        double[] y_i = {sy, sxy, sx2y, sx3y};
        double[] result = solveLinearSystem(x_i, y_i);
        double a0 = result[0], a1 = result[1], a2 = result[2], a3 = result[3];
        MathFunction f= new MathFunction(x -> a3*x*x*x + a2*x*x + a1*x + a0);
        String function = a3 + "x^3" + " + " + a2 +"x^2" + " + " + a3 + "x" + " + " + a0;
        return new Result(MethodType.CUBIC, function,f, deviation(f), determination(f), false);
    }
}
