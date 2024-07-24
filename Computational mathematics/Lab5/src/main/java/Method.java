public abstract class Method {
    protected final double[] x;
    protected final double point;
    protected final double[] y;
    protected final int n;

    protected Method(double point, double[] x, double[] y) {
        this.point = point;
        this.x = x;
        this.y = y;
        this.n = x.length;
    }

    public abstract Result solve();

}
