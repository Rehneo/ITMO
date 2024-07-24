import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line;
        int type = 0;
        double x0 = 0, y0 = 0, xn = 0, h = 0;
        double eps = 0;

        System.out.println("Выберите уравнение для решения: ");
        System.out.println("1) y' = y + (1 + x)*y^2");
        System.out.println("2) y' = x^2 + y");
        System.out.println("3) y' = sin(x)*y");
        try {
            line = scanner.nextLine().split(" ");
            type = Integer.parseInt(line[0]);
            if(type < 1 || type > 3){
                System.out.println("Введите 1, 2 или 3!");
                System.exit(-1);
            }
        }catch (NumberFormatException e) {
            System.out.println("Введите 1, 2 или 3!");
            System.exit(-1);
        }
        System.out.println("Введите начало и конец интервала (два числа через пробел): ");
        try {
            line = scanner.nextLine().split(" ");
            x0 = Double.parseDouble(line[0]);
            xn = Double.parseDouble(line[1]);
        }catch (NumberFormatException e){
            System.out.println("Введенные данные должны быть числом!");
            System.exit(-1);
        }
        System.out.println("Введите начальное условие для x0 = " + x0);
        try {
            line = scanner.nextLine().split(" ");
            y0 = Double.parseDouble(line[0]);
        }catch (NumberFormatException e){
            System.out.println("Введенное значение должно быть числом!");
            System.exit(-1);
        }

        System.out.println("Введите шаг h: ");
        try {
            line = scanner.nextLine().split(" ");
            h = Double.parseDouble(line[0]);
        }catch (NumberFormatException e){
            System.out.println("Введенное значение должно быть числом!");
            System.exit(-1);
        }

        System.out.println("Введите точность eps: ");
        try {
            line = scanner.nextLine().split(" ");
            eps = Double.parseDouble(line[0]);
        }catch (NumberFormatException e){
            System.out.println("Введенное значение должно быть числом!");
            System.exit(-1);
        }

        MathFunction function = FunctionFactory.get(type, x0, y0);
        Result adams = new Adams(x0, xn, y0, h, eps, function).solve();
        Result euler = new Euler(x0, xn, y0, h, eps, function, 2).solve();
        Result runge = new Runge(x0, xn, y0, h, eps, function, 4).solve();
        System.out.println("Усовершенствованный метод Эйлера: ");
        System.out.println(euler + "\n");
        System.out.println("Метод Рунге-Кутта 4-го порядка: ");
        System.out.println(runge + "\n");
        System.out.println("Метод Адамса: ");
        System.out.println(adams + "\n");
        Plot plot = new Plot();
        double[] y = new double[euler.getX().length];
        System.out.println("Точное решение: ");
        for(int i = 0; i < euler.getX().length;i++){
            y[i] = function.solAt(euler.getX()[i]);
            System.out.println(euler.getX()[i] + "\t" + y[i]);
            System.out.println();
        }
        System.out.println();
        plot.init(euler.getX(), y, euler.getY(), runge.getY(), adams.getY());
        plot.show();
    }
}
