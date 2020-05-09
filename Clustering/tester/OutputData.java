import java.util.Scanner;

public class OutputData
{
    public int[] cx;
    public int[] cy;

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
        od.cx = new int[id.K];
        od.cy = new int[id.K];
        for (int i = 0; i < id.K; i++) {
            od.cx[i] = sc.nextInt();
            od.cy[i] = sc.nextInt();
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
        for (int i = 0; i < cx.length; ++i) {
            sb.append(cx[i]).append(' ');
            sb.append(cy[i]).append('\n');
        }

        return sb.toString();
    }
}
