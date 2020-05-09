import java.security.SecureRandom;

public class InputData
{
    public static final int MAX_N = 200;
    public static final int MIN_N = 10;
    public static final int MAX_X = 99;
    public static final int MAX_Y = 99;
    
    public int N;
    public int[] x;
    public int[] y;

    public static InputData genInput (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        InputData id = new InputData();
        id.N = rnd.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        id.x = new int[id.N];
        id.y = new int[id.N];
        
        boolean[][] used = new boolean[MAX_X + 1][MAX_Y + 1];
        for (int i = 0; i < id.N; i++) {
            while (true) {
                int xt = rnd.nextInt(MAX_X + 1);
                int yt = rnd.nextInt(MAX_Y + 1);
                if (used[xt][yt]) continue;
                used[xt][yt] = true;
                id.x[i] = xt;
                id.y[i] = yt;
                break;
            }
        }

        return id;
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }

        return sb.toString();
    }
}
