public class OutputData
{
    public int K;
    public int[] v;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(K).append('\n');
        for (int i = 0; i < K; ++i) {
            sb.append(v[i]).append('\n');
        }

        return sb.toString();
    }
}
