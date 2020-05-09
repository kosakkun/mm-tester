public class OutputData
{
    public static final int MAX_M = 1000;
    public static final int MIN_M = 0;
    
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
