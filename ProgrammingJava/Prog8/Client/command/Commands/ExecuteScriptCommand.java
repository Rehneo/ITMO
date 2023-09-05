package Commands;
import Commands.AbstractCommands.Command;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;
import Exceptions.RecoursiveException;
import Io.TicketFieldsReader;
import Io.User;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Класс команды, которая считывает и исполняет скрипт.
 */
public class ExecuteScriptCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса User.
     */
    private User user;
    /**
     * Поле, содержащее путь к скрипту.
     */
    private String scriptPath;
    /**
     * Поле, хранящее объект класса ExecuteScript.Script.
     */
    private final Script script;
    /**
     * Конструктор класса.
     */
    public ExecuteScriptCommand(User user, Script script){
        super("execute_script");
        this.user = user;
        this.script = script;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData) throws CannotExecuteCommandException {
        result = new ArrayList<>();
        try {
            if (args.length == 1) {
                scriptPath = args[0].trim();
                if (script.scriptPaths.contains(scriptPath)) throw new RecoursiveException();
                else script.putScript(scriptPath);
            } else throw new IllegalArgumentException();

            File ioFile = new File(scriptPath);
            if (!ioFile.canWrite() || ioFile.isDirectory() || !ioFile.isFile()) throw new IOException();
            FileInputStream fileInputStream = new FileInputStream(scriptPath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            Scanner scanner = new Scanner(inputStreamReader);
            user = new User(scanner);
            TicketFieldsReader ticketReader = new TicketFieldsReader(user,true);
            CommandInvoker commandInvoker = new CommandInvoker(user, ticketReader, script);
            super.result.add(scriptPath);
            while (scanner.hasNext()) {
                if (commandInvoker.executeClient(scanner.nextLine(), printStream, userData)) {
                    super.result.add(commandInvoker.getLastContainer());
                }
                else {
                    throw new CannotExecuteCommandException("В скрипте содержатся недопустимые данные");
                }
            }
            script.removeScript(scriptPath);
            return;
        } catch (FileNotFoundException e) {
            printStream.println("Файл скрипта не найден");
        } catch (NullPointerException e) {
            printStream.println("Не выбран файл, из которого читать скрипт");
        } catch (IOException ex) {
            printStream.println("Невозможно получить доступ к файлу");
        } catch (IllegalArgumentException e) {
            printStream.println("скрипт не передан в качестве аргумента команды, либо кол-во агрументов больше 1");
        } catch (RecoursiveException e) {
            printStream.println("Скрипт " + scriptPath + " уже существует");
        }
        script.removeScript(scriptPath);
        throw new CannotExecuteCommandException("");
    }
    /**
     * Статический класс, в котором хранится массив адресов скриптов.
     */
    static class Script {
        /**
         * массив, в котором хранятся адреса запущенных скриптов.
         */
        private final ArrayList<String> scriptPaths = new ArrayList<>();
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
    @Override
    public String description(){
        return "Считывает и исполняет скрипт из указанного файла.";
    }
    
}
