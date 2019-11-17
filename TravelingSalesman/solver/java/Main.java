import java.util.Scanner;

class TravelingSalesman
{
    public int[] solve (int N, int[] x, int[] y)
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
            int x[] = new int[N];
            int y[] = new int[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
            }
            TravelingSalesman ts = new TravelingSalesman();
            int[] ret = ts.solve(N, x, y);
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
