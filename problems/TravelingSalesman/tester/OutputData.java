import java.util.Scanner;

public class OutputData
{
    public int[] v;

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
        od.v = new int[id.N];
        for (int i = 0; i < id.N; i++) {
            od.v[i] = sc.nextInt();
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
        for (int i = 0; i < v.length; ++i) {
            sb.append(v[i]).append('\n');
        }

        return sb.toString();
    }
}
