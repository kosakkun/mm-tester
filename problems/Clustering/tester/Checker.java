public class Checker
{
    /**
     * private constructor
     */
    private Checker ()
    {
        throw new AssertionError();
    }

    public static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        int SIZE_X = InputData.MAX_X + 1;
        int SIZE_Y = InputData.MAX_Y + 1;
        boolean[][] used = new boolean[SIZE_X][SIZE_Y];
        for (int i = 0; i < id.K; i++) {
            if (od.cx[i] < 0 || od.cy[i] < 0 || od.cx[i] >= SIZE_X || od.cy[i] >= SIZE_Y) {
                System.err.println(
                    "The coordinate x = " + od.cx[i] + ", y = " + 
                    od.cy[i] + " is out of range.");
                return false;
            }
            if (used[od.cx[i]][od.cy[i]]) {
                System.err.println(
                    "The coordinate x = " + od.cx[i] + ", y = " +
                    od.cy[i] + " is duplicated.");
                return false;
            }
            used[od.cx[i]][od.cy[i]] = true;
        }
        return true;
    }

    public static int[] getRoots (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        int[] roots = new int[id.N];
        
        for (int i = 0; i < id.N; i++) {
            int idx = -1;
            double dist = 1.0e9;
            for (int j = 0; j < id.K; j++) {
                int lx = Math.abs(id.x[i] - od.cx[j]);
                int ly = Math.abs(id.y[i] - od.cy[j]);
                double dt = Math.sqrt((double)(lx * lx + ly * ly));
                if (dist > dt) {
                    dist = dt;
                    idx = j;
                }
            }
            roots[i] = idx;
        }

        return roots;
    }

    public static double calcScore (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        if (!isValid(id, od)) {
            return -1.0;
        }

        double score = 0.0;
        int[] roots = getRoots(id, od);

        for (int i = 0; i < id.N; i++) {
            int lx = Math.abs(id.x[i] - od.cx[roots[i]]);
            int ly = Math.abs(id.y[i] - od.cy[roots[i]]);
            double dt = Math.sqrt((double)(lx * lx + ly * ly));
            score += dt;
        }

        return score;
    }
}
