package Data;

import java.io.Serializable;

/**
 * Класс событий, к которым относятся элементы коллекции.
 */
public class Event implements Validateable, Serializable {
    /**
     * уникальный идентификатор события. Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля генерируется автоматически.
     */
    private Long id;
    /**
     * название события. Поле не может быть null, Строка не может быть пустой.
     */
    private String name;
    /*
     * описание события. Длина строки не должна быть больше 1801, Поле может быть null.
     */
    private String description;
    /**
     * Конструктор.
     *
     * @param name            название события
     * @param description     описание события
     */
    public Event(String name, String description){
        this.name = name;
        this.description = description;
        id = EventManager.generateNextId();
    }
    /**
     * Метод, возвращающий название события.
     *
     * @return name - название события.
     */
    public String getName(){
        return name;
    }
    /**
     * Метод, возвращающий уникальный идентификатор события.
     *
     * @return id - идентификатор события.
     */
    public Long getId(){
        return id;
    }
    /**
     * Метод, возвращающий описание события.
     *
     * @return description - описание события.
     */
    public String getDescription(){
        return description;
    }
    /**
     * Метод, возвращающий поля объекта класса в формате JSON
     *
     * @return поля объекта класса
     */
    @Override
    public String toString(){
        String s = "";
        s += "{";
        s += "\"id\" : " +  "\"" + id.toString() + "\"" + ", ";
        if (description!=null) {
            s += "\"name\" : " +  "\"" + name + "\"" + ", ";
            s += "\"description\" : " + "\"" + description + "\"" + "}";
        } else{
            s += "\"name\" : " + "\"" + name + "\"" + "}";
        }
        return s;
    }
    /**
   * Валидирует правильность полей.
   * @return true, если все верно, иначе false
   */
    @Override
    public boolean validate() {
        return (id > 0) && (id != null) && (name!=null) && !(name.equals("")) && (description ==null || (description!=null && description.length() <= 1801));
    }
}