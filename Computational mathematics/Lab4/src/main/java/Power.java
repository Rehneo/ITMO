public class Power extends Method{
    public Power(double[] x, double[] y) {
        super(x, y);
    }

    @Override
    public Result solve() {
        boolean isNan = false;
        for(int i = 0; i < n;i++){
            if(x[i] < 0 || y[i] < 0){
                isNan = true;
            }
            this.x[i] = Math.log(this.x[i]);
            this.y[i] = Math.log(this.y[i]);
        }
        double[] r  = new Linear(x , y).solve().getR();
        r[1] = Math.exp(r[1]);
        MathFunction f = new MathFunction(x -> r[1]*Math.pow(x, r[0]));
        String function = r[1] + " * " + "x^(" + r[0] + ")";
        return new Result(MethodType.POWER, function, f, deviation(f), determination(f), isNan);
    }
}
