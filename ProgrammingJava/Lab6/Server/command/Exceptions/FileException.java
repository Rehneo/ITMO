package Exceptions;

import java.io.IOException;
/**
 * Базовый класс для всех файловых исключений.
 */
public class FileException  extends IOException{
    public FileException(String msg){
        super(msg);
    }
}
