package Json;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import com.google.gson.*;
import Exceptions.InvalidDateFormatException;
import static Utils.DateConverter.*;
/**
 * Класс ,читающий дату из данных формата JSON.
 */
public class LocalDateDeserializer implements JsonDeserializer<LocalDateTime> {
    /**
     * Метод, возвращающий прочитанную дату из данных формата JSON.
     * @return дата.
     * @throws JsonParseException
     */
    @Override
    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try{
            return parseLocalDate(json.getAsJsonPrimitive().getAsString());
        }
        catch (InvalidDateFormatException e){
            throw new JsonParseException("");
        }
    }
}
