import java.security.SecureRandom;

public class InputData
{
    public static final int MAX_N = 1000;
    public static final int MIN_N = 20;
    public static final int MAX_R = 100;
    public static final int MIN_R = 10;
    public static final int MAX_X = 1000;
    public static final int MAX_Y = 1000;

    public int N;
    public int R;
    public int[] xp;
    public int[] yp;

    public static InputData genInputData (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        InputData id = new InputData();
        id.N = rnd.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        id.R = rnd.nextInt(MAX_R - MIN_R + 1) + MIN_R;
        id.xp = new int[id.N];
        id.yp = new int[id.N];

        boolean [][] used = new boolean[MAX_X + 1][MAX_Y + 1];
        for (int i = 0; i < id.N; i++) {
            while (true) {
                int x = rnd.nextInt(MAX_X + 1);
                int y = rnd.nextInt(MAX_Y + 1);
                if (used[x][y]) continue;
                used[x][y] = true;
                id.xp[i] = x;
                id.yp[i] = y;
                break;
            }
        }

        return id;
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(R).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(xp[i]).append(' ');
            sb.append(yp[i]).append('\n');
        }
        
        return sb.toString();
    }
}
