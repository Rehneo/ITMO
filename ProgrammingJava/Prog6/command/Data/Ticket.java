package Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс объектов(значений) коллекции.
 */
public class Ticket implements Validateable, Serializable {
    /**
     * уникальный идентификатор коллекции. Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля генерируется автоматически.
     */
    private Long id; 
    /**
     * имя объекта класса. Поле не может быть null, Строка не может быть пустой.
     */
    private String name;
    /**
     * координаты объекта класса. Поле не может быть null.
     */
    private Coordinates coordinates;
    /**
     * время создания объекта класса. Поле не может быть null, Значение генерируется автоматически.
     */
    private java.time.LocalDateTime creationDate;
    /**
     * цена объекта класса. Поле может быть null, Значение поля должно быть больше 0.
     */
    private Long price;
    /**
     * показывает, является ли объект класса (билет) возвратным.
     */
    private boolean refundable;
    /**
     * тип объекта класса.Поле не может быть null.
     */
    private TicketType type;
    /**
     * Событие к которому относится объект класса. Поле не может быть null
     */
    private Event event;

    /**
     * Конструктор.
     *
     * @param name            имя объекта класса
     * @param price           цена объекта класса
     * @param coordinates     координаты объекта класса
     * @param refundable      является ли объект класса (билет) возвратным
     * @param type            тип объекта класса
     * @param event           событие, к которому относится объект класса
     */
    public Ticket(String name, Long price, Coordinates coordinates, boolean refundable, TicketType type, Event event){
        this.name = name;
        this.coordinates = coordinates;
        this.price =price;
        this.refundable =refundable;
        this.type = type;
        this.event = event;
        creationDate = LocalDateTime.now();
    }
    /**
     * Метод, возвращающий идентификатор объекта класса
     *
     * @return id - идентификатор объекта класса
     */
    public Long getId() {
        return id;
    }
    /**
     * Метод, присваивающий идентификатор объекту класса
     *
     * @param id идентификатор объекта класса
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Метод, возвращающий цену объекта класса
     *
     * @return price - цена объекта класса
     */
    public Long getPrice() {
        return price;
    }
    /**
     * Метод, возвращающий имя объекта класса
     *
     * @return name - имя объекта класса
     */
    public String getName() {
        return name;
    }
    /**
     * Метод, возвращающий поля объекта класса в формате JSON
     *
     * @return поля объекта класса
     */
    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String strCreationDate = creationDate.format(formatter);
        String s = "";
        s += "{\n";
        s += "  \"id\" : " + Long.toString(id) + ",\n";
        s += "  \"name\" : " + "\"" + name + "\"" + ",\n";
        s += "  \"coordinates\" : " + coordinates.toString() + ",\n";
        s += "  \"creationDate\" : " + "\"" + strCreationDate + "\"" + ",\n";
        if (price!=null) s += "  \"price\" : " +  "\"" + Long.toString(price) + "\"" + ",\n";
        s += "  \"refundable\" : " + "\"" + refundable + "\"" + ",\n";
        s += "  \"type\" : " + "\"" + type.toString() + "\"" + ",\n";
        s += "  \"event\" : " + event.toString() + "\n";
        s += "}";
        return s;

    }
    /**
     * Валидирует правильность полей.
     * @return true, если все верно, иначе false
     */
    @Override
    public boolean validate() {
        return (id != null) && (id.longValue() > 0) && (name != null) &&
        !(name.equals("")) && (coordinates != null) && coordinates.validate() &&
        (creationDate != null) && (price ==null || (price!=null && price.longValue() > 0 )) && (type != null) &&
        (event != null) && (event.validate());
    }
    /**
     * Метод, устанавливающий правило сравнения объектов класса.
     * @param ticket - объект, с которым необходимо сравнить объект,выполняющий метод.
     * @return 1, если первый объект больше, 0, если объекты равны, -1, если первый объект меньше.
     */
    public int compareTo(Ticket ticket) {
        int res = Long.compare(this.price,ticket.getPrice());
        return res;
    }
}