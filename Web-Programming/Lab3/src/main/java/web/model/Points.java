package web.model;
import lombok.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name="points")
@Getter
@Setter
@NoArgsConstructor
@Named("pointsBean")
@SessionScoped
public class Points implements Serializable {

    public Points(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @NotNull
    @Column(name = "x", nullable = false)
    private double x;
    @NotNull
    @Column(name = "y", nullable = false)
    private double y;
    @NotNull
    @Column(name = "r", nullable = false)
    private double r = 1;
    @NotNull
    @Column(name = "result", nullable = false)
    private boolean result;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Points points = (Points) o;

        if (id != points.id) return false;
        if (Double.compare(points.x, x) != 0) return false;
        if (Double.compare(points.y, y) != 0) return false;
        if (Double.compare(points.r, r) != 0) return false;
        return result == points.result;
    }

    @Override
    public int hashCode() {
        int result1;
        long temp;
        result1 = id;
        temp = Double.doubleToLongBits(x);
        result1 = 31 * result1 + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result1 = 31 * result1 + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(r);
        result1 = 31 * result1 + (int) (temp ^ (temp >>> 32));
        result1 = 31 * result1 + (result ? 1 : 0);
        return result1;
    }

    @Override
    public String toString(){
        return x + " " + y + " " + r + " " + result;
    }

}
