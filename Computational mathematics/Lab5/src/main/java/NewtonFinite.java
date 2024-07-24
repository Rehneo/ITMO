public class NewtonFinite extends Method{
    protected NewtonFinite(double point, double[] x, double[] y) {
        super(point, x, y);
    }

    @Override
    public Result solve() {
        System.out.println("Таблица конечных разностей: ");
        printTable();
        System.out.println();
        final double h = x[1] - x[0];
        if((x[0] <= point)&&(point <= (x[n-1] + x[0])/2)){
            System.out.println("Используется первая формула");
            double t = (point - x[0])/h;
            return solveLeft(t);
        }else{
            System.out.println("Используется вторая формула");
            double t = (point - x[n-1])/h;
            return solveRight(t);
        }
    }

    private double f(int degree, int i){
        if(degree == 0){
            return y[i];
        }
        return f(degree - 1, i + 1) - f(degree - 1,  i);
    }

    public void printTable(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n - i;j++){
                System.out.print(f(j, i) + " ");
            }
            System.out.println();
        }
    }

    public Result solveLeft(double t){
        double N = y[0];
        double p = 1;
        for(int i = 0; i < (n-1); i++){
            p *= (t - i)/((i+1));
            N += f(i + 1, 0)*p;
        }
        return new Result(point, N, MethodType.NEWTON_FINITE);
    }

    public Result solveRight(double t){
        double N = y[n-1];
        double p = 1;
        for(int i = 0; i < n - 1; i++){
            p *= (t + i)/((i+1));
            N += f(i + 1, (n - 2 - i))*p;
        }
        return new Result(point, N, MethodType.NEWTON_FINITE);
    }
}
