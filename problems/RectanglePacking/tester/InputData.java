import java.security.SecureRandom;

public class InputData
{
    public static final int FIXED_N = 100;
    public static final int MAX_SIZE = 100;
    public static final int MIN_SIZE = 5;

    public int N;
    public int[] w;
    public int[] h;

    public static InputData genInputData (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        InputData id = new InputData();
        id.N = FIXED_N;
        id.h = new int[id.N];
        id.w = new int[id.N];

        for (int i = 0; i < id.N; i++) {
            id.w[i] = rnd.nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE;
            id.h[i] = rnd.nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE;
        }

        return id;
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(w[i]).append(' ');
            sb.append(h[i]).append('\n');
        }
        
        return sb.toString();
    }
}
