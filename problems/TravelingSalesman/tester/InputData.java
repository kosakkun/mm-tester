import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public class InputData implements Cloneable
{
    public static final int MAX_N = 500;
    public static final int MIN_N = 50;
    public static final int MAX_X = 100;
    public static final int MAX_Y = 100;

    public int N;
    public int[] x;
    public int[] y;
    public int[][] G;
    public int type;

    public InputData (
        final int N,
        final int type)
    {
        this.N = N;
        this.x = new int[N];
        this.y = new int[N];
        this.G = new int[N][N];
        this.type = type;
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');

        switch (this.type) {
            case 1:
            case 2: 
                for (int i = 0; i < N; ++i) {
                    sb.append(x[i]).append(' ');
                    sb.append(y[i]).append('\n');
                }
                break;
            default:
                sb.append("Invalid scoring type.\n");
                break;
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
            for (int i = 0; i < this.N; i++) {
                id.G[i] = this.G[i].clone();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public static InputData genInputData (
        final long seed,
        final int type)
        throws Exception
    {
        switch (type) {
            case 1: return type1(seed);
            case 2: return type2(seed);
            default: return new InputData(1, type);
        }
    }

    private static InputData type1 (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        final int N = rnd.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        InputData id = new InputData(N, 1);
        
        Set<Pair<Integer,Integer>> used = new HashSet<>();
        while (used.size() < id.N) {
            final int xt = rnd.nextInt(MAX_X + 1);
            final int yt = rnd.nextInt(MAX_Y + 1);
            Pair<Integer,Integer> p = Pair.of(xt, yt);
            if (used.contains(p)) continue;
            id.x[used.size()] = xt;
            id.y[used.size()] = yt;
            used.add(p);
        }

        return id;
    }

    private static InputData type2 (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        final int N = rnd.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        InputData id = new InputData(N, 2);
        
        Set<Pair<Integer,Integer>> used = new HashSet<>();
        while (used.size() < id.N) {
            final int xt = rnd.nextInt(MAX_X + 1);
            final int yt = rnd.nextInt(MAX_Y + 1);
            Pair<Integer,Integer> p = Pair.of(xt, yt);
            if (used.contains(p)) continue;
            id.x[used.size()] = xt;
            id.y[used.size()] = yt;
            used.add(p);
        }

        return id;
    }
}
