import java.util.Scanner;

public class OutputData
{
    public int[] x;
    public int[] y;
    public int[] r;

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
        final int PN = id.N * id.N;
        od.x = new int[PN];
        od.y = new int[PN];
        od.r = new int[PN];
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
}
