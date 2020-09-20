import java.security.SecureRandom;

public class InputData implements Cloneable
{
    public static final int FIXED_N  = 400;
    public static final int MAX_SIZE = 50;
    public static final int MIN_SIZE = 5;

    public int N;
    public int[] w;
    public int[] h;

    public InputData (final int N)
    {
        this.N = N;
        this.w = new int[N];
        this.h = new int[N];
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

    @Override
    public InputData clone ()
    {
        InputData id = null;

        try {
            id = (InputData)super.clone();
            id.w = this.w.clone();
            id.h = this.h.clone();
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

        InputData id = new InputData(FIXED_N);
        for (int i = 0; i < id.N; i++) {
            id.w[i] = rnd.nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE;
            id.h[i] = rnd.nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE;
        }

        return id;
    }
}
