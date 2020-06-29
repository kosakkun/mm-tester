import java.util.Scanner;

public class OutputData
{
    public int[] c;

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
        od.c = new int[id.N];
        for (int i = 0; i < id.N; i++) {
            od.c[i] = sc.nextInt();
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
        for (int i = 0; i < c.length; ++i) {
            sb.append(c[i]).append('\n');
        }

        return sb.toString();
    }
}
