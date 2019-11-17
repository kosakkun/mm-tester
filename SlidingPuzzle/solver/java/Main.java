import java.util.Scanner;

class SlidingPuzzle
{
    public int[][] solve (int N, int[][] B)
    {
        int[][] ret = new int[N * N][2];
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                ret[x * N + y][0] = x;
                ret[x * N + y][1] = y;
            }
        }
        return ret;
    }
}

public class Main
{
    public static void main (String[] args)
    {
        try {
            Scanner sc = new Scanner(System.in);
            int N = sc.nextInt();
            int B[][] = new int[N][N];
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < N; y++) {
                    B[x][y] = sc.nextInt();
                }
            }
            SlidingPuzzle sp = new SlidingPuzzle();
            int[][] ret = sp.solve(N, B);
            System.out.println(ret.length);
            for (int i = 0; i < ret.length; i++) {
                System.out.println(ret[i][0] + " " + ret[i][1]);
            }
            System.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
