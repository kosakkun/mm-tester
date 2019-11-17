import java.util.Scanner;
import java.security.SecureRandom;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tester 
{
    @JsonIgnore public final int MAXN   = 1000;
    @JsonIgnore public final int MINN   = 20;
    @JsonIgnore public final int MAXR   = 100;
    @JsonIgnore public final int MINR   = 10;
    @JsonIgnore public final int WIDTH  = 1000 + 1;
    @JsonIgnore public final int HEIGHT = 1000 + 1;
    
    public final long seed;
    @JsonIgnore public final int N;
    @JsonIgnore public final int R;
    @JsonIgnore public final int[] px;
    @JsonIgnore public final int[] py;
    @JsonIgnore public final int M;
    @JsonIgnore public final int[] rx;
    @JsonIgnore public final int[] ry;
    @JsonIgnore private int score_t = -2;

    @JsonIgnore
    public String getInputString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(R).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(px[i]).append(' ');
            sb.append(py[i]).append('\n');
        }
        return sb.toString();
    }

    @JsonIgnore
    public String getOutputString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(M);
        for (int i = 0; i < M; ++i) {
            sb.append(rx[i]).append(' ');
            sb.append(ry[i]).append('\n');
        }
        return sb.toString();
    }

    public int getScore ()
    {
        if (score_t >= -1) {
            return score_t;
        }

        /* Check whether the output satisfies the constraints. */
        for (int i = 0; i < M; i++) {
            if (rx[i] < 0 || ry[i] < 0 || rx[i] >= WIDTH || ry[i] >= HEIGHT) {
                System.err.println("The coordinates of the disk must satisfy 0 <= xi,yi <= 1000, but your output " +
                                   "is the coordinates of the " + i + " disk is (" + rx[i] + "," + ry[i] + ").");
                return score_t = -1;
            }
        }
        for (int i = 0; i < N; i++) {
            boolean flag = false;
            for (int j = 0; j < M; j++) {
                int dx = Math.abs(px[i] - rx[j]);
                int dy = Math.abs(py[i] - ry[j]);
                if (dx * dx + dy * dy <= R * R) {
                    flag = true;
                }
            }
            if (!flag) {
                System.err.println("Point at coordinate x = " + px[i] + ", y = " + py[i] +
                                   " is not covered by a disk.");
                return score_t = -1;
            }
        }

        /* Calculate the score. */
        return score_t = M;
    }

    public Tester (final long _seed, final String exec) throws Exception
    {
        this.seed = _seed;
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();

        /* Generate input. */
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);
        N = rnd.nextInt(MAXN - MINN + 1) + MINN;
        R = rnd.nextInt(MAXR - MINR + 1) + MINR;
        px = new int[N];
        py = new int[N];
        boolean [][] usedPos = new boolean[WIDTH][HEIGHT];
        for (int i = 0; i < N; i++) {
            while (true) {
                int x = rnd.nextInt(WIDTH);
                int y = rnd.nextInt(HEIGHT);
                if (usedPos[x][y]) continue;
                usedPos[x][y] = true;
                px[i] = x;
                py[i] = y;
                break;
            }
        }

        /* Get the output. */
        proc.getOutputStream().write(getInputString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());
        M = sc.nextInt();
        rx = new int[M];
        ry = new int[M];
        for (int i = 0; i < M; i++) {
            rx[i] = sc.nextInt();
            ry[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }
    }
}
