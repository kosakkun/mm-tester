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
        boolean[] used = new boolean[id.N];
        for (int i = 0; i < id.N; i++) {
            if (od.v[i] < 0 || od.v[i] >= id.N) {
                System.err.println(
                    "The vertex numbers must be between 0 and " +
                    (id.N - 1) + ", but your output includes " +
                    od.v[i] + ".");
                return false;
            }
            if (used[od.v[i]]) {
                System.err.println(
                    "The vertex numbers must be unique, but your" + 
                    " output contains " + od.v[i] + " more than once.");
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

        switch (id.type) {
            case 1: return type1(id, od);
            case 2: return type2(id, od);
            case 3: return type3(id, od);
            case 4: return type4(id, od);
            default:
                System.err.println("Invalid scoring type: " + id.type);
                throw new Exception();
        }
    }

    private static double type1 (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        double score = 0.0;
        for (int i = 0; i < id.N; i++) {
            double dx = id.x[od.v[i]] - id.x[od.v[(i + 1) % id.N]];
            double dy = id.y[od.v[i]] - id.y[od.v[(i + 1) % id.N]];
            score += Math.sqrt(dx * dx + dy * dy);
        }

        return score;
    }

    private static double type2 (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        double score = 0.0;
        for (int i = 0; i < id.N; i++) {
            double dx = id.x[od.v[i]] - id.x[od.v[(i + 1) % id.N]];
            double dy = id.y[od.v[i]] - id.y[od.v[(i + 1) % id.N]];
            score += Math.abs(dx) + Math.abs(dy);
        }

        return score;
    }

    private static double type3 (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        double score = 0.0;
        for (int i = 0; i < id.N; i++) {
            double dx = id.x[od.v[i]] - id.x[od.v[(i + 1) % id.N]];
            double dy = id.y[od.v[i]] - id.y[od.v[(i + 1) % id.N]];
            score += Math.max(Math.abs(dx), Math.abs(dy));
        }

        return score;
    }

    private static double type4 (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        double score = 0.0;
        for (int i = 0; i < id.N; i++) {
            double dx = id.x[od.v[i]] - id.x[od.v[(i + 1) % id.N]];
            double dy = id.y[od.v[i]] - id.y[od.v[(i + 1) % id.N]];
            score = Math.max(score, Math.sqrt(dx * dx + dy * dy));
        }

        return score;
    }
}
