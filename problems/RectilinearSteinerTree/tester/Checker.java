public class Checker
{
    /**
     * private constructor
     */
    private Checker ()
    {
        throw new AssertionError();
    }

    public static final int[] dx = {1, 0, -1, 0};
    public static final int[] dy = {0, 1, 0, -1};

    public static void dfs (
        final int xt,
        final int yt,
        final int wl,
        final int hl,
        final boolean[][] board,
        final boolean[][] connect)
        throws Exception
    {
        if (connect[xt][yt]) return;
        connect[xt][yt] = true;
        for (int i = 0; i < 4; i++) {
            int nx = xt + dx[i];
            int ny = yt + dy[i];
            if (nx < 0 || ny < 0 || nx >= wl || ny >= hl) continue;
            if (!board[nx][ny]) continue;
            if (connect[nx][ny]) continue;  
            dfs(nx, ny, wl, hl, board, connect);
        }
    }

    public static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        final int WIDTH  = InputData.MAX_X + 1;
        final int HEIGHT = InputData.MAX_Y + 1;

        boolean[][] board = new boolean[WIDTH][HEIGHT];
        for (int i = 0; i < id.N; i++) {
            board[id.x[i]][id.y[i]] = true;
        }

        for (int i = 0; i < od.M; i++) {
            if (od.ax[i] < 0 || od.ay[i] < 0 ||
                od.ax[i] >= WIDTH || od.ay[i] >= HEIGHT) {
                System.err.println(
                    "The panel coordinates must be 0 <= xi <= " + InputData.MAX_X + 
                    ", 0 <= yi <= " + InputData.MAX_Y + ", but your output contains ("
                    + od.ax[i] + "," + od.ay[i] + ").");
                return false;
            }
            if (board[od.ax[i]][od.ay[i]]) {
                System.err.println(
                    "Panels overlap with x = " + od.ax[i] + ", y = " + od.ay[i] + ".");
                return false;
            }
            board[od.ax[i]][od.ay[i]] = true;
        }

        boolean[][] connect = new boolean[WIDTH][HEIGHT];
        dfs(id.x[0], id.y[0], WIDTH, HEIGHT, board, connect);
        for (int i = 0; i < id.N; i++) {
            if (!connect[id.x[i]][id.y[i]]) {
                System.err.println("Panels are not connected.");
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

        return od.M;
    }
}
