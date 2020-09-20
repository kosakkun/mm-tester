import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public class InputData implements Cloneable
{
    public static final int FIXED_N = 200;

    public int N;
    public int M;
    public int[] a;
    public int[] b;

    public InputData (
        final int N,
        final int M)
    {
        this.N = N;
        this.M = M;
        this.a = new int[M];
        this.b = new int[M];
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(a[i]).append(' ');
            sb.append(b[i]).append('\n');
        }
        
        return sb.toString();
    }

    @Override
    public InputData clone ()
    {
        InputData id = null;

        try {
            id = (InputData)super.clone();
            id.a = this.a.clone();
            id.b = this.b.clone();
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

        final int N = FIXED_N;
        final int M = rnd.nextInt(N * (N - 1) / 4 - 2 * N) + 2 * N;
        InputData id = new InputData(N, M);
        
        int esum = 0;
        Set<Pair> edge = new HashSet<>();
        while (esum < id.M) {
            final int at = rnd.nextInt(id.N);
            final int bt = rnd.nextInt(id.N);
            Pair e1 = Pair.of(at, bt);
            Pair e2 = Pair.of(bt, at);
            if (at == bt || edge.contains(e1)) {
                continue;
            }
            id.a[esum] = at;
            id.b[esum] = bt;
            esum++;
            edge.add(e1);
            edge.add(e2);
        }

        return id;
    }
}
