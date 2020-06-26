import java.util.Scanner;

public class OutputData
{
    public int M;
    public int[] v;

    public static OutputData runCommand (
        final String exec,
        final InputData id)
        throws Exception
    {
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();
        proc.getOutputStream().write(id.toString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());

        OutputData od = new OutputData();
        od.M = sc.nextInt();
        od.v = new int[od.M];

        for (int i = 0; i < od.M; i++) {
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
        sb.append(M).append('\n');
        for (int i = 0; i < v.length; ++i) {
            sb.append(v[i]).append('\n');
        }

        return sb.toString();
    }
}
