import java.util.Scanner;

public class OutputData implements Cloneable
{
    public int K;
    public int[] T;
    public int[] L;
    public int[][] D;

    public OutputData (final int K)
    {
        this.K = K;
        this.T = new int[K];
        this.L = new int[K];
        this.D = new int[K][];
    }

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(K).append('\n');
        for (int i = 0; i < K; i++) {
            sb.append(T[i]).append(' ');
            sb.append(L[i]).append('\n');
            for (int j = 0; j < L[i]; j++) {
                sb.append(D[i][j]);
                sb.append((j < L[i] - 1) ? ' ' : '\n');
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
            od.T = this.T.clone();
            od.L = this.L.clone();
            od.D = new int[this.K][];
            for (int i = 0; i < this.K; i++) {
                od.D[i] = this.D[i].clone();
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
        final int K = sc.nextInt();
        OutputData od = new OutputData(K);

        for (int i = 0; i < od.K; i++) {
            od.T[i] = sc.nextInt();
            od.L[i] = sc.nextInt();
            od.D[i] = new int[od.L[i]];
            for (int j = 0; j < od.L[i]; j++) {
                od.D[i][j] = sc.nextInt();
            }
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}
