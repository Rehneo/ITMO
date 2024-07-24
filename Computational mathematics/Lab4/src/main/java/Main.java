import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        int n = 0;
        final int METHOD_COUNT = 6;
        Plot plot = new Plot("Графики");
        String[] line;
        String path;
        System.out.println("Вы хотите прочитать данные с файла или консоли?");
        System.out.println("1) С консоли");
        System.out.println("2) С файла");
        int read = 0;
        Scanner scanner = new Scanner(System.in);
        try {
            line = scanner.nextLine().split(" ");
            read = Integer.parseInt(line[0]);
            if(read != 2 && read != 1){
                System.out.println("Введите 1 или 2!");
                System.exit(-1);
            }
        }catch (NumberFormatException e){
            System.out.println("Введите 1 или 2!");
            System.exit(-1);
        }
        if(read == 2){
            System.out.println("Введите путь к файлу: ");
            path = scanner.nextLine();
            scanner = new Scanner(new File(path));
        }
        System.out.println("Введите количество входных данных (от 8 до 12): ");
        try {
            line = scanner.nextLine().split(" ");
            n = Integer.parseInt(line[0]);
            if(n < 8 || n > 12){
                System.out.println("Значение должно быть от 8 до 12!");
                System.exit(-1);
            }
        }catch (NumberFormatException e){
            System.out.println("Значение должно быть целочисленным!");
            System.exit(-1);
        }
        double[] x = new double[n];
        double[] y = new double[n];
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        System.out.println("Введите таблицу значений функции в виде пар (x, y) через пробел: ");
        for(int i = 0; i < n; i++){
            line = scanner.nextLine().split(" ");
            try {
                x[i] = Double.parseDouble(line[0]);
                y[i] = Double.parseDouble(line[1]);
                if(x[i] < minX){
                    minX = x[i];
                }
                if(x[i] > maxX){
                    maxX = x[i];
                }
            }catch (NumberFormatException e){
                System.out.println("Введенные данные должны быть числом!");
                System.exit(-1);
            }
        }
        Result[] results = new Result[METHOD_COUNT];
        Method method;
        double minDev = Double.MAX_VALUE;
        List<Integer> mins = new ArrayList<>();
        int best = 0;
        for(int i = 0; i < METHOD_COUNT; i++){
            method = MethodFactory.createMethod(i, x, y);
            results[i] = method.solve();
            if(results[i].getDeviation() < minDev){
                mins.clear();
                minDev = results[i].getDeviation();
                best = i;
                mins.add(i);
            }else if(results[i].getDeviation() == minDev) {
                mins.add(i);
            }
            System.out.println(results[i]);
            System.out.println("\n");
        }
        if(mins.size() < 1){
            System.out.println("Лучшая аппроксимация: " + results[best].getType().getName() + "\n" +
                    "Среднеквадратичное отклонение: " + minDev);
        }else{
            System.out.println("Лучшие аппроксимации: ");
            for(int type: mins){
                System.out.println(results[type].getType().getName() + "\n" +
                        "Среднеквадратичное отклонение: " + minDev);
                System.out.println();
            }
        }
        plot.draw(minX, maxX, results[0].getF(), results[1].getF(), results[2].getF(), results[3].getF(),
                results[4].getF(), results[5].getF());
    }
}
