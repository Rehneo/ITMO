package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main{
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Вы хотите ввести данные с файла или с клавиатуры?");
        System.out.println("1) С файла");
        System.out.println("2) С клавиатуры");
        Scanner scanner = new Scanner(System.in);
        String isFile = scanner.nextLine();
        float[][] A;
        float[] B;
        int n;
        if(isFile.equals("2")){
            System.out.println("Введите размерность матрицы: ");
            n = scanner.nextInt();
            scanner.nextLine();
            String[] line;
            A = new float[n][n];
            B = new float[n];
            for(int i = 0; i < n; i++){
                System.out.println("Введите коэффициенты " + (i+1) +"-го уравнения: ");
                line = scanner.nextLine().split(" ");
                for(int j = 0; j < n; j++){
                    A[i][j] = Float.parseFloat(line[j]);
                }
                System.out.println("Введите значение правой части " + (i+1) +"-го уравнения: ");
                B[i] = Float.parseFloat(scanner.nextLine());
            }
            Solver solver = new Solver(n, A, B);
            solver.solve();
        } else if (isFile.equals("1")) {
            System.out.println("Введите путь к файлу: ");
            String path = scanner.nextLine();
            File file = new File(path);
            scanner = new Scanner(file);
            String[] line;
            System.out.println("Прочитанная матрица коэффициентов и правые части уравнений: ");
            line = scanner.nextLine().split(" ");
            n = line.length - 1;
            A = new float[n][n];
            B = new float[n];
            for(int i = 0; i < n; i++){
                A[0][i] = Float.parseFloat(line[i]);
                System.out.print(A[0][i] + "\t");
            }
            B[0] = Float.parseFloat(line[n]);
            System.out.print("|"+B[0]+"\n");
            int j = 1;
            while(scanner.hasNext()){
                line = scanner.nextLine().split(" ");
                for(int i = 0; i < n; i++){
                    A[j][i] = Float.parseFloat(line[i]);
                    System.out.print(A[j][i]+"\t");
                }
                B[j] = Float.parseFloat(line[n]);
                System.out.print("|"+B[j]+"\n");
                j++;
            }
            Solver solver = new Solver(n, A, B);
            solver.solve();
        }
    }
}