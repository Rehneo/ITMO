package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Database.CollectionDatabaseManager;
import Database.UserData;
import Exceptions.CannotExecuteCommandException;
import Exceptions.RecoursiveException;
import Io.TicketFieldsReader;
import Io.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;

public class AdminExecuteScriptCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private final CollectionManager manager;
    private final CollectionDatabaseManager cdm;
    /**
     * Поле, содержащее путь к скрипту.
     */
    private String scriptPath;
    /**
     * Поле, хранящее объект класса ExecuteScript.Script.
     */
    private Script script;

    /**
     * Конструктор класса.
     *
     * @param manager  - Хранит ссылку на объект класса CollectionManager.
     * @param script   - Хранит объект класса ExecuteScript.Script.
     */
    public AdminExecuteScriptCommand(CollectionManager manager,CollectionDatabaseManager cdm, Script script){
        this.manager = manager;
        this.script = script;
        this.cdm = cdm;
    }
    @Override
    public void execute(String[] args, PrintStream printStream, UserData userData, Lock lock) throws CannotExecuteCommandException {
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
            User user = new User(scanner);
            TicketFieldsReader ticketReader = new TicketFieldsReader(user, true);
            CommandInvoker commandInvoker = new CommandInvoker(manager,cdm, user, ticketReader, script);
            while(scanner.hasNext()){
                commandInvoker.executeAdmin(scanner.nextLine(), System.out,userData);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Скрипт не найден ");
        } catch(NullPointerException e){
            System.err.println("Скрипт не выбран ");
            throw new RuntimeException(e);
        } catch (IOException e){
            System.err.println("Невозможно получить доступ к скрипту: " + e.getMessage());
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        } catch (RecoursiveException e){
            System.err.println("Скрипт " +scriptPath + " уже существует!");
        }

        script.removeScript(scriptPath);
    }

    static class Script {
        /**
         * массив, в котором хранятся адреса запущенных скриптов.
         */
        private ArrayList<String> scriptPaths = new ArrayList<>();
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
