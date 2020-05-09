import java.security.SecureRandom;

public class Generator
{
    public static final int N = 100;

    public static InputData genInput (
        final long seed)
        throws Exception
    {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        InputData id = new InputData();
        id.N = N;
        id.M = rnd.nextInt(N * (N - 1) / 4 - 2 * N) + 2 * N;
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
}
