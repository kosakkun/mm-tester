public class OutputData
{
    public int K;
    public int[] T;
    public int[] L;
    public int[][] D;

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
