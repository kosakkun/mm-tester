import java.security.SecureRandom;

public class InputData implements Cloneable
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

    public InputData (
        final int N,
        final int K)
    {
        this.N = N;
        this.K = K;
        this.B = new int[N][N];
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

    @Override
    public InputData clone ()
    {
        InputData id = null;

        try {
            id = (InputData)super.clone();
            id.B = new int[this.N][];
            for (int i = 0; i < this.N; i++) {
                id.B[i] = this.B[i].clone();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public static InputData genInputData (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        final int N = rnd.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        final int K = rnd.nextInt(MAX_K - MIN_K + 1) + MIN_K;
        InputData id = new InputData(N, K);
        
        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N; c++) {
                id.B[r][c] = rnd.nextInt(MAX_B - MIN_B + 1) + MIN_B;
            }
        }

        return id;
    }
}
