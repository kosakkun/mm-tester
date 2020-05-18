public class Checker
{
    private static int[] dx = {1, 0, -1, 0};
    private static int[] dy = {0, 1, 0, -1};

    private static int getDir (
        final int fx,
        final int fy,
        final int tx,
        final int ty)
    {
        if (fx == tx) {
            if (fy > ty) return 3;
            if (fy < ty) return 1;
        }
        
        if (fy == ty) {
            if (fx > tx) return 2;
            if (fx < tx) return 0;
        }

        if (fx == tx && fy == ty) {
            return 4;
        }

        return -1;
    }

    public static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        final int WIDTH  = InputData.MAX_X + 1;
        final int HEIGHT = InputData.MAX_Y + 1;

        if (od.M < 0 || od.M > id.N) {
            System.err.println(
                "M must be 0 <= M <= " + id.N + 
                ", but your output is " + od.M + ".");
            return false;
        }

        for (int vt: od.v) {
            if (vt < 0 || vt >= id.N) {
                System.err.println(
                    "v[i] must be 0 <= v[i] < N, but your " +
                    "output is " + vt + ".");
                return false;
            }
        }

        final int EMPTY = 100000;
        int[][] used = new int[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                used[x][y] = EMPTY;
            }
        }

        for (int i = 0; i < id.N; i++) {
            used[id.x[i]][id.y[i]] = i;
        }

        int pre_d = -1;
        int cur_x = (od.M > 0) ? id.x[od.v[0]] : -1;
        int cur_y = (od.M > 0) ? id.y[od.v[0]] : -1;

        for (int vt: od.v) {
            if (used[id.x[vt]][id.y[vt]] == EMPTY) {
                System.err.println(
                    "You are trying to pick up the " + vt + "-th(" +
                    id.x[vt] + "," + id.y[vt] + ") stone more than once.");
                return false;
            }

            int new_d = getDir(cur_x, cur_y, id.x[vt], id.y[vt]);
            if (new_d < 0) {
                System.err.println(
                    "You cannot move from (" + cur_x + "," + cur_y +
                    ") to (" + id.x[vt] + "," + id.y[vt] + "). " + 
                    "Either the x-axis or the y-axis must be the same.");
                return false;
            }

            if (((new_d + 2) % 4) == pre_d) {
                System.err.println(
                    "You can't go back the way you came.");
                return false;
            }

            final int px = cur_x;
            final int py = cur_y;
            used[cur_x][cur_y] = EMPTY;
            while (!(cur_x == id.x[vt] && cur_y == id.y[vt])) {
                if (used[cur_x][cur_y] != EMPTY) {
                    System.err.println(
                        "When moving from (" + px + "," + py + ") to (" +
                        id.x[vt] + "," + id.y[vt] + "), you need to pick up (" +
                        cur_x + "," + cur_y + ") stone before (" + id.x[vt] +
                        "," + id.y[vt] + ") stone.");
                    return false;
                }
                cur_x += dx[new_d];
                cur_y += dy[new_d];
            }

            pre_d = new_d;
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

        return od.M;
    }
}
