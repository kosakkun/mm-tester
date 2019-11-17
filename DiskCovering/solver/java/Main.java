import java.util.Scanner;

class DiskCovering
{
    public int[][] solve (int N, int R, int[] x, int[] y)
    {
        int[][] ret = new int[N][2];
        for (int i = 0; i < N; i++) {
            ret[i][0] = x[i];
            ret[i][1] = y[i];
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
            int R = sc.nextInt();
            int[] x = new int[N];
            int[] y = new int[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
            }
            DiskCovering dc = new DiskCovering();
            int[][] ret = dc.solve(N, R, x, y);
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
