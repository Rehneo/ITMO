package methods;

public class SystemSolution {
    double x;
    double y;
    int n;

    double x0;
    double y0;

    public SystemSolution(double x, double y, int n, double x0 , double y0){
        this.x = x;
        this.y = y;
        this.n = n;
        this.x0 = x0;
        this.y0 = y0;
    }

    @Override
    public String toString(){
        return "Найденный корень: " + "(" + x +", " + y + ")" + "\n" +
                "Количество итераций: " + n + "\n"+
                "Значение первой функции: " + x0 + "\n"+
                        "Значение второй функции: " + y0 + "\n";
    }
}
