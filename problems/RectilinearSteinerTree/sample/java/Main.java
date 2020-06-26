import java.util.Scanner;

class RectilinearSteinerTree
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
        final int size = 100;
        boolean[][] used = new boolean[size][size];
        for (int i = 0; i < N; i++) {
            used[x[i]][y[i]] = true;
        }

        Result ret = new Result();
        ret.M = size * size - N;
        ret.ax = new int[ret.M];
        ret.ay = new int[ret.M];
        int idx = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!used[i][j]) {
                    ret.ax[idx] = i;
                    ret.ay[idx] = j;
                    idx++;
                }
            }
        }
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
            int[] x = new int[N];
            int[] y = new int[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
            }
            RectilinearSteinerTree rst = new RectilinearSteinerTree();
            RectilinearSteinerTree.Result ret = rst.solve(N, x, y);
            System.out.println(ret.M);
            for (int i = 0; i < ret.M; i++) {
                System.out.println(ret.ax[i] + " " + ret.ay[i]);
            }
            System.out.flush();
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
