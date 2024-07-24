import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, PythonExecutionException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Как вы хотите прочитать данные?");
        System.out.println("1) С консоли");
        System.out.println("2) Выбрать функцию");
        System.out.println("3) С файла");
        int choice = 1;
        try {
            String[] line = scanner.nextLine().split(" ");
            choice = Integer.parseInt(line[0]);
            if(choice < 1 || choice > 3){
                System.out.println("Введите 1, 2 или 3!");
                System.exit(-1);
            }
        }catch (NumberFormatException e){
            System.out.println("Введите 1, 2, или 3!");
            System.exit(-1);
        }
        ReadResult read;
        if(choice == 3){
            System.out.println("Введите путь к файлу: ");
            String path = scanner.nextLine();
            read = DataReader.read(path);
        }else{
            read = DataReader.read(choice);
        }
        System.out.println(new Lagrange(read.getPoint(), read.getX(), read.getY()).solve() + "\n");
        if(read.isFinite()){
            System.out.println(new NewtonFinite(read.getPoint(), read.getX(), read.getY()).solve());
        }else {
            System.out.println("Введенные данные не имеют конечные разности.");
            System.out.println(new NewtonDivided(read.getPoint(), read.getX(), read.getY()).solve() + "\n");
            System.out.println();
        }
        int n = read.getX().length;
        double[][] functionTable = new double[n][2];
        double[][] newtonPol = new double[n][2];
        for(int i = 0; i < n; i++){
            functionTable[i][0] = read.getX()[i];
            functionTable[i][1] = read.getY()[i];
        }
        double minY = Arrays.stream(functionTable).mapToDouble(x -> x[1]).min().getAsDouble();
        double maxY = Arrays.stream(functionTable).mapToDouble(x -> x[1]).max().getAsDouble();
        double maxX = Arrays.stream(functionTable).mapToDouble(x -> x[0]).max().getAsDouble();
        double minX = Arrays.stream(functionTable).mapToDouble(x -> x[0]).min().getAsDouble();
        double step = (maxX - minX) / n;
        int iter = 0;
        for (double i = minX; i <= maxX; i += step, iter++) {
            if (iter == n){
                break;
            }
            newtonPol[iter][0] = i;
            newtonPol[iter][1] = new NewtonDivided(i, read.getX(), read.getY()).solve().getY();
        }
        MyPlot plot = new MyPlot();
        plot.draw(minX - 1, maxX + 1, minY - 1, maxY + 1,functionTable, newtonPol);
    }
}
