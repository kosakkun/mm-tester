import java.security.SecureRandom;

public class Generator
{
    public static final int MAX_N = 10;
    public static final int MIN_N = 4;
    public static final int SHUFFLE = 100000;

    public static InputData genInput (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        InputData id = new InputData();
        id.N = (seed <= 7) ? (int)seed + 3 : rnd.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        id.B = new int[id.N][id.N];
        
        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N; c++) {
                id.B[r][c] = r * id.N + c + 1;
            }
        }

        id.B[id.N - 1][id.N - 1] = -1;
        for (int i = 0; i < SHUFFLE; i++) {
            while (true) {
                int r = rnd.nextInt(id.N);
                int c = rnd.nextInt(id.N);
                if (Checker.movePannel(id.N, r, c, id.B)) {
                    break;
                }
            }
        }

        return id;
    }
}
