import java.util.Scanner;

class LongestPath
{
    class Result
    {
        int K;
        int[] v;
    }

    public Result solve (
        final int N,
        final int M,
        final int[] x,
        final int[] y,
        final int[] a,
        final int[] b)
    {
        Result ret = new Result();
        ret.K = 2;
        ret.v = new int[]{a[0], b[0]};
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
            int M = sc.nextInt();
            int[] x = new int[N];
            int[] y = new int[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
            }
            int[] a = new int[M];
            int[] b = new int[M];
            for (int i = 0; i < M; i++) {
                a[i] = sc.nextInt();
                b[i] = sc.nextInt();
            }

            LongestPath lp = new LongestPath();
            LongestPath.Result ret = lp.solve(N, M, x, y, a, b);
            System.out.println(ret.K);
            for (int i = 0; i < ret.K; i++) {
                System.out.println(ret.v[i]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
