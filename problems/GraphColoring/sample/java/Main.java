import java.util.Scanner;

class GraphColoring
{
    class Result
    {
        int[] c;
    }

    public Result solve (
        final int N,
        final int M,
        final int[] a,
        final int[] b)
    {
        Result ret = new Result();
        ret.c = new int[N];
        for (int i = 0; i < N; i++) {
            ret.c[i] = i;
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
            int M = sc.nextInt();
            int a[] = new int[M];
            int b[] = new int[M];
            for (int i = 0; i < M; i++) {
                a[i] = sc.nextInt();
                b[i] = sc.nextInt();
            }

            GraphColoring gc = new GraphColoring();
            GraphColoring.Result ret = gc.solve(N, M, a, b);
            for (int i = 0; i < ret.c.length; ++i) {
                System.out.println(ret.c[i]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
