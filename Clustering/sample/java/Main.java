import java.util.Scanner;

class Clustering
{
    public int[][] solve (int N, int K, int[] x, int[] y)
    {
        int[][] ret = new int[K][2];
        for (int i = 0; i < K; i++) {
            ret[i][0] = (i / 4) * 200 + 100;
            ret[i][1] = (i % 4) * 250 + 100;
        }
        return ret;
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
            int x[] = new int[N];
            int y[] = new int[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
            }
            Clustering c = new Clustering();
            int[][] ret = c.solve(N, K, x, y);
            for (int i = 0; i < ret.length; ++i) {
                System.out.println(ret[i][0] + " " + ret[i][1]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
