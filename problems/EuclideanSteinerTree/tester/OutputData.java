import java.util.Scanner;

public class OutputData implements Cloneable
{
    public static final int MAX_M = 1000;
    public static final int MIN_M = 0;
    
    public int M;
    public int[] ax;
    public int[] ay;

    public OutputData (final int M)
    {
        this.M = M;
        this.ax = new int[M];
        this.ay = new int[M];
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(ax[i]).append(' ');
            sb.append(ay[i]).append('\n');
        }
        
        return sb.toString();
    }

    @Override
    public OutputData clone ()
    {
        OutputData od = null;

        try {
            od = (OutputData)super.clone();
            od.ax = this.ax.clone();
            od.ay = this.ay.clone();
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
            od.ax[i] = sc.nextInt();
            od.ay[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}
