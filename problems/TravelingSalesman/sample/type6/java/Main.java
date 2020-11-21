import java.util.Scanner;

class TravelingSalesman
{
    public int[] solve (
        final int N,
        final int[][] c)
    {
        int[] v = new int[N];
        for (int i = 0; i < N; i++) {
            v[i] = i;
        }
        return v;
    }
}

public class Main
{
    public static void main (String[] args)
    {
        try {
            Scanner sc = new Scanner(System.in);
            int N = sc.nextInt();
            int c[][] = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    c[i][j] = sc.nextInt();
                }
            }
            TravelingSalesman ts = new TravelingSalesman();
            int[] v = ts.solve(N, c);
            for (int i = 0; i < v.length; ++i) {
                System.out.println(v[i]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
