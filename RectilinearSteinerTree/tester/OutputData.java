public class OutputData
{
    public int M;
    public int[] ax;
    public int[] ay;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(ax[i]).append(' ');
            sb.append(ay[i]).append('\n');
        }
        
        return sb.toString();
    }
}
