import java.util.Scanner;
import java.security.SecureRandom;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tester 
{
    @JsonIgnore public final int MAXN   = 1000;
    @JsonIgnore public final int MINN   = 100;
    @JsonIgnore public final int MAXK   = 20;
    @JsonIgnore public final int MINK   = 5;
    @JsonIgnore public final int WIDTH  = 1000 + 1;
    @JsonIgnore public final int HEIGHT = 1000 + 1;

    public final long seed;
    @JsonIgnore public final int N;
    @JsonIgnore public final int K;
    @JsonIgnore public final int[] x;
    @JsonIgnore public final int[] y;
    @JsonIgnore public final int[] cx;
    @JsonIgnore public final int[] cy;
    @JsonIgnore public int[] belongV;
    @JsonIgnore private double score_t = -2.0;

    @JsonIgnore
    public String getInputString () {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(K).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }
        return sb.toString();
    }

    @JsonIgnore
    public String getOutputString () {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cx.length; ++i) {
            sb.append(cx[i]).append(' ');
            sb.append(cy[i]).append('\n');
        }
        return sb.toString();
    }
   
    public double getScore () {

        if (score_t >= -1.0) {
            return score_t;
        }

        /* Check whether the output satisfies the constraints. */
        boolean [][] used = new boolean[WIDTH][HEIGHT];
        for (int i = 0; i < K; i++) {
            if (cx[i] < 0 || cy[i] < 0 || cx[i] >= WIDTH || cy[i] >= HEIGHT) {
                System.err.println("The coordinate x = " + cx[i] + ", y = " + cy[i] + " is out of range.");
                return score_t = -1.0;
            }
            if (used[cx[i]][cy[i]]) {
                System.err.println("The coordinate x = " + cx[i] + ", y = " + cy[i] + " is duplicated.");
                return score_t = -1.0;
            }
            used[cx[i]][cy[i]] = true;
        }

         /* Calculate the score. */
        double sum = 0.0;
        belongV = new int[N];
        for (int i = 0; i < N; i++) {
            int idx = -1;
            double dist = 1.0e9;
            for (int j = 0; j < K; j++) {
                int lx = Math.abs(x[i] - cx[j]);
                int ly = Math.abs(y[i] - cy[j]);
                double dt = Math.sqrt((double)(lx * lx + ly * ly));
                if (dist > dt) {
                    dist = dt;
                    idx = j;
                }
            }
            sum += dist;
            belongV[i] = idx;
        }
        return score_t = sum;
    }

    public Tester (final long _seed, final String exec) throws Exception {

        this.seed = _seed;
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();

        /* Generate input. */
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);
        N = rnd.nextInt(MAXN - MINN + 1) + MINN;
        K = rnd.nextInt(MAXK - MINK + 1) + MINK;
        x = new int[N];
        y = new int[N];
        boolean used[][] = new boolean[WIDTH][HEIGHT];
        int esum = 0;
        while (esum < N) {
            int xt = rnd.nextInt(WIDTH);
            int yt = rnd.nextInt(HEIGHT);
            if (used[xt][yt]) continue;
            used[xt][yt] = true;
            x[esum] = xt;
            y[esum] = yt;
            esum++;
        }

        /* Get the output. */
        proc.getOutputStream().write(getInputString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());
        cx = new int[K];
        cy = new int[K];
        for (int i = 0; i < K; i++) {
            cx[i] = sc.nextInt();
            cy[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        getScore();
    }
}
