import java.util.Scanner;

public class OutputData
{
    public int M;
    public int[] xr;
    public int[] yr;

    public static OutputData runCommand (
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

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(xr[i]).append(' ');
            sb.append(yr[i]).append('\n');
        }

        return sb.toString();
    }
}
