import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataReader {
    private static Scanner scanner;


    public static ReadResult read(int method){
        if(method == 2){
            return readFromFunction();
        }
        scanner = new Scanner(System.in);
        return readFromTable(scanner);
    }

    public static ReadResult read(String path) throws FileNotFoundException {
        scanner = new Scanner(new File(path));
        return readFromTable(scanner);
    }
    private static ReadResult readFromTable(Scanner scanner){
        String[] line;
        int n = 0;
        System.out.println("Введите количество входных данных (от 4 до 12):");
        try {
            line = scanner.nextLine().split(" ");
            n = Integer.parseInt(line[0]);
            if(n < 4 || n > 12){
                System.out.println("Значение должно быть от 4 до 12!");
                System.exit(-1);
            }
        }catch (NumberFormatException e){
            System.out.println("Значение должно быть целочисленным!");
            System.exit(-1);
        }
        double[] x = new double[n];
        double[] y = new double[n];
        System.out.println("Введите таблицу значений функции в виде пар (x, y) через пробел: ");
        for(int i = 0; i < n; i++){
            line = scanner.nextLine().split(" ");
            try {
                x[i] = Double.parseDouble(line[0]);
                y[i] = Double.parseDouble(line[1]);
            }catch (NumberFormatException e){
                System.out.println("Введенные данные должны быть числом!");
                System.exit(-1);
            }
        }
        double h = x[1] - x[0];
        boolean isFinite = true;
        for(int i = 2; i < n; i++){
            if (Math.abs((x[i] - x[i - 1]) - h) > 0.0001) {
                isFinite = false;
                break;
            }
        }
        double point = readPoint();
        return new ReadResult(point, x, y, isFinite);
    }

    private static ReadResult readFromFunction(){
        scanner = new Scanner(System.in);
        int choice = 1;
        String[] line;
        System.out.println("Выберите функцию для интерполяции: ");
        System.out.println("1) sin(x)");
        System.out.println("2) x^2");
        System.out.println("3) x^3");
        try {
            line = scanner.nextLine().split(" ");
            choice = Integer.parseInt(line[0]);
            if(choice < 1 || choice > 3){
                System.out.println("Введите 1, 2 или 3!");
                System.exit(-1);
            }
        }catch (NumberFormatException e){
            System.out.println("Введите 1, 2, или 3!");
            System.exit(-1);
        }
        MathFunction f = null;
        switch (choice){
            case 1 -> f = new MathFunction(Math::sin, "sin(x)");
            case 2 -> f = new MathFunction(x -> x*x, "x^2");
            case 3 -> f = new MathFunction(x -> x*x*x, "x^3");
        }
        double start = 0, end = 0;
        System.out.println("Введите начало и конец исследуемого интервала через пробел: ");
        line = scanner.nextLine().split(" ");
        try {
            start = Double.parseDouble(line[0]);
            end = Double.parseDouble(line[1]);
        }catch (NumberFormatException e){
            System.out.println("Введенные данные должны быть числом!");
            System.exit(-1);
        }
        int n = 0;
        System.out.println("Введите количество точек на интервале (больше 8): ");
        try {
            line = scanner.nextLine().split(" ");
            n = Integer.parseInt(line[0]);
            if(n < 3){
                System.out.println("Значение должно быть больше 2!");
                System.exit(-1);
            }
        }catch (NumberFormatException e){
            System.out.println("Значение должно быть целочисленным!");
            System.exit(-1);
        }
        double[] x = new double[n];
        double[] y = new double[n];
        x[0] = start;
        y[0] = f.at(x[0]);
        double h = (end - start)/n;
        for(int i = 1; i < n; i++){
            x[i] = x[i-1] +  h;
            y[i] = f.at(x[i]);
        }
        double point = readPoint();
        return new ReadResult(point, x, y, true);
    }

    private static double readPoint(){
        double point = 0;
        System.out.println("Введите координату x для аппроксимации: ");
        try{
            String[] line = scanner.nextLine().split(" ");
            point = Double.parseDouble(line[0]);
        }catch (NumberFormatException e){
            System.out.println("Значение должно быть числом!");
            System.exit(-1);
        }
        System.out.println();
        return point;
    }
}
