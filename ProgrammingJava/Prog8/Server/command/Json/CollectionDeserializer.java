package Json;
import java.lang.reflect.Type;
import Data.*;
import java.util.HashSet;
import java.util.TreeMap;
import com.google.gson.*;
/**
 * Класс, читающий коллекцию из данных формата JSON.
 */
public class CollectionDeserializer implements JsonDeserializer<TreeMap<Integer, Ticket>> {
    /**
     * поле, содержащее уникальные идентификаторы элементов коллекции.
     */
    private HashSet<Long> uniqueIds;
    /**
     * Конструктор класса.
     *
     * @param uniqueIds - уникальные идентификаторы элементов коллекции.
     */
    public CollectionDeserializer(HashSet<Long> uniqueIds){
        this.uniqueIds = uniqueIds;
    }
    /**
     * Метод, возвращающий прочитанную коллекцию из данных формата JSON.
     * @return коллекция.
     * @throws JsonParseException
     */
    @Override
    public TreeMap<Integer, Ticket> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        TreeMap<Integer, Ticket> tree = new TreeMap<>();
        Integer key = 1;
        JsonArray tickets = json.getAsJsonArray();
        int BadElements = 0;
        for (JsonElement jticket: tickets){
            Ticket ticket = null;
            try {
                if (jticket.getAsJsonObject().entrySet().isEmpty()){
                    System.err.println("Найден пустой билет");
                    throw new JsonParseException("Пустой билет");
                }
                if (!jticket.getAsJsonObject().has("id")){
                    System.err.println("Найден билет без id");
                    throw new JsonParseException("Нет id");
                }
                ticket = (Ticket) context.deserialize(jticket, Ticket.class);
                Long id = ticket.getId();
                if(uniqueIds.contains(id)){
                    System.err.println("В коллекции уже есть билет с id: " + id.toString());
                    throw new JsonParseException("Не уникальное id");
                }
                if(!ticket.validate()){
                    System.err.println("Билет #" + id.toString() + " не удовлетворяет условиям");
                    throw new JsonParseException("Недопустимые данные");
                }
                uniqueIds.add(id);
                tree.put(key, ticket);
                key += 1;
            } catch (JsonParseException | NumberFormatException  e) {
                BadElements += 1;
                System.err.println(e.getMessage());
            }
        }
        if(tree.size() == 0){
            if(BadElements == 0) System.err.println("Пустые данные");
            else System.err.println("Все билеты в данных повреждены");
            throw new JsonParseException("Нет данных");
        }
        if(BadElements == 0) System.out.println("Количество поврежденных билетов: " + BadElements + " (Успех!)");
        else System.out.println("Количество поврежденных билетов: " + BadElements);
        return tree;
    }
    
}
