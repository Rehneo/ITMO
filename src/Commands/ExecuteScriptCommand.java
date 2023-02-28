package Commands;

import Collection.CollectionManager;
import Exceptions.RecoursiveException;
import Io.TicketFieldsReader;
import Io.User;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Класс команды, которая считывает и исполняет скрипт.
 */
public class ExecuteScriptCommand implements CommandWithArgs {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
    /**
     * Массив, хранящий аргументы команды.
     */
    private String[] args;
    /**
     * Поле, хранящее ссылку на объект класса User.
     */
    private User user;
    /**
     * Поле, содержащее путь к скрипту.
     */
    private String scriptPath;
    /**
     * Поле, хранящее ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     */
    private TicketFieldsReader ticketReader;
    /**
     * Поле, хранящее объект класса ExecuteScript.Script.
     */
    private Script script;
    /**
     * Конструктор класса.
     *
     * @param manager  - Хранит ссылку на объект класса CollectionManager.
     * @param ticketReader - Хранит ссылку на объект, осуществляющий чтение полей из указанного в user потока ввода.
     * @param script   - Хранит объект класса ExecuteScript.Script.
     */
    public ExecuteScriptCommand(CollectionManager manager, TicketFieldsReader ticketReader, Script script){
        this.manager = manager;
        this.ticketReader = ticketReader;
        this.script = script;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute() {
        try {
            if (args.length == 1){
                scriptPath = args[0];
                if (script.scriptPaths.contains(scriptPath)) throw new RecoursiveException();
                else script.putScript(scriptPath);
            } else throw new IllegalArgumentException("Указаны неправильные аргументы");
            File file = new File(scriptPath);
            if (!file.canWrite() || file.isDirectory() || !file.isFile()) throw new IOException();
            FileInputStream fileInputStream = new FileInputStream(scriptPath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            Scanner scanner = new Scanner(inputStreamReader);
            user = new User(scanner);
            ticketReader = new TicketFieldsReader(user,true);
            CommandInvoker commandInvoker = new CommandInvoker(manager, user, ticketReader, script);
            while(scanner.hasNext()){
                commandInvoker.execute(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Скрипт не найден");
        } catch(NullPointerException e){
            System.err.println("Скрипт не выбран");
        } catch (IOException e){
            System.err.println("Невозможно получить доступ к скрипту: " + e.getMessage());
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        } catch (RecoursiveException e){
            System.err.println("Скрипт" +scriptPath + "уже существует!");
        }

        script.removeScript(scriptPath);
    }
    /**
     * @param args -  аргументы команды.
     * @see CommandWithArgs
     */
    @Override
    public void getArgs(String[] args) {
        this.args = args;
    }
    /**
     * Статический класс, в котором хранится массив адресов скриптов.
     */
    static class Script {
        /**
         * массив, в котором хранятся адреса запущенных скриптов.
         */
        private ArrayList<String> scriptPaths = new ArrayList<String>();
        /**
         * Метод, добавляющий скрипт в массив.
         *
         * @param scriptPath  - путь к скрипту.
         */
        public void putScript(String scriptPath) {
            scriptPaths.add(scriptPath);
        }
        /**
         * Метод, удаляющий скрипт из массива.
         *
         * @param scriptPath - путь к скрипту.
         */
        public void removeScript(String scriptPath) {
            scriptPaths.remove(scriptPath);
        }
    }
    /**
     * @return Описание команды.
     * @see Command
     */
    public String description(){
        return "Считывает и исполняет скрипт из указанного файла.";
    }
    
}
