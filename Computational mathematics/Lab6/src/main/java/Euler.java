public class Euler extends Method{
    protected Euler(double x0, double xn, double y0, double h, double eps, MathFunction f, int pr) {
        super(x0, xn, y0, h, eps, f, pr);
    }

    @Override
    protected double[] calculate(double h1) {
        int n1 = (int) ((xn - x0) / h1);
        double[] x1 = new double[n1 + 1];
        double[] y1 = new double[n1 + 1];
        x1[0] = x0;
        y1[0] = y0;

        for (int i = 1; i <= n1; i++) {
            x1[i] = x0 + i * h1;
        }
        for (int i = 1; i <= n1; i++) {
            y1[i] = y1[i - 1] + (h1 / 2) * (f.at(x1[i - 1], y1[i - 1]) +
                    f.at(x1[i], y1[i - 1] + h1 * f.at(x1[i - 1], y1[i - 1])));

        }
        return y1;
    }
}
