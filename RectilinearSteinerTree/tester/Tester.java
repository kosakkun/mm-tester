import java.util.Scanner;
import java.security.SecureRandom;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tester 
{
    @JsonIgnore public final int[] dx = {1, 0, -1, 0};
    @JsonIgnore public final int[] dy = {0, 1, 0, -1};
    @JsonIgnore public final int MAXN   = 200;
    @JsonIgnore public final int MINN   = 10;
    @JsonIgnore public final int WIDTH  = 100;
    @JsonIgnore public final int HEIGHT = 100;
    
    public final long seed;
    @JsonIgnore public final int N;
    @JsonIgnore public final int[] x;
    @JsonIgnore public final int[] y;
    @JsonIgnore public final int M;
    @JsonIgnore public final int[] ax;
    @JsonIgnore public final int[] ay;
    @JsonIgnore public boolean[][] board;
    @JsonIgnore public boolean[][] connect;
    @JsonIgnore private int score_t = -2;

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
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(ax[i]).append(' ');
            sb.append(ay[i]).append('\n');
        }
        return sb.toString();
    }
   
    private void dfs (int xt, int yt, int wl, int hl) {
        if (connect[xt][yt]) return;
        connect[xt][yt] = true;
        for (int i = 0; i < 4; i++) {
            int nx = xt + dx[i];
            int ny = yt + dy[i];
            if (nx < 0 || ny < 0 || nx >= wl || ny >= hl) continue;
            if (!board[nx][ny]) continue;
            if (connect[nx][ny]) continue;  
            dfs(nx, ny, wl, hl);
        }
    }

    public int getScore () {

        if (score_t >= -1) {
            return score_t;
        }

        /* Check whether the output satisfies the constraints. */
        board = new boolean[WIDTH][HEIGHT];
        for (int i = 0; i < N; i++) {
            board[x[i]][y[i]] = true;
        }
        for (int i = 0; i < M; i++) {
            if (ax[i] < 0 || ay[i] < 0 || ax[i] >= WIDTH || ay[i] >= HEIGHT) {
                System.err.println("The panel coordinates must be 0 <= xi,yi <= 99, " + 
                                   "but your output contains (" + ax[i] + "," + ay[i] + ").");
                return score_t = -1;
            }
            if (board[ax[i]][ay[i]]) {
                System.err.println("Panels overlap with x = " + ax[i] + ", y = " + ay[i] + ".");
                return score_t = -1;
            }
            board[ax[i]][ay[i]] = true;
        }
        connect = new boolean[WIDTH][HEIGHT];
        dfs(x[0], y[0], WIDTH, HEIGHT);
        for (int i = 0; i < N; i++) {
            if (!connect[x[i]][y[i]]) {
                System.err.println("Panels are not connected.");
                return score_t = -1;
            }
        }

        /* Calculate the score. */
        return score_t = M;
    }

    public Tester (final long _seed, final String exec) throws Exception {

        this.seed = _seed;
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();

        /* Generate input. */
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);
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
        M = sc.nextInt();
        ax = new int[M];
        ay = new int[M];
        for (int i = 0; i < M; i++) {
            ax[i] = sc.nextInt();
            ay[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }
    }
}
