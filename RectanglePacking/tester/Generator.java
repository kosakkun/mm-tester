import java.security.SecureRandom;

public class Generator
{
    public static final int N = 100;
    public static final int MAX_SIZE = 100;
    public static final int MIN_SIZE = 5;

    public static InputData genInput (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        InputData id = new InputData();
        id.N = N;
        id.h = new int[id.N];
        id.w = new int[id.N];

        for (int i = 0; i < id.N; i++) {
            id.w[i] = rnd.nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE;
            id.h[i] = rnd.nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE;
        }

        return id;
    }
}
