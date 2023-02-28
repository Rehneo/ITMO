package Commands;

/**
 * Интерфейс, реализация которого приведена в командах.
 */

public interface Command {
     /**
     * Метод, исполняющий команду.
     */
    void execute();
    /**
     * Метод, выводящий описание команды.
     * @return description - описание команды.
     */
    String description();
}
