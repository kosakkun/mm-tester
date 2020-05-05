import java.util.Scanner;
import java.security.SecureRandom;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tester 
{
    public final long seed;
    @JsonIgnore public final int N = 100;
    @JsonIgnore public final int M;
    @JsonIgnore public final int[] a;
    @JsonIgnore public final int[] b;
    @JsonIgnore public final int[] col;
    @JsonIgnore public final boolean[][] edge;
    @JsonIgnore private int score_t = -2;

    @JsonIgnore
    public String getInputString () {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(a[i]).append(' ');
            sb.append(b[i]).append('\n');
        }
        return sb.toString();
    }

    @JsonIgnore
    public String getOutputString () {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < col.length; ++i) {
            sb.append(col[i]).append('\n');
        }
        return sb.toString();
    }
   
    public int getScore () {

        if (score_t >= -1) {
            return score_t;
        }

        /* Check whether the output satisfies the constraints. */
        for (int i = 0; i < M; i++) {
            if (col[a[i]] == col[b[i]]) {
                System.err.println("Vertex " + a[i] + " and vertex " + b[i] +
                                   " are connected, but your output is the same color.");
                return score_t = -1;
            }
        }

        /* Calculate the score. */
        int cnum = 0;
        boolean[] used = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (col[i] < 0 || col[i] >= N) {
                System.err.println("The vertex color must be a number between 0 and " + 
                                   (N - 1) + ", but your output includes " + col[i] + ".");
                return score_t = -1;
            }
            if (!used[col[i]]) {
                used[col[i]] = true;
                cnum++;
            }
        }
        int cntc = 0;
        int[] recol = new int[N];
        for (int i = 0; i < N; i++) {
            if (used[i]) {
                recol[i] = cntc++;
            }
        }
        for (int i = 0; i < N; i++) {
            col[i] = recol[col[i]];
        }
        return score_t = cnum;
    }

    public Tester (final long _seed, final String exec) throws Exception {

        this.seed = _seed;
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();

        /* Generate input. */
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);
        M = rnd.nextInt(N * (N - 1) / 4 - 2 * N) + 2 * N;
        a = new int[M];
        b = new int[M];
        edge = new boolean[N][N];
        int esum = 0;
        while (esum < M) {
            int at = rnd.nextInt(N);
            int bt = rnd.nextInt(N);
            if (at == bt || edge[at][bt]) continue;
            edge[at][bt] = true;
            edge[bt][at] = true;
            a[esum] = at;
            b[esum] = bt;
            esum++;
        }

        /* Get the output. */
        proc.getOutputStream().write(getInputString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());
        col = new int[N];
        for (int i = 0; i < N; i++) {
            col[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        getScore();
    }
}
