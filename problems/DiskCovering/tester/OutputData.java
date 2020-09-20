import java.util.Scanner;

public class OutputData implements Cloneable
{
    public int M;
    public int[] xr;
    public int[] yr;

    public OutputData (final int M)
    {
        this.M = M;
        this.xr = new int[M];
        this.yr = new int[M];
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

    @Override
    public OutputData clone ()
    {
        OutputData od = null;

        try {
            od = (OutputData)super.clone();
            od.xr = this.xr.clone();
            od.yr = this.yr.clone();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return od;
    }

    public static OutputData runCommand (
        final String exec,
        final InputData id)
        throws Exception
    {
        Process proc = Runtime.getRuntime().exec(exec);
        Scanner sc = new Scanner(proc.getInputStream());
        new ErrorReader(proc.getErrorStream()).start();

        /* Input to the "<command>". */
        proc.getOutputStream().write(id.toString().getBytes());
        proc.getOutputStream().flush();
        
        /* Output from the "<command>". */
        final int M = sc.nextInt();
        OutputData od = new OutputData(M);
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
