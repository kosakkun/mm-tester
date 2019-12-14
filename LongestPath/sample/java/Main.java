import java.util.Scanner;

class LongestPath
{
    public int[] solve (int N, int M, int[] x, int[] y, int[] a, int[] b)
    {
        int[] ret = {a[0], b[0]};
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
            int[] ret = lp.solve(N, M, x, y, a, b);
            System.out.println(ret.length);
            for (int i = 0; i < ret.length; i++) {
                System.out.println(ret[i]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
