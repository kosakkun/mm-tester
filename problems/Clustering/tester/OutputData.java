import java.util.Scanner;

public class OutputData implements Cloneable
{
    public int[] cx;
    public int[] cy;

    public OutputData (final int K)
    {
        cx = new int[K];
        cy = new int[K];
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cx.length; ++i) {
            sb.append(cx[i]).append(' ');
            sb.append(cy[i]).append('\n');
        }

        return sb.toString();
    }

    @Override
    public OutputData clone ()
    {
        OutputData od = null;

        try {
            od = (OutputData)super.clone();
            od.cx = this.cx.clone();
            od.cy = this.cy.clone();
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
        OutputData od = new OutputData(id.K);
        for (int i = 0; i < id.K; i++) {
            od.cx[i] = sc.nextInt();
            od.cy[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}
