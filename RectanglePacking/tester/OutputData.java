public class OutputData
{
    public int[] x;
    public int[] y;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < x.length; ++i) {
            sb.append(x[i]).append(' ');
            sb.append(y[i]).append('\n');
        }

        return sb.toString();
    }
}
