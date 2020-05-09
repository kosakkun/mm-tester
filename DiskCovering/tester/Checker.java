public class Checker
{
    public static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        for (int i = 0; i < od.M; i++) {
            if (od.xr[i] < 0 || od.yr[i] < 0 ||
                od.xr[i] > InputData.MAX_X || od.yr[i] > InputData.MAX_Y) {
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
