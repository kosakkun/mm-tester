import java.util.Scanner;

class Hiroimono
{
    class Result
    {
        int M;
        int[] v;
    }

    public Result solve (
        final int N,
        final int[] x,
        final int[] y)
    {
        Result ret = new Result();
        ret.M = 1;
        ret.v = new int[]{0};

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
            int x[] = new int[N];
            int y[] = new int[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
            }
            Hiroimono h = new Hiroimono();
            Hiroimono.Result ret = h.solve(N, x, y);

            System.out.println(ret.M);
            for (int i = 0; i < ret.v.length; ++i) {
                System.out.println(ret.v[i]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
