import java.util.Scanner;

class EuclideanSteinerTree
{
    public int[][] solve (int N, int[] x, int[] y)
    {
        int[][] ret = {{500, 500}};
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
            int[][] ret = est.solve(N, x, y);
            System.out.println(ret.length);
            for (int i = 0; i < ret.length; i++) {
                System.out.println(ret[i][0] + " " + ret[i][1]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
