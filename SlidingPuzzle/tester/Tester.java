import java.util.Scanner;
import java.security.SecureRandom;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tester 
{
    @JsonIgnore public final int MAXN = 10;
    @JsonIgnore public final int MINN = 4;
    @JsonIgnore public final int SHUFFLE = 100000;
    
    public final long seed;
    @JsonIgnore public final int N;
    @JsonIgnore public final int initBposX;
    @JsonIgnore public final int initBposY;
    @JsonIgnore public final int[][] initBoard;
    @JsonIgnore public int turn;
    @JsonIgnore public int bposX;
    @JsonIgnore public int bposY;
    @JsonIgnore public int[][] curBoard;
    @JsonIgnore public final int M;
    @JsonIgnore public final int[] posX;
    @JsonIgnore public final int[] posY;
    @JsonIgnore private long score_t = -2;

    @JsonIgnore
    public String getInputString () {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                sb.append(initBoard[x][y]);
                sb.append((y < N - 1) ? ' ' : '\n');
            }
        }
        return sb.toString();
    }

    @JsonIgnore
    public String getOutputString () {
        StringBuffer sb = new StringBuffer();
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(posX[i]).append(' ');
            sb.append(posY[i]).append('\n');
        }
        return sb.toString();
    }

    private boolean move_pannel (int x, int y) {

        if (bposX < 0 || bposX >= N) return false;
        if (bposY < 0 || bposY >= N) return false;
        if (x == bposX && y != bposY) {
            if (y < bposY) {
                for (int i = bposY; i > y; i--) {
                    curBoard[x][i] = curBoard[x][i - 1];
                }
            } else {
                for (int i = bposY; i < y; i++) {
                    curBoard[x][i] = curBoard[x][i + 1];
                }
            }
            curBoard[x][y] = -1;
            bposX = x;
            bposY = y;
            return true;
        }
        if (x != bposX && y == bposY) {
            if (x < bposX) {
                for (int i = bposX; i > x; i--) {
                    curBoard[i][y] = curBoard[i - 1][y];
                }
            } else {
                for (int i = bposX; i < x; i++) {
                    curBoard[i][y] = curBoard[i + 1][y];
                }
            }
            curBoard[x][y] = -1;
            bposX = x;
            bposY = y;
            return true;
        }
        return false;
    }

    public void initPuzzle () {
        turn = 0;
        bposX = initBposX;
        bposY = initBposY;
        curBoard = initBoard;
    }

    public boolean nextPuzzle () {
        if (turn >= M) {
            return false;
        }
        move_pannel(posX[turn], posY[turn]);
        turn++;
        return true;
    }

    public long getScore () {

        if (score_t >= -1) {
            return score_t;
        }

        if (M > SHUFFLE) {
            System.err.println("The number of slides must be less than or equal to " + 
                               SHUFFLE + ", but your output is " + M + ".");
            return score_t = -1;
        }

        long score = M;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                if (curBoard[x][y] >= 0) {
                    long nx = (curBoard[x][y] - 1) / N;
                    long ny = (curBoard[x][y] - 1) % N;
                    long diff = Math.abs(x - nx) + Math.abs(y - ny);
                    score += diff * 100000;
                }
            }
        }
        return score_t = score;
    }

    public Tester (final long _seed, final String exec) throws Exception {

        this.seed = _seed;
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();

        /* Generate input. */
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(_seed);
        if (_seed <= 7) {
            N = (int)_seed + 3;
        } else {
            N = rnd.nextInt(MAXN - MINN + 1) + MINN;
        }
        curBoard = new int[N][N];
        bposX = N - 1;
        bposY = N - 1;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                curBoard[x][y] = x * N + y + 1;
            }
        }
        curBoard[bposX][bposY] = -1;
        for (int i = 0; i < SHUFFLE; i++) {
            while (true) {
                int x = rnd.nextInt(N);
                int y = rnd.nextInt(N);
                if (move_pannel(x, y)) break;
            }
        }
        initBoard = curBoard;
        initBposX = bposX;
        initBposY = bposY;

        /* Get the output. */
        proc.getOutputStream().write(getInputString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());
        M = sc.nextInt();
        posX = new int[M];
        posY = new int[M];
        for (int i = 0; i < M; ++i) {
            posX[i] = sc.nextInt();
            posY[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }
    }
}
