import java.util.Scanner;

class GraphColoring
{
    public int[] solve (int N, int M, int[] a, int[] b)
    {
        int[] ret = new int[N];
        for (int i = 0; i < N; i++) {
            ret[i] = i;
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
            int[] ret = gc.solve(N, M, a, b);
            for (int i = 0; i < ret.length; ++i) {
                System.out.println(ret[i]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
