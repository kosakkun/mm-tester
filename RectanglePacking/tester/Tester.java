import java.util.Scanner;
import java.security.SecureRandom;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tester 
{
    @JsonIgnore public final int N = 100;
    @JsonIgnore public final int RECT_MAX = 100;
    @JsonIgnore public final int RECT_MIN = 5;
    @JsonIgnore public final int BOX_SIZE = 1000;

    public final long seed;
    @JsonIgnore public final int[] h;
    @JsonIgnore public final int[] w;
    @JsonIgnore public final int[] x;
    @JsonIgnore public final int[] y;
    @JsonIgnore private int score_t = -2;

    @JsonIgnore
    public String getInputString () {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(w[i]).append(' ');
            sb.append(h[i]).append('\n');
        }
        return sb.toString();
    }

    @JsonIgnore
    public String getOutputString () {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < x.length; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }
        return sb.toString();
    }

    public int getScore () {

        if (score_t >= -1) {
            return score_t;
        }

        /* Check whether the output satisfies the constraints. */
        boolean[][] used = new boolean[BOX_SIZE][BOX_SIZE];
        for (int i = 0; i < N; i++) {
            for (int xt = x[i]; xt < x[i] + w[i]; xt++) {
                for (int yt = y[i]; yt < y[i] + h[i]; yt++) {
                    if (xt < 0 || yt < 0 || xt >= BOX_SIZE || yt >= BOX_SIZE) {
                        System.err.println("There is a rectangle which does not fit in the box.");
                        return score_t = -1;
                    }
                    if (used[xt][yt]) {
                        System.err.println("There are overlapping rectangles.");
                        return score_t = -1;
                    }
                    used[xt][yt] = true;
                }
            }
        }

        /* Calculate the score. */
        int highest_pos = 0;
        for (int i = 0; i < N; i++) {
            highest_pos = Math.max(highest_pos, y[i] + h[i]);
        }
        return score_t = highest_pos;
    }

    public Tester (final long _seed, final String exec) throws Exception {

        this.seed = _seed;
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();

        /* Generate input. */
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);
        h = new int[N];
        w = new int[N];
        for (int i = 0; i < N; i++) {
            w[i] = rnd.nextInt(RECT_MAX - RECT_MIN + 1) + RECT_MIN;
            h[i] = rnd.nextInt(RECT_MAX - RECT_MIN + 1) + RECT_MIN;
        }

        /* Get the output. */
        proc.getOutputStream().write(getInputString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());
        x = new int[N];
        y = new int[N];
        for (int i = 0; i < N; i++) {
            x[i] = sc.nextInt();
            y[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        getScore();
    }
}
