import java.util.ArrayList;

public class Checker
{
    public static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        if (od.M < OutputData.MIN_M || od.M > OutputData.MAX_M) {
            System.err.println(
                "The number of added vertices must be between " + 
                OutputData.MIN_M + " and " + OutputData.MAX_M +
                ", but your output is " + od.M + ".");
            return false;
        }

        int WIDTH  = InputData.MAX_X + 1;
        int HEIGHT = InputData.MAX_Y + 1;
        boolean[][] used = new boolean[WIDTH][HEIGHT];

        for (int i = 0; i < id.N; i++) {
            used[id.x[i]][id.y[i]] = true;
        }

        for (int i = 0; i < od.M; i++) {
            if (od.ax[i] < 0 || od.ay[i] < 0 ||
                od.ax[i] >= WIDTH || od.ay[i] >= HEIGHT) {
                System.err.println(
                    "All vertex coordinates must be 0 <= xi <= " +
                    InputData.MAX_X + ", 0 <= yi <= " + InputData.MAX_Y +
                    ", but your output includes (" + od.ax[i] + ", " + od.ay[i] + ").");
                return false;
            }
            if (used[od.ax[i]][od.ay[i]]) {
                System.err.println(
                    "All vertices must not have duplicate coordinates, " + 
                    "but your output will have multiple vertices at (" + 
                    od.ax[i] + ", " + od.ay[i] + ").");
                return false;
            }
            used[od.ax[i]][od.ay[i]] = true;
        }

        return true;
    }

    public static LineSegment[] getMinimumPinningTree (
        final int N,
        final int[] x,
        final int[] y)
    {
        class Tuple {
            public Double dist;
            public Integer a,b;
            public Tuple (double _d, int _a, int _b) {
                dist = _d;
                a = _a;
                b = _b;
            }
        }

        ArrayList<Tuple> order = new ArrayList<Tuple>();
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                LineSegment e = new LineSegment(x[i], y[i], x[j], y[j]);
                order.add(new Tuple(e.calcDist(), i, j));
            }
        }

        order.sort((a, b) -> a.dist.compareTo(b.dist));
        ArrayList<LineSegment> tree = new ArrayList<LineSegment>();
        DisjointSet ds = new DisjointSet(N);

        for (int i = 0; i < order.size(); i++) {
            int at = order.get(i).a;
            int bt = order.get(i).b;
            if (!ds.same(at, bt)) {
                ds.unite(at, bt);
                tree.add(new LineSegment(x[at], y[at], x[bt], y[bt]));
            }
        }

        LineSegment[] MST = new LineSegment[tree.size()];
        tree.toArray(MST);

        return MST;
    }

    public static double calcScore (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        if (!isValid(id, od)) {
            return -1.0;
        }

        int[] nx = new int[id.N + od.M];
        int[] ny = new int[id.N + od.M];

        for (int i = 0; i < id.N; i++) {
            nx[i] = id.x[i];
            ny[i] = id.y[i];
        }

        for (int i = id.N; i < id.N + od.M; i++) {
            nx[i] = od.ax[i - id.N];
            ny[i] = od.ay[i - id.N];
        }

        double score = 0.0;
        LineSegment[] MST = getMinimumPinningTree(id.N + od.M, nx, ny);
        for (int i = 0; i < MST.length; i++) {
            score += MST[i].calcDist();
        }

        return score;
    }
}
