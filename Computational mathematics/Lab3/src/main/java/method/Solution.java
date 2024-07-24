package method;

public class Solution {
    private final double ANS;
    private final int N;

    public Solution(double ANS, int N){
        this.ANS = ANS;
        this.N = N;
    }

    @Override
    public String toString(){
        return "Найденное значение интеграла: " + ANS + "\n" +
                "Число разбиений интервала : " + N;
    }

}
