package Commands;
/**
 * Интерфейс, реализуемый командами с аргументами.
 */
public interface CommandWithArgs extends Command {
    /**
     * Метод, получающий аргументы команды.
     *
     * @param args - аргументы команды.
     */
    void getArgs(String[] args);
}
