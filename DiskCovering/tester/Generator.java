import java.security.SecureRandom;

public class Generator
{
    public static final int MAX_N = 1000;
    public static final int MIN_N = 20;
    public static final int MAX_R = 100;
    public static final int MIN_R = 10;
    public static final int MAX_X = 1000;
    public static final int MAX_Y = 1000;

    public static InputData genInput (
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
}
