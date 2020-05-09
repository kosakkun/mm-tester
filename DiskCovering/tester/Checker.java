import java.util.Scanner;

public class Checker
{
    static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        for (int i = 0; i < od.M; i++) {
            if (od.xr[i] < 0 || od.yr[i] < 0 ||
                od.xr[i] > Generator.MAX_X || od.yr[i] > Generator.MAX_Y) {
                System.err.println(
                    "The coordinates of the disk must satisfy 0 <= xi,yi " + 
                    "<= 1000, but your output is the coordinates of the " +
                    i + " disk is (" + od.xr[i] + "," + od.yr[i] + ").");
                return false;
            }
        }

        for (int i = 0; i < id.N; i++) {
            boolean flag = false;
            for (int j = 0; j < od.M; j++) {
                int dx = Math.abs(id.xp[i] - od.xr[j]);
                int dy = Math.abs(id.yp[i] - od.yr[j]);
                if (dx * dx + dy * dy <= id.R * id.R) {
                    flag = true;
                }
            }
            if (!flag) {
                System.err.println(
                    "Point at coordinate x = " + id.xp[i] + ", y = " + id.yp[i] +
                    " is not covered by a disk.");
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
        od.xr = new int[od.M];
        od.yr = new int[od.M];
        
        for (int i = 0; i < od.M; i++) {
            od.xr[i] = sc.nextInt();
            od.yr[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}
