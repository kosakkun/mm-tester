import java.util.Scanner;

class RectanglePacking
{
    public int[][] solve (int N, int[] w, int[] h)
    {
        int[][] ret = new int[N][2];
        for (int i = 0; i < N; i++) {
            ret[i][0] = 100 * (i % 10);
            ret[i][1] = 100 * (i / 10);
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
            int w[] = new int[N];
            int h[] = new int[N];
            for (int i = 0; i < N; i++) {
                w[i] = sc.nextInt();
                h[i] = sc.nextInt();
            }
            RectanglePacking rp = new RectanglePacking();
            int[][] ret = rp.solve(N, w, h);
            for (int i = 0; i < ret.length; ++i) {
                System.out.println(ret[i][0] + " " + ret[i][1]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
