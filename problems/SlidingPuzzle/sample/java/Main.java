import java.util.Scanner;

class SlidingPuzzle
{
    class Result
    {
        int M;
        int[] r;
        int[] c;
    }

    public Result solve (
        final int N,
        final int[][] B)
    {
        Result ret = new Result();
        ret.M = N * N;
        ret.r = new int[ret.M];
        ret.c = new int[ret.M];
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                ret.r[x * N + y] = x;
                ret.c[x * N + y] = y;
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
            int B[][] = new int[N][N];
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < N; y++) {
                    B[x][y] = sc.nextInt();
                }
            }

            SlidingPuzzle sp = new SlidingPuzzle();
            SlidingPuzzle.Result ret = sp.solve(N, B);
            System.out.println(ret.M);
            for (int i = 0; i < ret.M; i++) {
                System.out.println(ret.r[i] + " " + ret.c[i]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
