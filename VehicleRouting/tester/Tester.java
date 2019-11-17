import java.util.Arrays;
import java.util.Scanner;
import java.security.SecureRandom;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tester 
{
    @JsonIgnore public final int MAX_N = 500, MIN_N = 50;
    @JsonIgnore public final int MAX_M = 10,  MIN_M = 3;
    @JsonIgnore public final int MAX_CAP = 20, MIN_CAP = 5;
    @JsonIgnore public final int MAX_SPEED = 20, MIN_SPEED = 1;
    @JsonIgnore public final int WIDTH  = 1000 + 1;
    @JsonIgnore public final int HEIGHT = 1000 + 1;
    @JsonIgnore public final int NEAREST = 50;

    public final long seed;
    @JsonIgnore public final int N;
    @JsonIgnore public final int M;
    @JsonIgnore public final int depotX;
    @JsonIgnore public final int depotY;
    @JsonIgnore public final int[] x;
    @JsonIgnore public final int[] y;
    @JsonIgnore public final int[] cap;
    @JsonIgnore public final int[] speed;
    @JsonIgnore public final int K;
    @JsonIgnore public final int[] T;
    @JsonIgnore public final int[] L;
    @JsonIgnore public final int[][] D;
    @JsonIgnore public double[] dist;
    @JsonIgnore public double[] time;
    @JsonIgnore public int[] last_idx;
    @JsonIgnore private double score_t = -2.0;

    @JsonIgnore
    public String getInputString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        sb.append(depotX).append(' ');
        sb.append(depotY).append('\n');
        for (int i = 0; i < N; i++) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n'); 
        }
        for (int i = 0; i < M; i++) {
            sb.append(cap[i]).append(' ');
            sb.append(speed[i]).append('\n'); 
        }
        return sb.toString();
    }

    @JsonIgnore
    public String getOutputString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(K).append('\n');
        for (int i = 0; i < K; i++) {
            sb.append(T[i]).append(' ');
            sb.append(L[i]).append('\n');
            for (int j = 0; j < L[i]; j++) {
                sb.append(D[i][j]);
                sb.append((j < L[i] - 1) ? ' ' : '\n');
            }
        }
        return sb.toString();
    }

    private double calcDist (int x1, int y1, int x2, int y2)
    {
        double lx = (double)(x1 - x2);
        double ly = (double)(y1 - y2);
        return Math.sqrt(lx * lx + ly * ly);
    }

    public double getScore ()
    {
        if (score_t >= -1.0) {
            return score_t;
        }

        dist = new double[M];
        time = new double[M];
        last_idx = new int[M];
        Arrays.fill(last_idx, -1);

        /* Check whether the output satisfies the constraints. */
        boolean[] used = new boolean[N];
        for (int i = 0; i < K; i++) {
            if (T[i] < 0 || T[i] >= M) {
                System.err.println("The track number must be between 0 and " + (M - 1)
                                   + ", and your output contains " + T[i] + ".");
                return score_t = -1.0;
            }
            if (L[i] <= 0 || L[i] > cap[T[i]]) {
                System.err.println("The load on truck " + T[i] + " must be between 1 and " + cap[T[i]]
                                   + ", but in your output you are trying to load " + L[i] + " pieces of luggage.");
                return score_t = -1.0;
            }
            for (int j = 0; j < L[i]; j++) {
                int idx = D[i][j];
                if (idx < 0 || idx >= N) {
                    System.err.println("The delivery number must be between 0 and " + (N - 1) +
                                       ", but your output contains " + idx + ".");
                    return score_t = -1.0;
                }
                if (used[idx]) {
                    System.err.println("Your delivery destination must be visited once, but your output "
                                       + "has visited " + idx + " more than once.");
                    return score_t = -1.0;
                }
                used[idx] = true;
            }
        }   
        for (int i = 0; i < N; i++) {
            if (!used[i]) {
                System.err.println("The delivery destination must visit everything from 0 to "
                                   + (N - 1) + ", and your output does not include " + i + ".");
                return score_t = -1.0;
            }
        }

        /* Calculate the score. */
        for (int i = 0; i < K; i++) {
            for (int j = 0; j < L[i]; j++) {
                if (j == 0 && last_idx[T[i]] < 0) {
                    dist[T[i]] += calcDist(depotX, depotY, x[D[i][j]], y[D[i][j]]);
                }  else if (j == 0) {
                    dist[T[i]] += calcDist(x[last_idx[T[i]]], y[last_idx[T[i]]], depotX, depotY);
                    dist[T[i]] += calcDist(depotX, depotY, x[D[i][j]], y[D[i][j]]);
                } else {
                    dist[T[i]] += calcDist(x[last_idx[T[i]]], y[last_idx[T[i]]],  x[D[i][j]], y[D[i][j]]);
                }
                last_idx[T[i]] = D[i][j];
            }
        }
        double max_time = -1.0;
        for (int i = 0; i < M; i++) {
            time[i] = dist[i] / (double)speed[i];
            max_time = Math.max(max_time, time[i]);
        }
        return score_t = max_time;
    }

    public Tester (final long _seed, final String exec) throws Exception
    {
        this.seed = _seed;
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();

        /* Generate input. */
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);
        N = rnd.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        M = rnd.nextInt(MAX_M - MIN_M + 1) + MIN_M;
        depotX = rnd.nextInt(WIDTH);
        depotY = rnd.nextInt(HEIGHT);
        x = new int[N];
        y = new int[N];
        boolean[][] usedPos = new boolean[WIDTH][HEIGHT];
        usedPos[depotX][depotY] = true;
        for (int i = 0; i < N; i++) {
            while (true) {
                int xt = rnd.nextInt(WIDTH);
                int yt = rnd.nextInt(HEIGHT);
                if (usedPos[xt][yt]) continue;
                int lx = xt - depotX;
                int ly = yt - depotY;
                if (lx * lx + ly * ly >= NEAREST * NEAREST) {
                    usedPos[xt][yt] = true;
                    x[i] = xt;
                    y[i] = yt;
                    break;
                }
            }
        }
        cap   = new int[M];
        speed = new int[M];
        for (int i = 0; i < M; i++) {
            cap[i]   = rnd.nextInt(MAX_CAP - MIN_CAP + 1) + MIN_CAP;
            speed[i] = rnd.nextInt(MAX_SPEED - MIN_SPEED + 1) + MIN_SPEED;
        }

        /* Get the output. */
        proc.getOutputStream().write(getInputString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());
        K = sc.nextInt();
        T = new int[K];
        L = new int[K];
        D = new int[K][];
        for (int i = 0; i < K; i++) {
            T[i] = sc.nextInt();
            L[i] = sc.nextInt();
            D[i] = new int[L[i]];
            for (int j = 0; j < L[i]; j++) {
                D[i][j] = sc.nextInt();
            }
        }

        if (proc != null) {
            proc.destroy();
        }
    }
}
