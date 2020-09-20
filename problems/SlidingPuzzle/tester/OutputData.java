import java.util.Scanner;

public class OutputData implements Cloneable
{
    public int M;
    public int[] r;
    public int[] c;

    public OutputData (final int M)
    {
        this.M = M;
        this.r = new int[M];
        this.c = new int[M];
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(r[i]).append(' ');
            sb.append(c[i]).append('\n');
        }

        return sb.toString();
    }

    @Override
    public OutputData clone ()
    {
        OutputData od = null;

        try {
            od = (OutputData)super.clone();
            od.r = this.r.clone();
            od.c = this.c.clone();
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
            od.r[i] = sc.nextInt();
            od.c[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}
