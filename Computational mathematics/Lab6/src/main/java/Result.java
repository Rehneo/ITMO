public class Result {

    private final double[] x;
    private final double[] y;
    public Result(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public double[] getX(){
        return x;
    }

    public double[] getY(){
        return y;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("xi").append("\t").append("yi").append("\n");
        for (int i = 0; i < x.length; i++) {
            s.append(x[i]).append("\t").append(y[i]);
            s.append("\n");
        }
        return s.toString();
    }
}
