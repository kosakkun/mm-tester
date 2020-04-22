#include <iostream>
#include <vector>
using namespace std;

class GraphColoring
{
public:
    auto solve (
        const int N,
        const int M,
        const vector<int> a,
        const vector<int> b)
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
    auto ret = gc.solve(N, M, a, b);
    for (auto c: ret) {
        cout << c << endl;
    }

    cout.flush();

    return 0;
}

