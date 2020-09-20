import java.util.Scanner;

public class OutputData implements Cloneable
{
    public int K;
    public int[] v;

    public OutputData (final int K)
    {
        this.K = K;
        this.v = new int[K];
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(K).append('\n');
        for (int i = 0; i < K; ++i) {
            sb.append(v[i]).append('\n');
        }

        return sb.toString();
    }

    @Override
    public OutputData clone ()
    {
        OutputData od = null;

        try {
            od = (OutputData)super.clone();
            od.v = this.v.clone();
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
        final int K = sc.nextInt();
        OutputData od = new OutputData(K);
        for (int i = 0; i < od.K; i++) {
            od.v[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}
