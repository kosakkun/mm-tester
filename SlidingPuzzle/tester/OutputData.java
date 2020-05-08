public class OutputData
{
    public int M;
    public int[] r;
    public int[] c;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(r[i]).append(' ');
            sb.append(c[i]).append('\n');
        }

        return sb.toString();
    }
}
