package Lab4.point;

import Lab4.entity.Point;
import lombok.Data;

@Data
public class PointResponse {
    private double x;
    private double y;
    private double r;
    private boolean result;

    public PointResponse(double x, double y, double r, boolean result){
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    public PointResponse(Point point){
        this.x = point.getX();
        this.y = point.getY();
        this.r = point.getR();
        this.result = point.isResult();
    }
}
