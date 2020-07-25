import java.security.SecureRandom;

public class InputData
{
    public static final int MAX_N = 50;
    public static final int MIN_N = 50;
    public static final int MAX_K = 100;
    public static final int MIN_K = 3;
    public static final int MAX_B = 99;
    public static final int MIN_B = 1;

    public int N;
    public int K;
    public int[][] B;

    public static InputData genInputData (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        InputData id = new InputData();
        id.N = rnd.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        id.K = rnd.nextInt(MAX_K - MIN_K + 1) + MIN_K;
        id.B = new int[id.N][id.N];
        
        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N; c++) {
                id.B[r][c] = rnd.nextInt(MAX_B - MIN_B + 1) + MIN_B;
            }
        }

        return id;
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(K).append('\n');
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                sb.append(B[r][c]);
                sb.append((c < N - 1) ? ' ' : '\n');
            }
        }

        return sb.toString();
    }
}
