import java.util.Scanner;

public class OutputData
{
    public int M;
    public int[] r;
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
        od.M = sc.nextInt();
        od.r = new int[od.M];
        od.c = new int[od.M];
        for (int i = 0; i < od.M; i++) {
            od.r[i] = sc.nextInt();
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
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(r[i]).append(' ');
            sb.append(c[i]).append('\n');
        }

        return sb.toString();
    }
}
