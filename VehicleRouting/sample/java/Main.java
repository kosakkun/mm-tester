import java.util.Scanner;

class VehicleRouting
{
    class Result
    {
        int K;
        int[] T;
        int[] L;
        int[][] D;
    }

    public Result solve (
        final int N,
        final int M,
        final int depotX,
        final int depotY,
        final int[] x,
        final int[] y,
        final int[] cap,
        final int[] speed)
    {
        Result ret = new Result();
        ret.K = (N + cap[0] - 1) / cap[0];
        ret.T = new int[ret.K];
        ret.L = new int[ret.K];
        ret.D = new int[ret.K][];

        int rm = N;
        for (int i = 0; i < ret.K; i++) {
            ret.T[i] = 0;
            ret.L[i] = Math.min(rm, cap[0]);
            ret.D[i] = new int[ret.L[i]];
            for (int j = 0; j < ret.L[i]; j++) {
                ret.D[i][j] = --rm;
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
            int M = sc.nextInt();
            int depotX = sc.nextInt();
            int depotY = sc.nextInt();
            int[] x = new int[N];
            int[] y = new int[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
            }
            int[] cap = new int[M];
            int[] speed = new int[M];
            for (int i = 0; i < M; i++) {
                cap[i] = sc.nextInt();
                speed[i] = sc.nextInt();
            }

            VehicleRouting vr = new VehicleRouting();
            VehicleRouting.Result ret = vr.solve(N, M, depotX, depotY, x, y, cap, speed);
            System.out.println(ret.K);
            for (int i = 0; i < ret.K; i++) {
                System.out.println(ret.T[i] + " " + ret.L[i]);
                for (int j = 0; j < ret.L[i]; j++) {
                    System.out.print(ret.D[i][j]);
                    System.out.print((j < ret.L[i] - 1 ? " " : "\n"));
                }
            }
            System.out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
