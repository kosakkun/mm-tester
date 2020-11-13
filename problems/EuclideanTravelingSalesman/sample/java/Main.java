import java.util.Scanner;

class EuclideanTravelingSalesman
{
    public int[] solve (
        final int N,
        final int[] x,
        final int[] y)
    {
        int[] v = new int[N];
        for (int i = 0; i < N; i++) {
            v[i] = i;
        }
        return v;
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
            EuclideanTravelingSalesman ets = new EuclideanTravelingSalesman();
            int[] v = ets.solve(N, x, y);
            for (int i = 0; i < v.length; ++i) {
                System.out.println(v[i]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
