import java.util.Arrays;

public class Checker
{
    /**
     * private constructor
     */
    private Checker ()
    {
        throw new AssertionError();
    }

    public static final int[] dr = {1, 0, -1, 0};
    public static final int[] dc = {0, 1, 0, -1};

    private static void dfs (
        final int N,
        final int r,
        final int c,
        final int[][] R,
        final boolean[][] used_cell)
        throws Exception
    {
        if (used_cell[r][c]) return;
        used_cell[r][c] = true;

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (nr < 0 || nc < 0 || nr >= N || nc >= N) {
                continue;
            }
            if (R[nr][nc] == R[r][c]) {
                dfs(N, nr, nc, R, used_cell);
            }
        }
    }

    public static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        boolean[][] used_cell = new boolean[id.N][id.N];
        boolean[]   used_rnum = new boolean[id.K];

        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N; c++) {
                if (od.R[r][c] < 0 || od.R[r][c] >= id.K) {
                    System.err.println(
                        "The region number must be 0 <= K < " + id.K +
                        ", but your output contains " + od.R[r][c] + ".");
                    return false;
                }
                if (used_cell[r][c]) {
                    continue;
                }
                if (used_rnum[od.R[r][c]]) {
                    System.err.println(
                        "Region number " + od.R[r][c] + " is not concatenated.");
                    return false;
                }
                used_rnum[od.R[r][c]] = true;
                dfs (id.N, r, c, od.R, used_cell);
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

        int[] sum = new int[id.K];
        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N; c++) {
                sum[od.R[r][c]] += id.B[r][c];
            }
        }

        Arrays.sort(sum);
        int score = sum[id.K - 1] - sum[0];

        return score;
    }
}
