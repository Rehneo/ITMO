public class ReadResult {
    private final double point;
    private final double[] x;
    private final double[] y;

    private final boolean isFinite;
    public ReadResult(double point, double[] x, double[] y, boolean isFinite) {
        this.point = point;
        this.x = x;
        this.y = y;
        this.isFinite = isFinite;
    }

    public double getPoint(){
        return point;
    }

    public double[] getX(){
        return x;
    }

    public double[] getY() {
        return y;
    }

    public boolean isFinite(){
        return isFinite;
    }
}
