package Commands;

import Collection.CollectionManager;
import Commands.AbstractCommands.Command;
import Commands.AbstractCommands.CommandContainer;
import Commands.AbstractCommands.InvocationStatus;
import Exceptions.CannotExecuteCommandException;
import Exceptions.RecoursiveException;
import Io.TicketFieldsReader;
import Io.User;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import Exceptions.*;
/**
 * Класс команды, которая считывает и исполняет скрипт.
 */
public class ExecuteScriptCommand extends Command {
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager manager;
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
     */
    public ExecuteScriptCommand(User user, TicketFieldsReader ticketReader, Script script){
        super("execute_script");
        this.user = user;
        this.ticketReader = ticketReader;
        this.script = script;
    }
    /**
     * Конструктор класса.
     */
    public ExecuteScriptCommand(CollectionManager manager) {
        super("execute_script");
        this.manager = manager;
    }
    /**
     * Метод, исполняющий команду.
     */
    @Override
    public void execute(String[] args, InvocationStatus invocationEnum, PrintStream printStream) throws CannotExecuteCommandException {
        if (invocationEnum.equals(InvocationStatus.CLIENT)) {
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
                CommandInvoker commandInvoker = new CommandInvoker(user, ticketReader, script);
                super.result.add(scriptPath);
                PrintStream nullStream = (new PrintStream(new OutputStream() {
                    public void write(int b) {
                        //DO NOTHING
                    }
                }));

                while (scanner.hasNext()) {
                    if (commandInvoker.executeClient(scanner.nextLine(), nullStream)) {
                        super.result.add(commandInvoker.getLastContainer());
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
                System.out.println("Скрипт " + scriptPath + " уже существует");
                printStream.println("Скрипт " + scriptPath + " уже существует");
            }
            script.removeScript(scriptPath);
            throw new CannotExecuteCommandException("Принудительное завершение работы команды execute_script");
        } else if (invocationEnum.equals(InvocationStatus.SERVER)) {
            printStream.println("Файл, который исполняется скриптом: " + this.getResult().get(0));
            Object[] arr = result.toArray();
            arr = Arrays.copyOfRange(arr, 1, arr.length);
            CommandContainer[] containerArray = Arrays.copyOf(arr, arr.length, CommandContainer[].class);
            CommandInvoker commandInvoker = new CommandInvoker(manager);
            for (CommandContainer command : containerArray) {
                commandInvoker.executeServer(command.getName(), command.getResult(), printStream);
            }
        }
    }
    /**
     * Статический класс, в котором хранится массив адресов скриптов.
     */
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
