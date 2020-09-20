import java.security.SecureRandom;

public class InputData implements Cloneable
{
    public static final int MAX_N = 50;
    public static final int MIN_N = 5;
    public static final int MAX_C = 50;
    public static final int MIN_C = 5;

    public int N;
    public int C;
    public int[] U;
    public int[] D;
    public int[] L;
    public int[] R;

    public InputData (
        final int N,
        final int C)
    {
        final int PN = N * N;
        this.N = N;
        this.C = C;
        this.U = new int[PN];
        this.D = new int[PN];
        this.L = new int[PN];
        this.R = new int[PN];
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

    @Override
    public InputData clone ()
    {
        InputData id = null;

        try {
            id = (InputData)super.clone();
            id.U = this.U.clone();
            id.D = this.D.clone();
            id.L = this.L.clone();
            id.R = this.R.clone();
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
        final int C = rnd.nextInt(MAX_C - MIN_C + 1) + MIN_C;
        InputData id = new InputData(N, C);

        final int PN = N * N;
        for (int i = 0; i < PN; i++) {
            id.U[i] = rnd.nextInt(id.C);
            id.D[i] = rnd.nextInt(id.C);
            id.L[i] = rnd.nextInt(id.C);
            id.R[i] = rnd.nextInt(id.C);
        }

        return id;
    }
}
