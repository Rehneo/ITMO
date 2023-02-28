package Commands;
/**
 * Класс команды, завершающей работу программы.
 */
public class ExitCommand implements Command{
    /**
     * Конструктор класса.
     */
    public ExitCommand() {
    }

    /**
     * Метод, завершающий работу программы. При завершении выводит соответствующее сообщение.
     */
    @Override
    public void execute() {
        System.out.println("Завершение работы программы.");
        System.exit(0);
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Завершает программу (без сохранения в файл).";
    }
}
