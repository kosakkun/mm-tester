import java.util.Scanner;

public class Checker
{
    public static final int[] dx = {1, 0, -1, 0};
    public static final int[] dy = {0, 1, 0, -1};
    private static boolean[][] board;
    private static boolean[][] connect;

    private static void dfs (
        final int xt,
        final int yt,
        final int wl,
        final int hl)
    {
        if (connect[xt][yt]) return;
        connect[xt][yt] = true;
        for (int i = 0; i < 4; i++) {
            int nx = xt + dx[i];
            int ny = yt + dy[i];
            if (nx < 0 || ny < 0 || nx >= wl || ny >= hl) continue;
            if (!board[nx][ny]) continue;
            if (connect[nx][ny]) continue;  
            dfs(nx, ny, wl, hl);
        }
    }

    static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        final int WIDTH  = Generator.MAX_X + 1;
        final int HEIGHT = Generator.MAX_Y + 1;

        board = new boolean[WIDTH][HEIGHT];
        for (int i = 0; i < id.N; i++) {
            board[id.x[i]][id.y[i]] = true;
        }

        for (int i = 0; i < od.M; i++) {
            if (od.ax[i] < 0 || od.ay[i] < 0 ||
                od.ax[i] >= WIDTH || od.ay[i] >= HEIGHT) {
                System.err.println(
                    "The panel coordinates must be 0 <= xi <= " + Generator.MAX_X + 
                    ", 0 <= yi <= " + Generator.MAX_Y + ", but your output contains ("
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

        connect = new boolean[WIDTH][HEIGHT];
        dfs(id.x[0], id.y[0], WIDTH, HEIGHT);
        for (int i = 0; i < id.N; i++) {
            if (!connect[id.x[i]][id.y[i]]) {
                System.err.println("Panels are not connected.");
                return false;
            }
        }
        
        return true;
    }

    static int calcScore (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        if (!isValid(id, od)) {
            return -1;
        }

        return od.M;
    }

    static OutputData runCommand (
        final String exec,
        final InputData id)
        throws Exception
    {
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();
        proc.getOutputStream().write(id.toString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());

        OutputData od = new OutputData();
        od.M = sc.nextInt();
        od.ax = new int[od.M];
        od.ay = new int[od.M];
        
        for (int i = 0; i < od.M; i++) {
            od.ax[i] = sc.nextInt();
            od.ay[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}
