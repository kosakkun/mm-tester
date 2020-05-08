import java.util.Arrays;
import java.util.Scanner;

public class Checker
{
    static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        boolean[] used = new boolean[id.N];
        for (int i = 0; i < od.K; i++) {
            if (od.T[i] < 0 || od.T[i] >= id.M) {
                System.err.println(
                    "The track number must be between 0 and " + (id.M - 1) +
                    ", and your output contains " + od.T[i] + ".");
                return false;
            }
            if (od.L[i] <= 0 || od.L[i] > id.cap[od.T[i]]) {
                System.err.println(
                    "The load on truck " + od.T[i] + " must be between 1 and " +
                    id.cap[od.T[i]] + ", but in your output you are trying to load " +
                    od.L[i] + " pieces of luggage.");
                return false;
            }
            for (int j = 0; j < od.L[i]; j++) {
                int idx = od.D[i][j];
                if (idx < 0 || idx >= id.N) {
                    System.err.println(
                        "The delivery number must be between 0 and " + (id.N - 1) +
                        ", but your output contains " + idx + ".");
                    return false;
                }
                if (used[idx]) {
                    System.err.println(
                        "Your delivery destination must be visited once, but your " +
                        "output has visited " + idx + " more than once.");
                    return false;
                }
                used[idx] = true;
            }
        }   
        for (int i = 0; i < id.N; i++) {
            if (!used[i]) {
                System.err.println(
                    "The delivery destination must visit everything from 0 to " +
                    (id.N - 1) + ", and your output does not include " + i + ".");
                return false;
            }
        }

        return true;
    }

    static double calcDist (
        final int x1,
        final int y1,
        final int x2,
        final int y2)
    {
        double lx = (double)(x1 - x2);
        double ly = (double)(y1 - y2);
        return Math.sqrt(lx * lx + ly * ly);
    }

    static double[] getDist (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        double[] dist = new double[id.M];
        int[] last_idx = new int[id.M];
        Arrays.fill(last_idx, -1);

        for (int i = 0; i < od.K; i++) {
            for (int j = 0; j < od.L[i]; j++) {
                double dt = 0;
                if (j == 0 && last_idx[od.T[i]] < 0) {
                    dt += calcDist(id.depotX, id.depotY,
                                   id.x[od.D[i][j]], id.y[od.D[i][j]]);
                }
                else if (j == 0) {
                    dt += calcDist(id.x[last_idx[od.T[i]]], id.y[last_idx[od.T[i]]],
                                   id.depotX, id.depotY);
                    dt += calcDist(id.depotX, id.depotY,
                                   id.x[od.D[i][j]], id.y[od.D[i][j]]);
                }
                else {
                    dt += calcDist(id.x[last_idx[od.T[i]]], id.y[last_idx[od.T[i]]],
                                   id.x[od.D[i][j]], id.y[od.D[i][j]]);
                }
                dist[od.T[i]] += dt;
                last_idx[od.T[i]] = od.D[i][j];
            }
        }

        return dist;
    }

    static double calcScore (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        if (!isValid(id, od)) {
            return -1.0;
        }

        double[] dist = getDist(id, od);
        double max_time = -1.0;
        
        for (int i = 0; i < id.M; i++) {
            double time = dist[i] / (double)id.speed[i];
            max_time = Math.max(max_time, time);
        }

        return max_time;
    }

    static OutputData runCommand (
        final String exec,
        final InputData id)
        throws Exception
    {
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();
        proc.getOutputStream().write(id.toString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());

        OutputData od = new OutputData();
        od.K = sc.nextInt();
        od.T = new int[od.K];
        od.L = new int[od.K];
        od.D = new int[od.K][];
        for (int i = 0; i < od.K; i++) {
            od.T[i] = sc.nextInt();
            od.L[i] = sc.nextInt();
            od.D[i] = new int[od.L[i]];
            for (int j = 0; j < od.L[i]; j++) {
                od.D[i][j] = sc.nextInt();
            }
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}
