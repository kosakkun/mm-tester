import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public class InputData implements Cloneable
{
    public static final int MAX_N = 1000;
    public static final int MIN_N = 50;
    public static final int MAX_X = 1000;
    public static final int MAX_Y = 1000;

    public int N;
    public int[] x;
    public int[] y;

    public InputData (final int N)
    {
        this.N = N;
        this.x = new int[N];
        this.y = new int[N];
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

    @Override
    public InputData clone ()
    {
        InputData id = null;

        try {
            id = (InputData)super.clone();
            id.x = this.x.clone();
            id.y = this.y.clone();
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
        InputData id = new InputData(N);
        
        Set<Pair> used = new HashSet<>();
        for (int i = 0; i < id.N; i++) {
            while (true) {
                final int xt = rnd.nextInt(MAX_X + 1);
                final int yt = rnd.nextInt(MAX_Y + 1);
                Pair p = Pair.of(xt, yt);
                if (used.contains(p)) continue;
                used.add(p);
                id.x[i] = xt;
                id.y[i] = yt;
                break;
            }
        }

        return id;
    }
}
