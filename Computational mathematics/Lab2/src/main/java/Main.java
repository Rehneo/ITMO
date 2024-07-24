import function.BinarySystemType;
import function.FunctionFactory;
import function.FunctionType;
import function.MathFunction;
import methods.Method;
import methods.MethodFactory;
import methods.SystemIteration;
import plot.Plot;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Plot plot = new Plot("График");
        System.out.println("Что хотите сделать?");
        System.out.println("1) Решить уравнение");
        System.out.println("2) Решить систему уравнений");
        int i;
        try {
            i = Integer.parseInt(scanner.nextLine());
            if(!(i == 1 || i == 2)) {
                throw new IllegalArgumentException();
            }
        }catch (Exception e){
            throw new IllegalArgumentException("Введите 1 или 2!");
        }
        if(i == 1){
            try {
                solveEquation(scanner, plot);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else{
            try {
                solveSystem(scanner, plot);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }


    static void solveEquation(Scanner scanner, Plot plot){
        double start;
        double end;
        double eps;
        MathFunction function;
        Method method;
        System.out.println("Выберите уравнение, корень которого необходимо найти: ");
        for(FunctionType ft: FunctionType.values()){
            System.out.println((ft.ordinal() + 1) + ") " + ft.functionName);
        }
        try {
            function = FunctionFactory.getFunction(Integer.parseInt(scanner.nextLine()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Введите 1, 2 или 3!");
        }
        plot.draw( -5, 5, function);
        System.out.println("Введите значение погрешности вычислений: ");
        try {
            eps = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Значение погрешности должно быть числом!");
        }
        System.out.println("Введите начало и конец интервала через пробел: ");
        try {
            String[] line = scanner.nextLine().split(" ");
            start = Double.parseDouble(line[0]);
            end = Double.parseDouble(line[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Прочитан некорректный интервал!");
        }
        System.out.println("Выберите метод: ");
        System.out.println("1) Метод половинного деления");
        System.out.println("2) Метод секущих");
        System.out.println("3) Метод простой итерации");
        int i;
        try {
            i = Integer.parseInt(scanner.nextLine());
        }catch (Exception e){
            throw new IllegalArgumentException("Введите 1, 2 или 3!");
        }
        method = MethodFactory.getMethod(i,start, end, eps, function);
        System.out.println(method.solve());
    }

    static void solveSystem(Scanner scanner, Plot plot){
        double x0;
        double y0;
        double eps;
        boolean system;
        System.out.println("Выберите систему, которую необходимо решить: ");
        for(BinarySystemType ft: BinarySystemType.values()){
            System.out.println((ft.ordinal() + 1) + ")\n" + ft.SystemName);
        }
        try {
            int i = Integer.parseInt(scanner.nextLine());
            if(i == 1 || i == 2){
                system = i != 1;
            }else {
                throw new IllegalArgumentException();
            }
        }catch (Exception e){
            throw new IllegalArgumentException("Введите 1 или 2!");
        }
        if(!system){
            plot.systemFirst(-4, 4);
        }else{
            plot.systemSecond(-2, 0);
        }
        System.out.println("Введите значение погрешности вычислений: ");
        try {
            eps = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Значение погрешности должно быть числом!");
        }
        System.out.println("Введите начальное приближение к корню (значение x и y через пробел)");
        try {
            String[] line = scanner.nextLine().split(" ");
            x0 = Double.parseDouble(line[0]);
            y0 = Double.parseDouble(line[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Прочитан некорректный интервал!");
        }
        SystemIteration systemIteration = new SystemIteration(eps, x0, y0, system);
        System.out.println(systemIteration.solve());
    }
}
