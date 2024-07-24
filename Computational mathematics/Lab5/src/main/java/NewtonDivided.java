public class NewtonDivided extends Method{
    protected NewtonDivided(double point, double[] x, double[] y) {
        super(point, x, y);
    }

    @Override
    public Result solve() {
        double N = 0;
        double p = 1;
        for(int i = 0; i < n; i++){
            N += f(i, 0)*p;
            p *= (point - x[i]);
        }
        return new Result(point, N, MethodType.NEWTON_DIVIDED);
    }

    private double f(int degree, int i){
        if(degree == 0){
            return y[i];
        }
        return (f(degree - 1, i + 1) - f(degree - 1, i))/(x[i + degree] - x[i]);
    }
}
