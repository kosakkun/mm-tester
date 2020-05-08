public class InputData
{
    public int N;
    public int K;
    public int[] x;
    public int[] y;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(K).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }
        
        return sb.toString();
    }
}
