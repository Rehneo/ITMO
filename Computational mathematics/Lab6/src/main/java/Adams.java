
public class Adams extends Method{

    protected Adams(double x0, double xn, double y0, double h, double eps, MathFunction f) {
        super(x0, xn, y0, h, eps, f, 0);
    }

    @Override
    public Result solve() {
        return new Result(x, calculate(h));
    }

    @Override
    protected double[] calculate(double h1) {
        Result result = new Runge(x0, xn, y0, h1, eps, f, 4).solve();
        y[1] = result.getY()[1];
        y[2] = result.getY()[2];
        y[3] = result.getY()[3];
        for (int i = 4; i <= n; i++) {
            double yp = y[i-1] + (h1/24) * (55*f.at(x[i-1], y[i-1]) - 59*f.at(x[i-2], y[i-2]) +
                    37*f.at(x[i-3], y[i-3]) - 9*f.at(x[i-4], y[i-4]));
            double yk = y[i-1] + (h1/24) * (9*f.at(x[i], yp) + 19*f.at(x[i-1], y[i-1]) -
                    5*f.at(x[i-2], y[i-2]) + f.at(x[i-3], y[i-3]));
            while (Math.abs(yk-yp) > eps){
                yp = yk;
                yk = y[i-1] + (h1/24) * (9*f.at(x[i], yp) + 19*f.at(x[i-1], y[i-1]) -
                        5*f.at(x[i-2], y[i-2]) + f.at(x[i-3], y[i-3]));
            }
            y[i] = yk;
        };
        return y;
    }
}
