import java.util.Scanner;

public class OutputData implements Cloneable
{
    public int[] x;
    public int[] y;
    public int[] r;

    public OutputData (final int N)
    {
        this.x = new int[N];
        this.y = new int[N];
        this.r = new int[N];
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < x.length; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append(' ');
            sb.append(r[i]).append('\n');
        }

        return sb.toString();
    }

    @Override
    public OutputData clone ()
    {
        OutputData od = null;

        try {
            od = (OutputData)super.clone();
            od.x = this.x.clone();
            od.y = this.y.clone();
            od.r = this.r.clone();
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
        final int PN = id.N * id.N;
        OutputData od = new OutputData(PN);
        for (int i = 0; i < PN; i++) {
            od.x[i] = sc.nextInt();
            od.y[i] = sc.nextInt();
            od.r[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}
