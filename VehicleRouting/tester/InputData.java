public class InputData
{
    public int N;
    public int M;
    public int depotX;
    public int depotY;
    public int[] x;
    public int[] y;
    public int[] cap;
    public int[] speed;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        sb.append(depotX).append(' ');
        sb.append(depotY).append('\n');
        for (int i = 0; i < N; i++) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n'); 
        }
        for (int i = 0; i < M; i++) {
            sb.append(cap[i]).append(' ');
            sb.append(speed[i]).append('\n'); 
        }

        return sb.toString();
    }
}
