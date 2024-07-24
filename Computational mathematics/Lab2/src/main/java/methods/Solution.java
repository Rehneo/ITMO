package methods;

public class Solution {
    double answer;
    int n;
    double f;



    public Solution(double answer, int n, double f){
        this.answer = answer;
        this.n = n;
        this.f = f;
    }

    @Override
    public String toString(){
        return "Найденный корень: " + answer + "\n" +
                "Количество итераций: " + n + "\n" +
                "Значение функции: " + f;
    }
}
