package Lab4.point;

public class AreaChecker {
    public static boolean check(double x, double y, double r){
        return isInRectangle(x,y,r) || isInTriangle(x,y,r) || isInSector(x,y,r);
    }

    private static boolean isInRectangle(double x, double y, double r){
        return ( x >= 0 && x <= r/2 ) && ( y <= 0 && y >= -r );
    }

    private static boolean isInTriangle(double x, double y, double r){
        return ( x <= 0 && y <= 0 ) && ( y >= -2*x - r );
    }

    private static boolean isInSector(double x, double y, double r){
        return ( x <= 0 && y >= 0 ) && ( x*x + y*y <= r*r );
    }
}
