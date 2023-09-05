package Data;
/**
 * Интерфейс, который реазилуют объекты, поля которых необходимо проверять.
 */
public interface Validateable {
    /**
     * Валидирует правильность полей.
     * @return true, если все верно, иначе false
     */
    boolean validate();
}
