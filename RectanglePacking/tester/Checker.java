import java.util.Scanner;

public class Checker
{
    public static final int BOX_SIZE = 1000;

    static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        boolean[][] used = new boolean[BOX_SIZE][BOX_SIZE];
        for (int i = 0; i < id.N; i++) {
            for (int xt = od.x[i]; xt < od.x[i] + id.w[i]; xt++) {
                for (int yt = od.y[i]; yt < od.y[i] + id.h[i]; yt++) {
                    if (xt < 0 || yt < 0 || xt >= BOX_SIZE || yt >= BOX_SIZE) {
                        System.err.println(
                            "There is a rectangle which does not fit in the box.");
                        return false;
                    }
                    if (used[xt][yt]) {
                        System.err.println("There are overlapping rectangles.");
                        System.err.println("" + xt + " " + yt + ": idx = " + i);
                        return false;
                    }
                    used[xt][yt] = true;
                }
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

        int max_y = 0;
        for (int i = 0; i < id.N; i++) {
            max_y = Math.max(max_y, od.y[i] + id.h[i]);
        }

        return max_y;
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
        od.x = new int[id.N];
        od.y = new int[id.N];
        
        for (int i = 0; i < id.N; i++) {
            od.x[i] = sc.nextInt();
            od.y[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}
