import java.util.Scanner;

class DiskCovering
{
    class Result
    {
        int M;
        int[] x;
        int[] y;
    }

    public Result solve (
        final int N,
        final int R,
        final int[] x,
        final int[] y)
    {
        Result ret = new Result();
        ret.M = N;
        ret.x = x;
        ret.y = y;
        return ret;
    }
}

public class Main
{
    public static void main (String[] args) 
    {
        try 
        {
            Scanner sc = new Scanner(System.in);
            int N = sc.nextInt();
            int R = sc.nextInt();
            int[] x = new int[N];
            int[] y = new int[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
            }

            DiskCovering dc = new DiskCovering();
            DiskCovering.Result ret = dc.solve(N, R, x, y);
            System.out.println(ret.M);
            for (int i = 0; i < ret.M; i++) {
                System.out.println(ret.x[i] + " " + ret.y[i]);
            }
            System.out.flush();
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
