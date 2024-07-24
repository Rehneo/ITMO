public class Exponential extends Method{
    public Exponential(double[] x, double[] y) {
        super(x, y);
    }

    @Override
    public Result solve() {
        boolean isNan = false;
        for(int i = 0; i < n; i++){
            if(this.y[i] > 0){
                this.y[i] = Math.log(this.y[i]);
            }else{
                isNan = true;
            }
        }
        double[] r = new Linear(x, y).solve().getR();
        r[1] = Math.exp(r[1]);
        MathFunction f  = new MathFunction(x -> r[1] * Math.exp(x*r[0]));
        String function = r[1] + " * " + "e^(" + r[0] + ")x";
        return new Result(MethodType.EXPONENTIAL, function, f, deviation(f), determination(f), isNan);
    }
}
