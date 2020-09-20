import java.util.Scanner;

public class OutputData implements Cloneable
{
    public int[][] R;

    public OutputData (final int N)
    {
        R = new int[N][N];
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int r = 0; r < R.length; r++) {
            for (int c = 0; c < R[r].length; c++) {
                sb.append(R[r][c]);
                sb.append((c < R[r].length - 1) ? ' ' : '\n');
            }
        }

        return sb.toString();
    }

    @Override
    public OutputData clone ()
    {
        OutputData od = null;

        try {
            od = (OutputData)super.clone();
            od.R = new int[this.R.length][];
            for (int i = 0; i < this.R.length; i++) {
                od.R[i] = this.R[i].clone();
            }
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
        OutputData od = new OutputData(id.N);
        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N; c++) {
                od.R[r][c] = sc.nextInt();
            }
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}
