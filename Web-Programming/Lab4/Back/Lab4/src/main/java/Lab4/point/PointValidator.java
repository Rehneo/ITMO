package Lab4.point;

import static Lab4.point.PointValidationStatus.*;
import static Lab4.point.PointValidationStatus.OK;

public class PointValidator {
    public static PointValidationStatus validate(double x, double y, double r){
        if((x < -5) || (x > 3)){
            return INVALID_X_RANGE;
        }
        if((y <= -5) || (y >= 5)){
            return INVALID_Y_RANGE;
        }
        if((r < 1) || (r > 5)){
            return INVALID_R_RANGE;
        }
        return OK;
    }
}
