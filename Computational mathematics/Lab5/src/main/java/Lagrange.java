public class Lagrange extends Method{

    protected Lagrange(double point, double[] x, double[] y) {
        super(point, x, y);
    }

    @Override
    public Result solve() {
        double ln = 0;
        for(int i = 0; i < n; i++){
            ln += y[i] * (numerator(i)/denominator(i));
        }
        return new Result(point, ln, MethodType.LAGRANGE);
    }

    private double numerator(int i){
        double s = 1;
        for(int j = 0; j < n; j++){
            if(j != i){
                s *= (point - x[j]);
            }
        }
        return s;
    }

    private double denominator(int i){
        double s = 1;
        for(int j = 0; j < n; j++){
            if(j != i){
                s *= (x[i] - x[j]);
            }
        }
        return s;
    }
}
