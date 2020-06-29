import java.util.Scanner;

public class OutputData
{
    public static final int MAX_M = 1000;
    public static final int MIN_M = 0;
    
    public int M;
    public int[] ax;
    public int[] ay;

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
}
