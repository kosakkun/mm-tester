import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public class InputData implements Cloneable
{
    public static final int MAX_N = 500;
    public static final int MIN_N = 50;
    public static final int MAX_M = 10;
    public static final int MIN_M = 3;
    public static final int MAX_CAP = 20;
    public static final int MIN_CAP = 5;
    public static final int MAX_SPEED = 20;
    public static final int MIN_SPEED = 1;
    public static final int MAX_X = 1000;
    public static final int MAX_Y = 1000;
    public static final int NEAREST = 50;

    public int N;
    public int M;
    public int depotX;
    public int depotY;
    public int[] x;
    public int[] y;
    public int[] cap;
    public int[] speed;

    public InputData (
        final int N,
        final int M)
    {
        this.N = N;
        this.M = M;
        this.depotX = 0;
        this.depotY = 0;
        this.x = new int[N];
        this.y = new int[N];
        this.cap   = new int[M];
        this.speed = new int[M];
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        sb.append(depotX).append(' ');
        sb.append(depotY).append('\n');
        for (int i = 0; i < N; i++) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n'); 
        }
        for (int i = 0; i < M; i++) {
            sb.append(cap[i]).append(' ');
            sb.append(speed[i]).append('\n'); 
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
            id.cap = this.cap.clone();
            id.speed = this.speed.clone();
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
        final int M = rnd.nextInt(MAX_M - MIN_M + 1) + MIN_M;
        InputData id = new InputData(N, M);
        
        id.depotX = rnd.nextInt(MAX_X + 1);
        id.depotY = rnd.nextInt(MAX_Y + 1);

        Set<Pair> used = new HashSet<>();
        used.add(Pair.of(id.depotX, id.depotY));
        while (used.size() < id.N + 1) {
            final int xt = rnd.nextInt(MAX_X + 1);
            final int yt = rnd.nextInt(MAX_Y + 1);
            Pair p = Pair.of(xt, yt);
            if (used.contains(p)) continue;
            final int lx = xt - id.depotX;
            final int ly = yt - id.depotY;
            if (lx * lx + ly * ly >= NEAREST * NEAREST) {
                id.x[used.size() - 1] = xt;
                id.y[used.size() - 1] = yt;
                used.add(p);
            }
        }
        
        for (int i = 0; i < id.M; i++) {
            id.cap[i]   = rnd.nextInt(MAX_CAP - MIN_CAP + 1) + MIN_CAP;
            id.speed[i] = rnd.nextInt(MAX_SPEED - MIN_SPEED + 1) + MIN_SPEED;
        }

        return id;
    }
}
