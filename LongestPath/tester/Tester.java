import java.util.Scanner;
import java.util.ArrayList;
import java.security.SecureRandom;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tester 
{
    @JsonIgnore public final int MAXN   = 1000;
    @JsonIgnore public final int MINN   = 50;
    @JsonIgnore public final int WIDTH  = 1000 + 1;
    @JsonIgnore public final int HEIGHT = 1000 + 1;
    @JsonIgnore public final int MIN_DIST = 20;

    public final long seed;
    @JsonIgnore public final int N,M;
    @JsonIgnore public final int[] x,y;
    @JsonIgnore public final int[] a,b;
    @JsonIgnore public final boolean[][] connect;
    @JsonIgnore public final int K;
    @JsonIgnore public final int[] v;
    @JsonIgnore private double score_t = -2.0;

    @JsonIgnore
    public String getInputString () {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }
        for (int i = 0; i < M; i++) {
            sb.append(a[i]).append(' ');
            sb.append(b[i]).append('\n');
        }
        return sb.toString();
    }

    @JsonIgnore
    public String getOutputString () {
        StringBuffer sb = new StringBuffer();
        sb.append(K).append('\n');
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
        for (int i = 0; i < K; i++) {
            if (v[i] < 0 || v[i] >= N) {
                System.err.println("All elements of your return must be between 0 and " +
                                   (N - 1) + ", and your return contained " + v[i] + ".");
                return score_t = -1.0;
            }
            if (i > 0 && !connect[v[i - 1]][v[i]]) {
                System.err.println("There is no edge connecting vertex " + v[i - 1] + 
                                   " and vertex " + v[i] + ".");
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
        for (int i = 1; i < K; i++) {
            double dx = (double)(x[v[i - 1]] - x[v[i]]);
            double dy = (double)(y[v[i - 1]] - y[v[i]]);
            dist += Math.sqrt(dx * dx + dy * dy);
        }
        return score_t = dist;
    }

    private boolean intersect (
        long l1_ax, long l1_ay, long l1_bx, long l1_by,
        long l2_ax, long l2_ay, long l2_bx, long l2_by)
    {
        long tc1 = (l1_ax - l1_bx) * (l2_ay - l1_ay) + (l1_ay - l1_by) * (l1_ax - l2_ax);
        long tc2 = (l1_ax - l1_bx) * (l2_by - l1_ay) + (l1_ay - l1_by) * (l1_ax - l2_bx);
        long td1 = (l2_ax - l2_bx) * (l1_ay - l2_ay) + (l2_ay - l2_by) * (l2_ax - l1_ax);
        long td2 = (l2_ax - l2_bx) * (l1_by - l2_ay) + (l2_ay - l2_by) * (l2_ax - l1_bx);
        return tc1 * tc2 < 0 && td1 * td2 < 0;
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
        for (int i = 0; i < N; i++) {
            while (true) {
                int xt = rnd.nextInt(WIDTH);
                int yt = rnd.nextInt(HEIGHT);
                boolean ok = true;
                for (int j = 0; j < i; j++) {
                    int lx = x[j] - xt;
                    int ly = y[j] - yt;
                    if (lx * lx + ly * ly <= MIN_DIST * MIN_DIST) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    x[i] = xt;
                    y[i] = yt;
                    break;
                }
            }
        }

        class Tuple {
            public Integer key,a,b;
            public Tuple (int _k, int _a, int _b) {
                key = _k;
                a = _a;
                b = _b;
            }
        }

        ArrayList<Tuple> order = new ArrayList<Tuple>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) continue;
                int key = rnd.nextInt(Integer.MAX_VALUE);
                order.add(new Tuple(key, i, j));
            }
        }
        order.sort((a, b) -> a.key.compareTo(b.key));

        connect = new boolean[N][N];
        ArrayList<Tuple> edges = new ArrayList<Tuple>();
        for (int i = 0; i < 2; i++) {
            final int MAX_DIST = (int)((i + 1) * 1000.0 / Math.sqrt(N));
            for (int key = 0; key < order.size(); key++) {
                int at = order.get(key).a;
                int bt = order.get(key).b;
                if (connect[at][bt]) continue;
                int lx = x[at] - x[bt];
                int ly = y[at] - y[bt];
                if (lx * lx + ly * ly <= MAX_DIST * MAX_DIST) {
                    boolean cross = false;
                    for (int j = 0; j < edges.size(); j++) {
                        int as = edges.get(j).a;
                        int bs = edges.get(j).b;
                        if (intersect(x[at], y[at], x[bt], y[bt], 
                                      x[as], y[as], x[bs], y[bs]))
                        {
                            cross = true;
                            break;
                        }
                    }
                    if (!cross) {
                        edges.add(new Tuple(0, at, bt));
                        connect[at][bt] = true;
                        connect[bt][at] = true;
                    }
                }
            }
        }
        M = edges.size();
        a = new int[M];
        b = new int[M];
        for (int i = 0; i < edges.size(); i++) {
            a[i] = edges.get(i).a;
            b[i] = edges.get(i).b;
        }

        /* Get the output. */
        proc.getOutputStream().write(getInputString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());
        K = sc.nextInt();
        v = new int[K];
        for (int i = 0; i < K; i++) {
            v[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }
    }
}
