public class InputData
{
    public int N;
    public int R;
    public int[] xp;
    public int[] yp;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(R).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(xp[i]).append(' ');
            sb.append(yp[i]).append('\n');
        }
        
        return sb.toString();
    }
}
