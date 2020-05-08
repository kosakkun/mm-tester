public class InputData
{
    public int N;
    public int[] x;
    public int[] y;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }

        return sb.toString();
    }
}
