import java.util.Arrays;

public class DisjointSet
{
    private int n;
    private int[] node;

    public DisjointSet (int _n)
    {
        n = _n;
        node = new int[_n];
        Arrays.fill(node, -1);
    }

    public boolean unite (int x, int y)
    {
        x = root(x);
        y = root(y);
        if (x != y) {
            if (node[y] < node[x]) {
                int tmp = x;
                x = y;
                y = tmp;
            }
            node[x] += node[y]; 
            node[y] = x;
        }
        return x != y;
    }

    public boolean same (int x, int y)
    {
        return root(x) == root(y);
    }

    public int root (int x)
    {
        if (node[x] < 0) return x;
        return node[x] = root(node[x]);
    }

    public int size (int x)
    {
        return -node[root(x)];
    }
    
};
