import java.util.Scanner;

class Clustering
{
    class Result
    {
        int[] cx;
        int[] cy;
    }

    public Result solve (
        final int N,
        final int K,
        final int[] x,
        final int[] y)
    {
        Result ret = new Result();
        ret.cx = new int[K];
        ret.cy = new int[K];
        for (int i = 0; i < K; i++) {
            ret.cx[i] = (i / 4) * 200 + 100;
            ret.cy[i] = (i % 4) * 250 + 100;
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
            Clustering.Result ret = c.solve(N, K, x, y);
            for (int i = 0; i < ret.cx.length; ++i) {
                System.out.println(ret.cx[i] + " " + ret.cy[i]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
