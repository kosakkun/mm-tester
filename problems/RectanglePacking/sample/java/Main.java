import java.util.Scanner;

class RectanglePacking
{
    class Result
    {
        int[] x;
        int[] y;
    }

    public Result solve (
        final int N,
        final int[] w,
        final int[] h)
    {
        Result ret = new Result();
        ret.x = new int[N];
        ret.y = new int[N];
        for (int i = 0; i < N; i++) {
            ret.x[i] = 50 * (i % 20);
            ret.y[i] = 50 * (i / 20);
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
            int w[] = new int[N];
            int h[] = new int[N];
            for (int i = 0; i < N; i++) {
                w[i] = sc.nextInt();
                h[i] = sc.nextInt();
            }

            RectanglePacking rp = new RectanglePacking();
            RectanglePacking.Result ret = rp.solve(N, w, h);
            for (int i = 0; i < ret.x.length; ++i) {
                System.out.println(ret.x[i] + " " + ret.y[i]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
