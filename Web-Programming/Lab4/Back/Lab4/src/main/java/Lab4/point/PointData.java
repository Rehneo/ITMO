package Lab4.point;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PointData {
    @NotNull(message = "X must not be null or empty")
    @NotEmpty(message = "X must not be null or empty")
    private String x;
    @NotNull(message = "Y must not be null or empty")
    @NotEmpty(message = "Y must not be null or empty")
    private String y;
    @NotNull(message = "R must not be null or empty")
    @NotEmpty(message = "R must not be null or empty")
    private String r;
}
