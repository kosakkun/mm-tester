public class Checker
{
    public static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        boolean[][] connect = new boolean[id.N][id.N];
        for (int i = 0; i < id.M; i++) {
            connect[id.a[i]][id.b[i]] = true;
            connect[id.b[i]][id.a[i]] = true;
        }

        boolean[] used = new boolean[id.N];
        for (int i = 0; i < od.K; i++) {
            if (od.v[i] < 0 || od.v[i] >= id.N) {
                System.err.println(
                    "All elements of your output must be between 0 and " +
                    (id.N - 1) + " but contained " + od.v[i] + ".");
                return false;
            }
            if (i > 0 && !connect[od.v[i - 1]][od.v[i]]) {
                System.err.println(
                    "There is no edge connecting vertex " + od.v[i - 1] + 
                    " and vertex " + od.v[i] + ".");
                return false;
            }
            if (used[od.v[i]]) {
                System.err.println(
                    "All elements of your output must be unique " +
                    "but contained " + od.v[i] + " two or more times.");
                return false;
            }
            used[od.v[i]] = true;
        }
        
        return true;
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
        for (int i = 1; i < od.K; i++) {
            double dx = (double)(id.x[od.v[i - 1]] - id.x[od.v[i]]);
            double dy = (double)(id.y[od.v[i - 1]] - id.y[od.v[i]]);
            score += Math.sqrt(dx * dx + dy * dy);
        }

        return score;
    }
}
