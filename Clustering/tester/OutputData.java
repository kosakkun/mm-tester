public class OutputData
{
    public int[] cx;
    public int[] cy;

    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cx.length; ++i) {
            sb.append(cx[i]).append(' ');
            sb.append(cy[i]).append('\n');
        }

        return sb.toString();
    }
}
