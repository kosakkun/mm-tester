public class InputData
{
    public int N;
    public int[] w;
    public int[] h;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(w[i]).append(' ');
            sb.append(h[i]).append('\n');
        }
        
        return sb.toString();
    }
}
