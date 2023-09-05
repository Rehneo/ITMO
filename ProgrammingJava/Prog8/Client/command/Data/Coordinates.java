package Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Класс, содержащий координаты элементов коллекции.
 */
public class Coordinates implements Validateable, Serializable {
    @Serial
    private final static long serialVersionUID = 59361993293717L;
    /**
     * координата x. Поле не может быть null.
     */
    private Float x; //Поле не может быть null
    /**
     * координата y. Максимальное значение поля: 396.
     */
    private float y;

    /**
     * Конструктор.
     *
     * @param x            координата x
     * @param y            координата y
     */
    public Coordinates(Float x, float y){
        this.x  = x;
        this.y = y;
    }
    /**
     * Метод, возвращающий координату x.
     *
     * @return x - координата x.
     */
    public Float getX(){
        return x;
    }
    /**
     * Метод, возвращающий координату y.
     *
     * @return y - координата y.
     */
    public Float getY(){
        return y;
    }
    /**
     * Метод, возвращающий поля объекта класса в формате JSON
     *
     * @return поля объекта класса
     */
    @Override
    public String toString(){
        String s = "";
        s += "{\"x\" : " + x + ", ";
        s += "\"y\" : " + y + "}";
        return s;
    }
    /**
     * Валидирует правильность полей.
     * @return true, если все верно, иначе false
     */
    @Override
    public boolean validate() {
        return (x != null) && (y <= 396) && !(Float.isNaN(y)) && !(Float.isInfinite(y));
    }
}
