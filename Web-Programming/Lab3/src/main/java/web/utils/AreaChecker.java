package web.utils;

import web.model.Points;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("areaChecker")
@SessionScoped
public class AreaChecker implements Serializable {

    public boolean isInArea(Points point){
        return isInArea(point.getX(), point.getY(), point.getR());
    }

    public boolean isInArea(double x, double y, double r){
        return isInRectangle(x,y,r) || isInTriangle(x,y,r) || isInSector(x,y,r);
    }

    private boolean isInRectangle(double x, double y, double r){
        return ( x <= 0 && x >= -r ) && ( y <= 0 && y >= -r );
    }

    private boolean isInTriangle(double x, double y, double r){
        return ( x <= 0 && y >= 0 ) && ( y <= x + r/2 );
    }

    private boolean isInSector(double x, double y, double r){

        return ( x >= 0 && y <= 0 ) && ( x*x + y*y <= r*r );
    }
}
