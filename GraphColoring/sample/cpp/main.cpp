#include <iostream>
#include <vector>
using namespace std;

class GraphColoring
{
public:
    vector<int> solve (int N, int M, vector<int> a, vector<int> b)
    {
        vector<int> ret(N);
        for (int i = 0; i < N; i++) {
            ret[i] = i;
        }
        return ret;
    }
};

int main ()
{
    int N,M;
    cin >> N >> M;

    vector < int > a(M),b(M);
    for (int i = 0; i < M; i++) {
        cin >> a[i] >> b[i];
    }

    GraphColoring gc;
    vector<int> ret = gc.solve(N, M, a, b);
    for (int i = 0; i < ret.size(); i++) {
        cout << ret[i] << endl;
    }

    cout.flush();

    return 0;
}

