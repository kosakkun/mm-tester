import java.util.Scanner;

public class OutputData
{
    public static final int BOX_SIZE = 1000;

    public int[] x;
    public int[] y;

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

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < x.length; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }

        return sb.toString();
    }
}
