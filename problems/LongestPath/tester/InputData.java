import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.security.SecureRandom;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class InputData implements Cloneable
{
    public static final int MAX_N = 1000;
    public static final int MIN_N = 50;
    public static final int MAX_X = 1000;
    public static final int MAX_Y = 1000;
    public static final int MIN_DIST = 20;

    public int N;
    public int M;
    public int[] x;
    public int[] y;
    public int[] a;
    public int[] b;

    public InputData (
        final int N,
        final int M)
    {
        this.N = N;
        this.M = M;
        this.x = new int[N];
        this.y = new int[N];
        this.a = new int[M];
        this.b = new int[M];
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }
        for (int i = 0; i < M; i++) {
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
            id.x = this.x.clone();
            id.y = this.y.clone();
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

        final int N = rnd.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        InputData id = new InputData(N, 1);

        for (int i = 0; i < id.N; i++) {
            while (true) {
                int xt = rnd.nextInt(MAX_X + 1);
                int yt = rnd.nextInt(MAX_Y + 1);
                boolean ok = true;
                for (int j = 0; j < i; j++) {
                    int lx = id.x[j] - xt;
                    int ly = id.y[j] - yt;
                    if (lx * lx + ly * ly <= MIN_DIST * MIN_DIST) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    id.x[i] = xt;
                    id.y[i] = yt;
                    break;
                }
            }
        }

        ArrayList<Triple<Integer,Integer,Integer>> order = 
            new ArrayList<Triple<Integer,Integer,Integer>>();
        
        for (int i = 0; i < id.N; i++) {
            for (int j = 0; j < id.N; j++) {
                if (i == j) continue;
                int key = rnd.nextInt(Integer.MAX_VALUE);
                order.add(Triple.of(key, i, j));
            }
        }

        order.sort((a, b) -> a.compareTo(b));
        Set<Pair<Integer,Integer>> connect = new HashSet<>();
        ArrayList<Pair<Integer,Integer>> edges = new ArrayList<Pair<Integer,Integer>>();
        
        for (int i = 0; i < 2; i++) {
            final int MAX_DIST = (int)((i + 1) * 1000.0 / Math.sqrt(id.N));
            for (int key = 0; key < order.size(); key++) {
                final int at = (int)order.get(key).getMiddle();
                final int bt = (int)order.get(key).getRight();
                Pair<Integer,Integer> e1 = Pair.of(at, bt);
                Pair<Integer,Integer> e2 = Pair.of(bt, at);
                if (connect.contains(e1)) continue;
                final int lx = id.x[at] - id.x[bt];
                final int ly = id.y[at] - id.y[bt];
                if (lx * lx + ly * ly <= MAX_DIST * MAX_DIST) {
                    boolean cross = false;
                    for (int j = 0; j < edges.size(); j++) {
                        int as = (int)edges.get(j).getLeft();
                        int bs = (int)edges.get(j).getRight();
                        if (intersect(
                            id.x[at], id.y[at], id.x[bt], id.y[bt], 
                            id.x[as], id.y[as], id.x[bs], id.y[bs]))
                        {
                            cross = true;
                            break;
                        }
                    }
                    if (!cross) {
                        edges.add(Pair.of(at, bt));
                        connect.add(e1);
                        connect.add(e2);
                    }
                }
            }
        }

        id.M = edges.size();
        id.a = new int[id.M];
        id.b = new int[id.M];
        for (int i = 0; i < edges.size(); i++) {
            id.a[i] = (int)edges.get(i).getLeft();
            id.b[i] = (int)edges.get(i).getRight();
        }

        return id;
    }

    private static boolean intersect (
        long l1_ax, long l1_ay, long l1_bx, long l1_by,
        long l2_ax, long l2_ay, long l2_bx, long l2_by)
    {
        long tc1 = (l1_ax - l1_bx) * (l2_ay - l1_ay) + (l1_ay - l1_by) * (l1_ax - l2_ax);
        long tc2 = (l1_ax - l1_bx) * (l2_by - l1_ay) + (l1_ay - l1_by) * (l1_ax - l2_bx);
        long td1 = (l2_ax - l2_bx) * (l1_ay - l2_ay) + (l2_ay - l2_by) * (l2_ax - l1_ax);
        long td2 = (l2_ax - l2_bx) * (l1_by - l2_ay) + (l2_ay - l2_by) * (l2_ax - l1_bx);
        return tc1 * tc2 < 0 && td1 * td2 < 0;
    }
}
