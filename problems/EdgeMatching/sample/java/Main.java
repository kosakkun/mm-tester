import java.util.Scanner;

class EdgeMatching
{
    class Result
    {
        int[] x;
        int[] y;
        int[] r;
    }

    public Result solve (
        final int N,
        final int C,
        final int[] U,
        final int[] D,
        final int[] L,
        final int[] R)
    {
        final int PN = N * N;
        Result ret = new Result();
        ret.x = new int[PN];
        ret.y = new int[PN];
        ret.r = new int[PN];

        for (int xt = 0; xt < N; xt++) {
            for (int yt = 0; yt < N; yt++) {
                ret.x[xt * N + yt] = xt;
                ret.y[xt * N + yt] = yt;
                ret.r[xt * N + yt] = 0;
            }
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
            int C = sc.nextInt();
            final int PN = N * N;
            int U[] = new int[PN];
            int D[] = new int[PN];
            int L[] = new int[PN];
            int R[] = new int[PN];
            for (int i = 0; i < PN; i++) {
                U[i] = sc.nextInt();
                D[i] = sc.nextInt();
                L[i] = sc.nextInt();
                R[i] = sc.nextInt();
            }

            EdgeMatching em = new EdgeMatching();
            EdgeMatching.Result ret = em.solve(N, C, U, D, L, R);
            for (int i = 0; i < PN; ++i) {
                System.out.println(ret.x[i] + " " + ret.y[i] + " " + ret.r[i]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
