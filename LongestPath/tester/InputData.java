public class InputData
{
    public int N;
    public int M;
    public int[] x;
    public int[] y;
    public int[] a;
    public int[] b;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        for (int i = 0; i < N; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }
        for (int i = 0; i < M; i++) {
            sb.append(a[i]).append(' ');
            sb.append(b[i]).append('\n');
        }
        
        return sb.toString();
    }
}
