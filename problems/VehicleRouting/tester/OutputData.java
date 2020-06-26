import java.util.Scanner;

public class OutputData
{
    public int K;
    public int[] T;
    public int[] L;
    public int[][] D;

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
        od.K = sc.nextInt();
        od.T = new int[od.K];
        od.L = new int[od.K];
        od.D = new int[od.K][];
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
}
