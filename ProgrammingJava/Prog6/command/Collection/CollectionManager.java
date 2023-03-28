package Collection;
import java.io.PrintStream;
import java.util.*;

import com.google.gson.*;
import Json.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private java.time.LocalDateTime initDate;
    /**
     * Коллекция, над которой осуществляется работа.
     */
    private TreeMap<Integer,Ticket> treeMap;
    /**
     * поле, содержащее уникальные идентификаторы элементов коллекции.
     */
    private HashSet<Long> uniqueIds;
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
    public String show(){
        int cnt = treeMap.size();
        if (cnt == 0){
            System.out.println("Коллекция пуста");
            return "";
        } else {
            String s = "[\n";
            for (Integer k: treeMap.keySet()){
                cnt -= 1;
                String m[] = treeMap.get(k).toString().split("\n");
                int cn = m.length;
                for (String l: m){
                    cn -= 1;
                    s += "  " + l; 
                    if ((cn == 0) && (cnt != 0)) s += ",";
                    s += "\n";
                }
            }
        s+= "]";
        return s;
        }
    }
    /**
     * Метод, добавляющий в коллекцию новый элемент с заданным ключом.
     * @param id - ключ.
     * @param ticket - элемент коллекции.
     */
    public void insert(Integer id, Ticket ticket, PrintStream printStream) {
        if (treeMap.get(id) == null) {
            ticket.setId(generateNextId());
            treeMap.put(id, ticket);
        } else System.out.println("Элемент с данным ключом уже существует");
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
    public void clear(){
        treeMap.clear();
        uniqueIds.clear();
    }
    /**
     * Метод, обновляющий элемент коллекции по его полю id.
     * @param id - идентификатор элемента коллекции.
     * @param ticket - новый элемент коллекции, который заменит старый.
     */
    public void updateById(Long id, Ticket ticket, PrintStream printStream){
        if (treeMap.size() == 0){
            System.out.println("Коллекция пуста");
        }else{
            boolean l = true;
            for (Integer k: treeMap.keySet()){
                Ticket tick = treeMap.get(k);
                if (tick.getId().equals(id)){
                    ticket.setId(id);
                    treeMap.put(k, ticket);
                    l = false;
                }
            }
            if (l){
                System.out.println("Элемента с заданным ID нет в коллекции");
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
    public void removeGreaterKey(Integer key){
        if (treeMap.size() == 0){
            System.out.println("Коллекция пуста");
        }else{
            Iterator<Map.Entry<Integer, Ticket>> it = treeMap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<Integer, Ticket> item = it.next();
                if (item.getKey() > key){
                    uniqueIds.remove(treeMap.get(item.getKey()).getId());
                    it.remove();
                }
            }
            System.out.println("Заданные элементы успешно удалены");
        }
    }
    /**
     * Метод, выводящий сумму значений поля price элементов коллекции.
     */
    public long sumOfPrice(){
        if (treeMap.size() == 0){
            System.out.println("Коллекция пуста");
            return 0;
        }else{
            long sum = 0;
            for (Ticket ticket: treeMap.values()){
                sum += ticket.getPrice();
            }
            return sum;
        }
    }
    /**
     * Метод, выводящий любой элемент из коллекции, значение поля name которого является минимальным.
     */
    public String minByName(){
        if (treeMap.size() == 0){
            System.out.println("Коллекция пуста");
            return "";
        }else{
            Ticket ticket = treeMap.get(treeMap.firstKey());
            for (Ticket ticket1: treeMap.values()){
                if (ticket1.getName().compareTo(ticket.getName()) > 0){
                    ticket = ticket1;
                }
            }
            return ticket.toString();
        }
    }
    /**
     * Метод, выводящий элементы коллекции в порядке возрастания.
     */
    public String printAscending(){
        if (treeMap.size() == 0){
            System.out.println("Коллекция пуста");
            return "";
        }else{
            int cnt = treeMap.size();
            List<Ticket> list  = new ArrayList<>(treeMap.values());
            list.sort(Ticket::compareTo);
            String s = "[\n";
            for (Ticket ticket : list){
                cnt -= 1;
                String m[] = ticket.toString().split("\n");
                int cn = m.length;
                for (String l: m){
                    cn -= 1;
                    s += "  " + l; 
                    if ((cn == 0) && (cnt != 0)) s += ",";
                    s += "\n";
                }
            }
        s+= "]";
        return s;
        }
    }
    /**
     * Метод, считывающий коллекцию из данных формата JSON.
     * @param json - данные формата JSON.
     * @return true если успешно, false если - нет.
     */
    public boolean deserialize(String json){
        boolean success = true;
        try {
            if (json == null || json.equals("")){
                treeMap =  new TreeMap<Integer, Ticket>();
            } else {
                Type collectionType = new TypeToken<TreeMap<Integer,Ticket>>(){}.getType();
                Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateDeserializer())
                .registerTypeAdapter(collectionType, new CollectionDeserializer(uniqueIds))
                .create();
                treeMap = gson.fromJson(json.trim(), collectionType);
            }
        } catch (JsonParseException e){
            success = false;
            System.err.println("Недопустимые данные");
        } 
        return success;
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
            String s = "[\n";
            for (Integer k: treeMap.keySet()){
                cnt -= 1;
                String m[] = treeMap.get(k).toString().split("\n");
                int cn = m.length;
                for (String l: m){
                    cn -= 1;
                    s += "  " + l; 
                    if ((cn == 0) && (cnt != 0)) s += ",";
                    s += "\n";
                }
            }
        s+= "]";
        return s;
        }
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
    public void save(String path) throws IOException{
        FileManager fileManager = new FileManager(path);
        String s = serializeCollection();
        fileManager.write(s);
    }
    /**
     * Метод, удаляющий все элементы коллекции, которые превышают заданный.
     * @param ticket - заданный элемент.
     */
    public void removeGreater(Ticket ticket){
        if (treeMap.size() == 0){
            System.out.println("Коллекция пуста");
        } else{
            Iterator<Map.Entry<Integer, Ticket>> it = treeMap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<Integer, Ticket> item = it.next();
                if (ticket.compareTo(item.getValue()) < 0){
                    uniqueIds.remove(treeMap.get(item.getKey()).getId());
                    it.remove();
                }
            }
        }
    }
}
