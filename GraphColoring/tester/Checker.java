import java.util.Scanner;

public class Checker
{
    static boolean isValid (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        for (int i = 0; i < id.M; i++) {
            if (od.c[id.a[i]] == od.c[id.b[i]]) {
                System.err.println(
                    "Vertex " + id.a[i] + " and vertex " + id.b[i] +
                    " are connected, but your output is the same color.");
                return false;
            }
        }

        for (int i = 0; i < id.N; i++) {
            if (od.c[i] < 0 || od.c[i] >= id.N) {
                System.err.println(
                    "The vertex color must be a number between 0 and " + 
                    (id.N - 1) + ", but your output includes " + od.c[i] + ".");
                return false;
            }
        }

        return true;
    }

    static int calcScore (
        final InputData id,
        final OutputData od)
        throws Exception
    {
        if (!isValid(id, od)) {
            return -1;
        }

        int cnum = 0;
        boolean[] used = new boolean[id.N];

        for (int i = 0; i < id.N; i++) {
            if (!used[od.c[i]]) {
                used[od.c[i]] = true;
                cnum++;
            }
        }
        
        return cnum;
    }

    static OutputData runCommand (
        final String exec,
        final InputData id)
        throws Exception
    {
        Process proc = Runtime.getRuntime().exec(exec);
        new ErrorReader(proc.getErrorStream()).start();
        proc.getOutputStream().write(id.toString().getBytes());
        proc.getOutputStream().flush();
        Scanner sc = new Scanner(proc.getInputStream());

        OutputData od = new OutputData();
        od.c = new int[id.N];
        for (int i = 0; i < id.N; i++) {
            od.c[i] = sc.nextInt();
        }

        if (proc != null) {
            proc.destroy();
        }

        return od;
    }
}
