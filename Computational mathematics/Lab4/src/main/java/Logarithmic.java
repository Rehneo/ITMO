public class Logarithmic extends Method{
    public Logarithmic(double[] x, double[] y) {
        super(x, y);
    }

    @Override
    public Result solve() {
        boolean isNan = false;
        for(int i = 0; i < n; i++){
            if(this.x[i] < 0){
                isNan = true;
            }
            this.x[i] = Math.log(this.x[i]);
        }
        double[] r = new Linear(x,y).solve().getR();
        MathFunction f = new MathFunction(x -> r[0] * Math.log(x) + r[1]);
        String function = r[0] + "ln(x)" + " + " + r[1];
        double deviation = deviation(f);
        return new Result(MethodType.LOGARITHMIC, function, f, deviation(f), determination(f), isNan);
    }
}
