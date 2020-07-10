import java.security.SecureRandom;

public class InputData
{
    public static final int FIXED_N = 200;

    public int N;
    public int M;
    public int[] a;
    public int[] b;

    public static InputData genInputData (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        InputData id = new InputData();
        id.N = FIXED_N;
        id.M = rnd.nextInt(id.N * (id.N - 1) / 4 - 2 * id.N) + 2 * id.N;
        id.a = new int[id.M];
        id.b = new int[id.M];
        
        int esum = 0;
        boolean[][] edge = new boolean[id.N][id.N];
        while (esum < id.M) {
            int at = rnd.nextInt(id.N);
            int bt = rnd.nextInt(id.N);
            if (at == bt || edge[at][bt]) {
                continue;
            }
            edge[at][bt] = true;
            edge[bt][at] = true;
            id.a[esum] = at;
            id.b[esum] = bt;
            esum++;
        }

        return id;
    }
    
    @Override
    public String toString ()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(N).append(' ');
        sb.append(M).append('\n');
        for (int i = 0; i < M; ++i) {
            sb.append(a[i]).append(' ');
            sb.append(b[i]).append('\n');
        }
        
        return sb.toString();
    }
}
