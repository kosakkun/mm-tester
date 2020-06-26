import java.security.SecureRandom;

public class InputData
{
    public static final int MAX_N = 500;
    public static final int MIN_N = 50;
    public static final int MAX_M = 10;
    public static final int MIN_M = 3;
    public static final int MAX_CAP = 20;
    public static final int MIN_CAP = 5;
    public static final int MAX_SPEED = 20;
    public static final int MIN_SPEED = 1;
    public static final int MAX_X = 1000;
    public static final int MAX_Y = 1000;
    public static final int NEAREST = 50;

    public int N;
    public int M;
    public int depotX;
    public int depotY;
    public int[] x;
    public int[] y;
    public int[] cap;
    public int[] speed;

    public static InputData genInputData (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        InputData id = new InputData();
        id.N = rnd.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        id.M = rnd.nextInt(MAX_M - MIN_M + 1) + MIN_M;
        id.depotX = rnd.nextInt(MAX_X + 1);
        id.depotY = rnd.nextInt(MAX_Y + 1);
        id.x = new int[id.N];
        id.y = new int[id.N];

        boolean[][] used = new boolean[MAX_X + 1][MAX_Y + 1];
        used[id.depotX][id.depotY] = true;
        for (int i = 0; i < id.N; i++) {
            while (true) {
                int xt = rnd.nextInt(MAX_X + 1);
                int yt = rnd.nextInt(MAX_Y + 1);
                if (used[xt][yt]) continue;
                int lx = xt - id.depotX;
                int ly = yt - id.depotY;
                if (lx * lx + ly * ly >= NEAREST * NEAREST) {
                    used[xt][yt] = true;
                    id.x[i] = xt;
                    id.y[i] = yt;
                    break;
                }
            }
        }

        id.cap   = new int[id.M];
        id.speed = new int[id.M];
        for (int i = 0; i < id.M; i++) {
            id.cap[i]   = rnd.nextInt(MAX_CAP - MIN_CAP + 1) + MIN_CAP;
            id.speed[i] = rnd.nextInt(MAX_SPEED - MIN_SPEED + 1) + MIN_SPEED;
        }

        return id;
    }

    @Override
    public String toString ()
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
}
