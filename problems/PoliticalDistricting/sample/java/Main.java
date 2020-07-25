import java.util.Scanner;

class PoliticalDistricting
{
    public int[][] solve (
        final int N,
        final int K,
        final int[][] B)
    {
        int[][] R = new int[N][N];
        int size = (N * N + K) / K;
  
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int num = r * N + c;
                R[r][(r % 2 == 1) ? c : N - 1 - c] = num / size;
            }
        }
   
        return R;
    }
}

public class Main
{
    public static void main (String[] args)
    {
        try {
            Scanner sc = new Scanner(System.in);
            int N = sc.nextInt();
            int K = sc.nextInt();
            int B[][] = new int[N][N];
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    B[r][c] = sc.nextInt();
                }
            }
            PoliticalDistricting pd = new PoliticalDistricting();
            int[][] R = pd.solve(N, K, B);
            for (int r = 0; r < N; r++) {
                String line = "";
                for (int c = 0; c < N; c++) {
                    line = line + R[r][c];
                    line = (c < N - 1) ? line + " " : line;
                }
                System.out.println(line);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
