package Data;
import java.util.HashSet;
/**
 * Класс работающий с объектами класса Event (класса событий, к которым относятся элементы коллекции).
 */
public class EventManager {
    /**
     * поле, содержащее уникальные идентификаторы событий.
     */
    private final static HashSet<Long> uniqueIds = new HashSet<>();
    /**
     * поле, содержащее последний уникальный идентификатор события.
     */
    private static long lastId;
    /**
     * Метод, генерирующий следующий уникальный идентификатор события.
     * @return id - уникальный идентификатор события.
     */
    public static long generateNextId(){
        if (uniqueIds.isEmpty()){
            uniqueIds.add((long) 1);
            lastId = 1;
            return 1;
        } lastId += 1;
        if(uniqueIds.contains(lastId)){
            while (uniqueIds.contains(lastId)) lastId+=1;
        }
        uniqueIds.add(lastId);
        return lastId;
    }
}
