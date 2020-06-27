import java.security.SecureRandom;

public class InputData
{
    public static final int MAX_N = 50;
    public static final int MIN_N = 5;
    public static final int MAX_C = 10;
    public static final int MIN_C = 3;

    public int N;
    public int C;
    public int[] U;
    public int[] D;
    public int[] L;
    public int[] R;

    public static InputData genInputData (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        InputData id = new InputData();
        id.N = rnd.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        id.C = rnd.nextInt(MAX_C - MIN_C + 1) + MIN_C;
        final int PN = id.N * id.N;
        id.U = new int[PN];
        id.D = new int[PN];
        id.L = new int[PN];
        id.R = new int[PN];

        for (int i = 0; i < PN; i++) {
            id.U[i] = rnd.nextInt(id.C);
            id.D[i] = rnd.nextInt(id.C);
            id.L[i] = rnd.nextInt(id.C);
            id.R[i] = rnd.nextInt(id.C);
        }

        return id;
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(C).append('\n');
        for (int i = 0; i < N * N; ++i) {
            sb.append(U[i]).append(' ');
            sb.append(D[i]).append(' ');
            sb.append(L[i]).append(' ');
            sb.append(R[i]).append('\n');
        }

        return sb.toString();
    }
}
