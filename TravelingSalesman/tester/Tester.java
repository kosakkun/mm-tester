import java.util.Scanner;
import java.security.SecureRandom;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tester 
{
    @JsonIgnore public final int MAXN   = 1000;
    @JsonIgnore public final int MINN   = 50;
    @JsonIgnore public final int WIDTH  = 1000 + 1;
    @JsonIgnore public final int HEIGHT = 1000 + 1;

    public final long seed;
    @JsonIgnore public final int N;
    @JsonIgnore public final int[] x;
    @JsonIgnore public final int[] y;
    @JsonIgnore public final int[] v;
    @JsonIgnore private double score_t = -2.0;

    @JsonIgnore
    public String getInputString () {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }
        return sb.toString();
    }

    @JsonIgnore
    public String getOutputString () {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < v.length; ++i) {
            sb.append(v[i]).append('\n');
        }
        return sb.toString();
    }

    public double getScore () {

        if (score_t >= -1.0) {
            return score_t;
        }

        /* Check whether the output satisfies the constraints. */
        boolean[] used = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (v[i] < 0 || v[i] >= N) {
                System.err.println("All elements of your return must be between 0 and " +
                                   (N - 1) + ", and your return contained " + v[i] + ".");
                return score_t = -1.0;
            }
            if (used[v[i]]) {
                System.err.println("All elements of your return must be unique, " +
                                   "and your return contained " + v[i] + " twice.");
                return score_t = -1.0;
            }
            used[v[i]] = true;
        }

        /* Calculate the score. */
        double dist = 0.0;
        for (int i = 0; i < N; i++) {
            double dx = (double)(x[v[i]] - x[v[(i + 1) % N]]);
            double dy = (double)(y[v[i]] - y[v[(i + 1) % N]]);
            dist += Math.sqrt(dx * dx + dy * dy);
        }
        return score_t = dist;
    }

    public Tester (final long _seed, final String exec) throws Exception {

        this.seed = _seed;
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();

        /* Generate input. */
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(_seed);
        N = rnd.nextInt(MAXN - MINN + 1) + MINN;
        x = new int[N];
        y = new int[N];
        boolean [][] usedPos = new boolean[WIDTH][HEIGHT];
        for (int i = 0; i < N; i++) {
            while (true) {
                int xt = rnd.nextInt(WIDTH);
                int yt = rnd.nextInt(HEIGHT);
                if (usedPos[xt][yt]) continue;
                usedPos[xt][yt] = true;
                x[i] = xt;
                y[i] = yt;
                break;
            }
        }

        /* Get the output. */
        proc.getOutputStream().write(getInputString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());
        v = new int[N];
        for (int i = 0; i < N; i++) {
            v[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        getScore();
    }
}
