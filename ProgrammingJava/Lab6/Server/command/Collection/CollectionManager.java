package Collection;
import java.io.PrintStream;
import java.util.*;

import com.google.gson.*;
import Json.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

import Data.Ticket;
import File.FileManager;

import com.google.gson.reflect.TypeToken;

/**
 * Класс работающий с коллекциями.
 */
public class CollectionManager {
    /**
     * Время инициализации коллекции.
     */
    private final java.time.LocalDateTime initDate;
    /**
     * Коллекция, над которой осуществляется работа.
     */
    private TreeMap<Integer,Ticket> treeMap;
    /**
     * поле, содержащее уникальные идентификаторы элементов коллекции.
     */
    private final HashSet<Long> uniqueIds;
    /**
     * Конструктор.
     */
    public CollectionManager(){
        treeMap = new TreeMap<>();
        uniqueIds = new HashSet<>();
        initDate = java.time.LocalDateTime.now();
    }
    /**
     * Метод, выводящий информацию о коллекции.
     */
    public String info(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date = initDate.format(formatter);
        String s = "";
        s += "Коллекция: " + treeMap.getClass().getSimpleName() + "\n";
        s += "Тип элементов коллекции: " + Ticket.class.getSimpleName() + "\n";
        s += "Время инициализации коллекции : " + date + "\n";
        s += "Количество элементов в коллекции: " + treeMap.size() + "\n";
        return s;
    }
    /**
     * Метод, генерирующий следующий уникальный идентификатор элемента коллекции.
     * @return id - уникальный идентификатор элемента коллекции.
     */
    public long generateNextId(){
        if (treeMap.size() == 0)
            return 1;
        else {
            long id = treeMap.get(treeMap.lastKey()).getId() + 1;
            if(uniqueIds.contains(id)){
                while (uniqueIds.contains(id)) id+=1;
            }
            uniqueIds.add(id);
            return id;
        }
    }
    /**
     * Метод, выводящий коллекцию в формате JSON.
     */
    public String show(PrintStream printStream){
        int cnt = treeMap.size();
        if (cnt == 0){
            printStream.println("Коллекция пуста");
            return "";
        } else {
            return getString(cnt);
        }
    }
    /**
     * Метод, добавляющий в коллекцию новый элемент с заданным ключом.
     * @param id - ключ.
     * @param ticket - элемент коллекции.
     */
    public void insert(Integer id, Ticket ticket,PrintStream printStream) {
        if (treeMap.get(id) == null) {
            ticket.setId(generateNextId());
            treeMap.put(id, ticket);
            printStream.println("Билет с ключом = " + id + " был добавлен в коллекцию.");
        } else printStream.println("Билет с указанным ключом уже существует!.");
    }
    /**
     * Метод, удаляющий элемент из коллекции по его ключу.
     * @param id - ключ.
     */
    public void removeKey(Integer id){
        Long l = treeMap.get(id).getId();
        uniqueIds.remove(l);
        treeMap.remove(id);
    }
    /**
     * Метод, очищающий коллекцию.
     */
    public void clear(PrintStream printStream){
        treeMap.clear();
        uniqueIds.clear();
        printStream.println("Коллекция " + this.getClass().getSimpleName() + " была очищена.");
    }
    /**
     * Метод, обновляющий элемент коллекции по его полю id.
     * @param id - идентификатор элемента коллекции.
     * @param ticket - новый элемент коллекции, который заменит старый.
     */
    public void updateById(Long id, Ticket ticket, PrintStream printStream){
        if (treeMap.size() == 0){
            printStream.println("Коллекция пуста");
        }else{
            boolean l = true;
            for (Integer k: treeMap.keySet()){
                Ticket tick = treeMap.get(k);
                if (tick.getId().equals(id)){
                    ticket.setId(id);
                    treeMap.put(k, ticket);
                    l = false;
                    printStream.println("Билет с id = " + id + " был обновлён.");
                }
            }
            if (l){
                printStream.println("Билета с указанным id не существует.");
            }
        }
    }
    /**
     * Метод, проверяющий, есть ли в коллекции элемент с заданным id.
     * @return true если да,  false если - нет.
     * @param id - ключ.
     */
    public boolean containsId(Long id){
        return uniqueIds.contains(id);
    }
    /**
     * Метод, удаляющий все элементы из коллекции, ключ которых превышает заданный.
     * @param key - ключ.
     */
    public void removeGreaterKey(Integer key, PrintStream printStream){
        ArrayList<Integer> keys = new ArrayList<>();
        treeMap.entrySet().stream().filter(mapEntry -> mapEntry.getKey() > key)
                .forEach(mapEntry -> keys.add(mapEntry.getKey()));
        keys.forEach(treeMap::remove);
        printStream.println("Билеты, у которых ключ больше " + key + ", были удалены");
    }
    /**
     * Метод, выводящий сумму значений поля price элементов коллекции.
     */
    public long sumOfPrice(PrintStream printStream){
        if (treeMap.size() == 0){
            printStream.println("Коллекция пуста");
            return 0;
        }else{
            AtomicLong sum = new AtomicLong();
            treeMap.forEach((key, value) -> sum.addAndGet(value.getPrice()));
            return sum.get();
        }
    }
    /**
     * Метод, выводящий любой элемент из коллекции, значение поля name которого является минимальным.
     */
    public String minByName(PrintStream printStream){
        if (treeMap.size() == 0){
            printStream.println("Коллекция пуста");
            return "";
        }else{
            Optional<String> optionalTicket = treeMap.values().stream().map(Ticket::getName).max(String::compareTo);
            return optionalTicket.orElse(null);
        }
    }
    /**
     * Метод, выводящий элементы коллекции в порядке возрастания.
     */
    public String printAscending(PrintStream printStream){
        if (treeMap.size() == 0){
            printStream.println("Коллекция пуста");
            return "";
        }else{
            int cnt = treeMap.size();
            List<Ticket> list  = new ArrayList<>(treeMap.values());
            list.sort(Ticket::compareTo);
            StringBuilder s = new StringBuilder("[\n");
            for (Ticket ticket : list){
                cnt -= 1;
                String[] m = ticket.toString().split("\n");
                int cn = m.length;
                for (String l: m){
                    cn -= 1;
                    s.append("  ").append(l);
                    if ((cn == 0) && (cnt != 0)) s.append(",");
                    s.append("\n");
                }
            }
        s.append("]");
        return s.toString();
        }
    }
    /**
     * Метод, считывающий коллекцию из данных формата JSON.
     *
     * @param json - данные формата JSON.
     */
    public void deserialize(String json){
        try {
            if (json == null || json.equals("")){
                treeMap = new TreeMap<>();
            } else {
                Type collectionType = new TypeToken<TreeMap<Integer,Ticket>>(){}.getType();
                Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateDeserializer())
                .registerTypeAdapter(collectionType, new CollectionDeserializer(uniqueIds))
                .create();
                treeMap = gson.fromJson(json.trim(), collectionType);
            }
        } catch (JsonParseException e){
            System.err.println("Недопустимые данные");
        }
    }
    /**
     * Метод, записывающий коллекцию в массив формата JSON.
     * @return s - массив формата JSON.
     */
    public String serializeCollection(){
        int cnt = treeMap.size();
        if (cnt == 0){
            System.out.println("Коллекция пуста");
            return " ";
        } else {
            return getString(cnt);
        }
    }

    private String getString(int cnt) {
        StringBuilder s = new StringBuilder("[\n");
        for (Integer k: treeMap.keySet()){
            cnt -= 1;
            String[] m = treeMap.get(k).toString().split("\n");
            int cn = m.length;
            for (String l: m){
                cn -= 1;
                s.append("  ").append(l);
                if ((cn == 0) && (cnt != 0)) s.append(",");
                s.append("\n");
            }
        }
        s.append("]");
        return s.toString();
    }

    /**
     * Метод, проверяющий, есть ли в коллекции элемент с заданным ключом.
     * @return true если да,  false если - нет.
     * @param key - ключ.
     */
    public boolean containsKey(Integer key){
        return treeMap.containsKey(key);
    }
    /**
     * Метод, сохраняющий коллекцию в формате JSON в заданный файл.
     * @param path - путь к файлу.
     * @throws IOException - если возникнет проблема с записью в файл.
     */
    public void save(String path, PrintStream printStream) throws IOException{
        FileManager fileManager = new FileManager(path);
        String s = serializeCollection();
        fileManager.write(s);
        printStream.println("Коллекция " + this.getClass().getSimpleName() + " была успешно сохранена");
    }
    /**
     * Метод, удаляющий все элементы коллекции, которые превышают заданный.
     * @param ticket - заданный элемент.
     */
    public void removeGreater(Ticket ticket, PrintStream printStream){
        if (treeMap.size() == 0){
            printStream.println("Коллекция пуста");
        } else{
            ArrayList<Integer> keys = new ArrayList<>();
            treeMap.entrySet().stream().filter(mapEntry -> mapEntry.getValue().compareTo(ticket) > 0)
                    .forEach(mapEntry -> keys.add(mapEntry.getKey()));
            keys.forEach(treeMap::remove);
        }
    }
}
