import java.util.Scanner;

class VehicleRouting
{
    public int[][] solve (int N, int M, int depotX, int depotY,
                          int[] x, int[] y, int[] cap, int[] speed)
    {
        int T = 0;
        int K = N / cap[T] + (N % cap[T] > 0 ? 1 : 0);
        int[][] ret = new int[K * 2][];
        int idx = 0;
        while (N > 0) {
            int L = Math.min(N, cap[T]);
            int[] truck = {T, L};
            int[] D = new int[L];
            for (int i = 0; i < L; i++) {
                D[i] = --N;
            }
            ret[idx++] = truck;
            ret[idx++] = D;
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
            int[][] ret = vr.solve(N, M, depotX, depotY, x, y, cap, speed);
            System.out.println(ret.length / 2);
            for (int i = 0; i < ret.length; i++) {
                for (int j = 0; j < ret[i].length; j++) {
                    System.out.print(ret[i][j]);
                    System.out.print((j < ret[i].length - 1 ? " " : "\n"));
                }
            }
            System.out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
