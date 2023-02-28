package Utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import Exceptions.InvalidDateFormatException;
/**
 * Класс, читающий дату из строки.
 */
public class DateConverter {
    private static  DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static LocalDateTime parseLocalDate(String s) throws InvalidDateFormatException{
        try {
            return LocalDateTime.parse(s, localDateFormatter );
        } catch (java.time.format.DateTimeParseException e) {
            throw new InvalidDateFormatException();
        } 
    }
}