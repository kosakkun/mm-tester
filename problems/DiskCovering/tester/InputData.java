import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public class InputData implements Cloneable
{
    public static final int MAX_N = 1000;
    public static final int MIN_N = 20;
    public static final int MAX_R = 100;
    public static final int MIN_R = 10;
    public static final int MAX_X = 1000;
    public static final int MAX_Y = 1000;

    public int N;
    public int R;
    public int[] xp;
    public int[] yp;

    public InputData (
        final int N,
        final int R)
    {
        this.N = N;
        this.R = R;
        this.xp = new int[N];
        this.yp = new int[N];
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(R).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(xp[i]).append(' ');
            sb.append(yp[i]).append('\n');
        }
        
        return sb.toString();
    }

    @Override
    public InputData clone ()
    {
        InputData id = null;

        try {
            id = (InputData)super.clone();
            id.xp = this.xp.clone();
            id.yp = this.yp.clone();
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
        final int R = rnd.nextInt(MAX_R - MIN_R + 1) + MIN_R;
        InputData id = new InputData(N, R);

        Set<Pair> used = new HashSet<>();
        while (used.size() < id.N) {
            final int x = rnd.nextInt(MAX_X + 1);
            final int y = rnd.nextInt(MAX_Y + 1);
            Pair p = Pair.of(x, y);
            if (used.contains(p)) continue;
            id.xp[used.size()] = x;
            id.yp[used.size()] = y;
            used.add(p);
            break;
        }

        return id;
    }
}
