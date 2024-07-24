package main;


public class Solver {
    float[][] A;

    float[][] enteredA;
    float [] enteredB;
    float[] B;

    float[] X;

    float[] R;
    final int N;
    float D;

    public Solver(int N, float[][] A, float[] B){
        this.N = N;
        this.A = A;
        this.B = B;
        this.enteredA = new float[N][N];
        this.enteredB = new float[N];
        for(int i = 0; i < N;i++){
            System.arraycopy(A[i], 0, enteredA[i], 0, N);
        }
        System.arraycopy(B, 0, enteredB, 0, N);
        X = new float[N];
        R = new float[N];
    }
    public void solve(){
        int p = forwardMove();
        calculateDeterminant(p);
        System.out.println("Определитель матрицы коэффициентов: " + D);
        reversalMove();
        calculateResidualVector();
        System.out.println();
        System.out.println();
        System.out.println("Треугольная матрица и преобразованный столбец B: ");
        printMatrix();
        System.out.println();
        printAnswer();
        System.out.println();
        printResidualVector();
    }


    private int chooseMainElement(int k){
        float mainE = A[k][k];
        int mainRow = k;
        for(int i = k+1; i < N; i++){
            if(Math.abs(A[i][k]) > Math.abs(mainE)){
                mainE = A[i][k];
                mainRow = i;
            }
        }
        if(mainRow != k){
            for(int i = k; i < N; i ++){
                float a = A[k][i];
                A[k][i] = A[mainRow][i];
                A[mainRow][i] = a;
            }
            float a = B[k];
            B[k] = B[mainRow];
            B[mainRow] = a;
            return 1;
        }
        return 0;
    }


    private int forwardMove(){
        int permutations = 0;
        for(int i = 0; i < N - 1; i ++){
            permutations += chooseMainElement(i);
            for(int j = i+1; j < N; j++){
                float c = A[j][i] / A[i][i];
                for(int k = i; k < N; k++){
                    A[j][k] = A[j][k] - c * A[i][k];
                }
                B[j] = B[j] - c * B[i];
            }
        }
        return permutations;
    }

    private void calculateDeterminant(int permutations){
        D = 1;
        for(int i = 0; i < N; i++){
            D *= A[i][i];
        }
        D *= Math.pow(-1, permutations);
    }

    private void reversalMove(){
        X[N-1] = B[N-1]/A[N-1][N-1];
        for(int i = N-2; i >= 0;i--){
            float s = 0;
            for(int j = i + 1; j < N; j++){
                s += A[i][j]*X[j];
            }
            X[i] = (B[i] - s)/A[i][i];
        }
    }

    private void calculateResidualVector(){
        for (int i = 0; i < N; i++) {
            float s = 0;
            for (int j = 0; j < N; j++) {
                s += enteredA[i][j] * X[j];
            }
            R[i] = s - enteredB[i];
        }
    }

    private void printResidualVector(){
        System.out.println("Вектор невязок: ");
        for (int i = 0; i < N; i++) {
            System.out.print(R[i] + "\t");
        }
    }
    private void printAnswer(){
        for(int i = 0; i < N;i++){
            System.out.print("x" + (i+1) + " = " + X[i]);
            System.out.println();
        }
    }

    public void printMatrix(){
        for(int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                System.out.print(A[i][j] + "\t");
            }
            System.out.print("|" + B[i] + "\n");
        }
    }

}
