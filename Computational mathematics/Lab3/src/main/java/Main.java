import function.FunctionFactory;
import function.FunctionType;
import function.MathFunction;
import method.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        MathFunction function;
        Scanner scanner = new Scanner(System.in);
        double A;
        double B;
        double eps;
        Method method;
        System.out.println("Выберите функцию, которую необходимо проинтегрировать: ");
        for(FunctionType ft: FunctionType.values()){
            System.out.println((ft.ordinal() + 1) + ") " + ft.functionName);
        }
        try {
            function = FunctionFactory.getFunction(Integer.parseInt(scanner.nextLine()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Введите 1, 2 или 3!");
        }

        System.out.println("Введите начало и конец интервала через пробел: ");
        try {
            String[] line = scanner.nextLine().split(" ");
            A = Double.parseDouble(line[0]);
            B = Double.parseDouble(line[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Прочитан некорректный интервал!");
        }

        System.out.println("Введите значение погрешности вычислений: ");
        try {
            eps = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Значение погрешности должно быть числом!");
        }
        System.out.println("Выберите метод: ");
        System.out.println("1) Метод левых прямоугольников");
        System.out.println("2) Метод правых прямоугольников");
        System.out.println("3) Метод средних прямоугольников");
        System.out.println("4) Метод трапеций");
        System.out.println("5) Метод Симпсона");
        int i;
        try {
            i = Integer.parseInt(scanner.nextLine());
        }catch (Exception e){
            throw new IllegalArgumentException("Введите 1, 2 или 3!");
        }
        if((!function.check(A) || !function.check(B))){
            System.out.println("Введенный интервал не входит");
            System.exit(-1);
        }
        method = MethodFactory.getMethod(function, A, B, eps, i);
        System.out.println(method.solve());
    }
}
