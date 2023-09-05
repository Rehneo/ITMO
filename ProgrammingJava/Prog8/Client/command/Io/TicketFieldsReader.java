package Io;

import Data.Coordinates;
import Data.Event;
import Data.Ticket;
import Data.TicketType;
import Exceptions.InvalidInputDataException;

import java.util.NoSuchElementException;

/**
 * Класс ,читающий поля объекта коллекции из консоли/скрипта.
 */
public class TicketFieldsReader {
    /**
     * поле, которое хранит ссылку на объект типа User.
     */
    private final User user;
    /**
     * true, если данные читаются из скрипта, false - если нет.
     */
    private boolean isScript = false;
    /**
     * Конструктор объекта класса.
     * @param user - хранит ссылку на объект типа User.
     */
    public TicketFieldsReader(User user){
        this.user = user;
    }
    /**
     * Конструктор объекта класса.
     * @param user - хранит ссылку на объект типа User.
     * @param isScript - true, если данные читаются из скрипта, false - если нет.
     */
    public TicketFieldsReader(User user, boolean isScript){
        this.user = user;
        this.isScript = isScript;
    }
    /**
     * Метод, производящий чтение данных из консоли.
     * @return возращает объект типа Ticket
     */
    public Ticket read() throws InvalidInputDataException{
        return new Ticket(readName(), readPrice(), readCoordinates(), readRefundable(), readType() , readEvent());
    }
    /**
     * Метод, производящий чтение поля name объекта класса Ticket.
     * @return возращает значение поля name объекта класса Ticket.
     * @throws  InvalidInputDataException - если в скрипте содержатся некорректные данные.
     */
    public String readName() throws InvalidInputDataException{
        String str;
        if(!isScript) {
            while (true) {
                user.printText("Введите название : ");
                str = user.readLine().trim();
                if (str.equals("")) user.printError("Имя не может быть null или пустой строкой");
                else return str;
            }
        } else{
            try {
                str = user.readLine().trim();
                if (str.equals("")){
                    user.printError("В скрипте указаны неправильные данные. Имя не может быть null или пустой строкой");
                    throw new InvalidInputDataException();
                } else return str;
            } catch (NoSuchElementException e){
                throw new InvalidInputDataException();
            }
        }
    }
    /**
     * Метод, производящий чтение поля price объекта класса Ticket.
     * @return возращает значение поля price объекта класса Ticket.
     * @throws  InvalidInputDataException - если в скрипте содержатся некорректные данные.
     */
    public Long readPrice() throws InvalidInputDataException{
        long price;
        if(!isScript) {
            while (true) {
                try {
                    user.printText("Введите цену билета (больше нуля): ");
                    String s = user.readLine().trim();
                    if (s.equals("")) throw new InvalidInputDataException();
                    price = Long.parseLong(s);
                    if (price <= 0) throw new InvalidInputDataException();
                    return price;
                } catch (InvalidInputDataException e) {
                    System.err.println("Значение цены должно быть больше нуля и не null");
                } catch (NumberFormatException e) {
                    System.err.println("Число должно быть типа Long");
                }
            }
        }else {
            try {
                String s = user.readLine().trim();
                if (s.equals("")) throw new InvalidInputDataException();
                price = Long.parseLong(s);
                if (price <= 0) throw new InvalidInputDataException();
                return price;
            } catch (InvalidInputDataException e) {
                System.err.println("В скрипте указаны неправильные данные. Значение цены должно быть больше нуля и не null");
                throw new InvalidInputDataException();
            } catch (NumberFormatException e) {
                System.err.println("В скрипте указаны неправильные данные. Число должно быть типа Long");
                throw new InvalidInputDataException();
            } catch (NoSuchElementException e){
                System.err.println("Найдена пустая строка");
                throw new InvalidInputDataException();
            }
        }
    }
    /**
     * Метод, производящий чтение поля refundable объекта класса Ticket.
     * @return возращает значение поля refundable объекта класса Ticket.
     * @throws  InvalidInputDataException - если в скрипте содержатся некорректные данные.
     */
    public boolean readRefundable() throws InvalidInputDataException {
        String s;
        if(!isScript) {
            while (true) {
                user.printText("Является ли билет возвратным? Введите \"yes\" или \"no\": ");
                s = user.readLine().trim();
                if (s.equals("yes")) {
                    return true;
                } else if (s.equals("no")) {
                    return false;
                } else {
                    user.printError("Неправильный ввод. Введите \"yes\" или \"no\": ");
                }
            }
        }else{
            try {
                s = user.readLine().trim();
                if (s.equals("yes")) {
                    return true;
                } else if (s.equals("no")) {
                    return false;
                } else {
                    user.printError("Неправильный ввод. В скрипте должно быть введено \"yes\" или \"no\": ");
                    throw new InvalidInputDataException();
                }
            } catch (NoSuchElementException e){
                System.err.println("Найдена пустая строка. В скрипте некорректные данные");
                throw new NoSuchElementException();
            }
        }
    }
    /**
     * Метод, производящий чтение поля coordinates объекта класса Ticket.
     * @return возращает значение поля coordinates объекта класса Ticket.
     * @throws  InvalidInputDataException - если в скрипте содержатся некорректные данные.
     */
    public Coordinates readCoordinates() throws InvalidInputDataException {
        return new Coordinates(readX(), readY());
    }
    /**
     * Метод, производящий чтение поля x объекта класса Coordinates.
     * @return возращает значение поля x объекта класса Coordinates.
     * @throws  InvalidInputDataException - если в скрипте содержатся некорректные данные.
     */
    public Float readX() throws InvalidInputDataException{
        float x;
        if(!isScript) {
            while (true) {
                try {
                    user.printText("Введите координату X: ");
                    String s = user.readLine().trim();
                    if (s.equals("")) throw new InvalidInputDataException();
                    x = Float.parseFloat(s);
                    return x;
                } catch (InvalidInputDataException e) {
                    System.out.println("Поле не может быть null");
                } catch (NumberFormatException e) {
                    System.err.println("Число должно быть типа Float");
                }
            }
        }else{
            try {
                String s = user.readLine().trim();
                if (s.equals("")) throw new InvalidInputDataException();
                x = Float.parseFloat(s);
                return x;
            } catch (InvalidInputDataException e) {
                System.out.println("В скрипте указаны неправильные данные. Поле не может быть null");
                throw e;
            } catch (NumberFormatException e) {
                System.err.println("В скрипте указаны неправильные данные. Число должно быть типа Float");
                throw new InvalidInputDataException();
            } catch (NoSuchElementException e){
                System.err.println("Найдена пустая строка");
                throw new InvalidInputDataException();
            }
        }
    }
    /**
     * Метод, производящий чтение поля y объекта класса Coordinates.
     * @return возращает значение поля y объекта класса Coordinates.
     * @throws  InvalidInputDataException - если в скрипте содержатся некорректные данные.
     */
    public float readY() throws InvalidInputDataException{
        float y;
        if(!isScript) {
            while (true) {
                try {
                    user.printText("Введите координату Y (Y <= 396): ");
                    String s = user.readLine().trim();
                    if (s.equals("")) y = 0;
                    else {
                        y = Float.parseFloat(s);
                        if (y > 396) throw new InvalidInputDataException();
                    }
                    return y;
                } catch (InvalidInputDataException e) {
                    System.out.println("Значение поля должно быть меньше 396");
                } catch (NumberFormatException e) {
                    System.err.println("Число должно быть типа float ");
                } catch (NoSuchElementException e){
                    System.err.println("Найдена пустая строка");
                    throw e;
                }
            }
        }else{
            try {
                String s = user.readLine().trim();
                if (s.equals("")) y = 0;
                else {
                    y = Float.parseFloat(s);
                    if (y > 396) throw new InvalidInputDataException();
                }
                return y;
            } catch (InvalidInputDataException e) {
                System.out.println("В скрипте указаны неправильные данные. Значение поля должно быть меньше 396");
                throw e;
            } catch (NumberFormatException e) {
                System.err.println("В скрипте указаны неправильные данные. Число должно быть типа float ");
                throw new InvalidInputDataException();
            } catch (NoSuchElementException e){
                System.err.println("Найдена пустая строка");
                throw new InvalidInputDataException();
            }
        }
    }
    /**
     * Метод, производящий чтение поля type объекта класса Ticket.
     * @return возращает значение поля type объекта класса Ticket.
     * @throws  InvalidInputDataException - если в скрипте содержатся некорректные данные.
     */
    public TicketType readType() throws InvalidInputDataException {
        TicketType type;
        if(!isScript) {
            while (true) {
                try {
                    user.printText("Допустимые значения билета : \n");
                    for (TicketType t : TicketType.values()) {
                        user.printText(t.name() + "\n");
                    }
                    user.printText("Введите тип билета: ");
                    type = TicketType.valueOf(user.readLine().toUpperCase().trim());
                    return type;
                } catch (IllegalArgumentException e) {
                    System.err.println("Неправильный ввод типа билета");
                }
            }
        }else{
            try {
                type = TicketType.valueOf(user.readLine().toUpperCase().trim());
                return type;
            } catch (IllegalArgumentException e) {
                System.err.println("В скрипте указаны неправильные данные. Неправильный ввод типа билета");
                throw new InvalidInputDataException();
            } catch (NoSuchElementException e){
                System.err.println("Найдена пустая строка");
                throw new InvalidInputDataException();
            }
        }
    }
    /**
     * Метод, производящий чтение поля event объекта класса Ticket.
     * @return возращает значение поля event объекта класса Ticket.
     * @throws  InvalidInputDataException - если в скрипте содержатся некорректные данные.
     */
    public Event readEvent() throws InvalidInputDataException{
        return new Event(readEventName(), readDescription());
    }
    /**
     * Метод, производящий чтение поля name объекта класса Event.
     * @return возращает значение поля name объекта класса Event.
     * @throws  InvalidInputDataException - если в скрипте содержатся некорректные данные.
     */
    public String readEventName() throws InvalidInputDataException{
        String s;
        if(!isScript) {
            while (true) {
                user.printText("Введите название Ивента: ");
                s = user.readLine().trim();
                if (s.equals("")) user.printError("Имя не может быть null или пустой строкой");
                else return s;
            }
        }else{
            try {
                s = user.readLine().trim();
                if (s.equals("")) {
                    user.printError("В скрипте указаны неправильные данные. Имя не может быть null или пустой строкой");
                    throw new InvalidInputDataException();
                }else return  s;
            } catch (NoSuchElementException e){
                System.err.println("В скрипте указаны неправильные данные. Имя не может быть null или пустой строкой");
                throw new InvalidInputDataException();
            }
        }
    }
    /**
     * Метод, производящий чтение поля description объекта класса Event.
     * @return возращает значение поля description объекта класса Event.
     */
    public String readDescription(){
        String s;
        if(!isScript) {
            user.printText("Введите описание Ивента (Если описания нет, введите пустую строку): ");
            s = user.readLine().trim();
            if (s.equals("")) return null;
            else return s;
        }else{
            try {
                s = user.readLine().trim();
                if (s.equals("")) return null;
                else return s;
            }catch (NoSuchElementException e){
                return null;
            }
        }
    }
}
