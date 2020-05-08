public class OutputData
{
    public int[] v;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < v.length; ++i) {
            sb.append(v[i]).append('\n');
        }

        return sb.toString();
    }
}
