import java.util.Scanner;

class RectilinearSteinerTree
{
    public int[][] solve (int N, int[] x, int[] y)
    {
        final int size = 100;
        boolean[][] used = new boolean[size][size];
        for (int i = 0; i < N; i++) {
            used[x[i]][y[i]] = true;
        }
        int idx = 0;
        int[][] ret = new int[size * size - N][2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!used[i][j]) {
                    ret[idx][0] = i;
                    ret[idx][1] = j;
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
            int[][] ret = rst.solve(N, x, y);
            System.out.println(ret.length);
            for (int i = 0; i < ret.length; i++) {
                System.out.println(ret[i][0] + " " + ret[i][1]);
            }
            System.out.flush();
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
