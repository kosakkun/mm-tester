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
        final int PN = id.N * id.N;
        boolean[][] used = new boolean[id.N][id.N];

        for (int i = 0; i < PN; i++) {
            if (od.x[i] < 0 || od.x[i] >= id.N ||
                od.y[i] < 0 || od.y[i] >= id.N) {
                System.err.println(
                    "The panel coordinates should be 0 <= xi, yi < " +
                    id.N + ", but your output contains (" + od.x[i] +
                    "," + od.y[i] + ").");
                return false;
            }

            if (used[od.x[i]][od.y[i]]) {
                System.err.println(
                    "The coordinates of the panels should not " +
                    "overlap, but your output will put more than " +
                    "one panel in (" + od.x[i] + "," + od.y[i] + ").");
                return false;
            }
            used[od.x[i]][od.y[i]] = true;
        }

        for (int i = 0; i < PN; i++) {
            if (od.r[i] < 0 || od.r[i] >= 4) {
                System.err.println(
                    "The number of the rotation must be between 0 and 3," +
                    " but your output includes " + od.r[i] + ".");
                return false;
            }
        }

        return true;
    }

    public static int calcScore (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        if (!isValid(id, od)) {
            return -1;
        }

        int[][] cU = new int[id.N][id.N];
        int[][] cD = new int[id.N][id.N];
        int[][] cL = new int[id.N][id.N];
        int[][] cR = new int[id.N][id.N];

        final int PN = id.N * id.N;
        for (int i = 0; i < PN; i++) {
            int[] col = {id.U[i], id.R[i], id.D[i], id.L[i]};
            cU[od.x[i]][od.y[i]] = col[(4 + 0 - od.r[i]) % 4];
            cR[od.x[i]][od.y[i]] = col[(4 + 1 - od.r[i]) % 4];
            cD[od.x[i]][od.y[i]] = col[(4 + 2 - od.r[i]) % 4];
            cL[od.x[i]][od.y[i]] = col[(4 + 3 - od.r[i]) % 4];
        }

        int score = 0;
        for (int x = 0; x < id.N; x++) {
            for (int y = 0; y < id.N - 1; y++) {
                if (cD[x][y] == cU[x][y + 1]) {
                    score++;
                }
            }
        }
        for (int x = 0; x < id.N - 1; x++) {
            for (int y = 0; y < id.N; y++) {
                if (cR[x][y] == cL[x + 1][y]) {
                    score++;
                }
            }
        }

        return score;
    }
}
