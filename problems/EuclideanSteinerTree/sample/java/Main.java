import java.util.Scanner;

class EuclideanSteinerTree
{
    class Result
    {
        int M;
        int[] ax;
        int[] ay;
    }

    public Result solve (
        final int N,
        final int[] x,
        final int[] y)
    {
        Result ret = new Result();
        ret.M = 1;
        ret.ax = new int[]{500};
        ret.ay = new int[]{500};
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
            EuclideanSteinerTree est = new EuclideanSteinerTree();
            EuclideanSteinerTree.Result ret = est.solve(N, x, y);
            System.out.println(ret.M);
            for (int i = 0; i < ret.M; i++) {
                System.out.println(ret.ax[i] + " " + ret.ay[i]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
