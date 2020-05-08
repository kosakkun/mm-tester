public class InputData
{
    public int N;
    public int[][] B;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                sb.append(B[x][y]);
                sb.append((y < N - 1) ? ' ' : '\n');
            }
        }

        return sb.toString();
    }
}
