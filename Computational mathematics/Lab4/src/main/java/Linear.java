import java.util.Arrays;

public class Linear extends Method{

    public Linear(double[] x, double[] y){
        super(x, y);
        this.a = new double[2];
    }
    @Override
    public Result solve() {
        double a, b;
        double sx = 0, sxx = 0, sy = 0, sxy = 0;
        for (int i = 0; i < n; i++) {
            sx += x[i];
            sxx += x[i] * x[i];
            sy += y[i];
            sxy += x[i] * y[i];

        }
        double delta = sxx * n - sx * sx;
        double delta1 = sxy * n - sx * sy;
        double delta2 = sxx * sy - sx * sxy;
        a = delta1 / delta;
        b = delta2 / delta;
        MathFunction f = new MathFunction(x -> a * x + b);
        String function = a + "x" + " + " + b;
        return new Result(MethodType.LINEAR, function, f, deviation(f), correlation(), new double[]{a, b}, determination(f));
    }

    private double correlation() {
        double x_avg = Arrays.stream(x).sum() / n;
        double y_avg = Arrays.stream(y).sum() / n;
        double numerator = 0;
        double denominator1 = 0;
        double denominator2 = 0;
        for (int i = 0; i < n; i++) {
            numerator += (x[i] - x_avg) * (y[i] - y_avg);
            denominator1 += Math.pow(x[i] - x_avg, 2);
            denominator2 += Math.pow(y[i] - y_avg, 2);
        }
        double denominator = Math.sqrt(denominator1*denominator2);
        return numerator/denominator;
    }
}
