package model;

public final class Point {
    private final double x;
    private final double y;
    private final double r;

    private final boolean isInArea;

    public Point(double x, double y, double r, boolean isInArea){
        this.x = x;
        this.y = y;
        this.r = r;
        this.isInArea = isInArea;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getR(){
        return r;
    }

    public boolean isInArea(){
        return isInArea;
    }
}
