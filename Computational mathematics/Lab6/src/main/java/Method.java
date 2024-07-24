

public abstract class Method {

    protected int n;
    protected double[] x;
    protected double[] y;
    protected double h;
    protected final MathFunction f;
    protected final double y0;
    protected final double x0;
    protected final double xn;
    protected final double eps;
    protected final int pr;

    protected Method(double x0, double xn, double y0, double h, double eps, MathFunction f, int pr) {
        this.x0 = x0; this.xn = xn; this.y0 = y0; this.h = h; this.eps = eps;
        this.f = f;
        this.pr = pr;
        n = (int) ((xn - x0) / h);
        x = new double[n + 1]; y = new double[n + 1];
        x[0] = x0; y[0] = y0;
        for (int i = 1; i <= n; i++) {
            x[i] = x0 + i * h;
        }
    }


    public Result solve(){
        double[] y1;
        double[] y2;
        int k = 1;
        do{
            y1 = calculate(h);
            y2 = calculate(h/2);
            h = h/2;
            k *= 2;
            System.out.println(h);
        }while (Math.abs(y1[y1.length - 1]-y2[y2.length - 1])/(Math.pow(2,pr) - 1) > eps);
        k /= 2;
        for(int i = 0; i < y1.length;i++){
            if(i % k == 0){
                y[i/k] = y1[i];
            }
        }
        return new Result(x, y);
    }



    protected abstract double[] calculate(double h1);
}
