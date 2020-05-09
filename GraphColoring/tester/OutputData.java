public class OutputData
{
    public int[] c;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < c.length; ++i) {
            sb.append(c[i]).append('\n');
        }

        return sb.toString();
    }
}
