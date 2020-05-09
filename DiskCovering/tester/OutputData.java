public class OutputData
{
    public int M;
    public int[] xr;
    public int[] yr;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(xr[i]).append(' ');
            sb.append(yr[i]).append('\n');
        }

        return sb.toString();
    }
}
