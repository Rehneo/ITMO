package method;

import function.MathFunction;

public abstract class Method {

    protected final double A;
    protected final double B;
    protected final MathFunction f;

    private final double eps;

    private final int MAX_ITERATION = 10000;

    private final int N = 4;

    public Method(MathFunction f, double a, double b, double eps) {
        this.f = f;
        this.A = a;
        this.B = b;
        this.eps = eps;
    }

    public Solution solve(){
        System.out.print("N = " + N+" ");
        double I = calculateIntegral(N);
        System.out.print("I = " + I);
        int n = 2 * N;
        double I_next = calculateIntegral(n);
        System.out.println(" N = " + n+" " + "I =  " + I_next);
        while(Math.abs(I_next - I) > eps){
            I = I_next;
            n = 2*n;
            I_next = calculateIntegral(n);
            System.out.println(" N = " + n+" " + "I =  " + I_next);
        }
        return new Solution(I_next, n);
    }

    public abstract double calculateIntegral(int n);
}
