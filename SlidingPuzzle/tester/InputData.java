import java.security.SecureRandom;

public class InputData
{
    public static final int MAX_N = 10;
    public static final int MIN_N = 4;
    public static final int SHUFFLE = 100000;

    public int N;
    public int[][] B;

    public static InputData genInputData (
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

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                sb.append(B[x][y]);
                sb.append((y < N - 1) ? ' ' : '\n');
            }
        }

        return sb.toString();
    }
}
