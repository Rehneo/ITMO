package Io;
import java.util.Scanner;
/**
 * Класс, взаимодействующий с пользователем.
 */
public class User {
    /**
     * хранит ссылку на Scanner, производящий чтение данных из указанного места.
     */
    Scanner scanner;
    /**
     * Конструктор класса без параметров.
     */
    public User(){
        scanner = new Scanner(System.in, "UTF-8");
    }
    /**
     * Конструктор класса с параметрами.
     *
     * @param scanner -  хранит ссылку на объект типа Scanner.
     */
    public User(Scanner scanner){
        this.scanner = scanner;
    }
    /**
     * Метод, производящий чтение данных.
     *
     * @return возвращает прочитанную строку.
     */
    public String readLine(){
        return scanner.nextLine();
    }
    /**
    * Метод, выводящий текст.
    *
    * @param s - строка, которую следует вывести в стандартный поток вывода.
    */
    public void printText(String s) {
        System.out.print(s);
    }
    /**
    * Метод, выводящий текст ошибки.
    *
    * @param s - строка, которую следует вывести в стандартный поток вывода.
    */
    public void printError(String s) {
        System.err.println(s);
    }

}
