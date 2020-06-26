#include <iostream>
#include <vector>
#include <tuple>
using namespace std;

class LongestPath
{
public:
    auto solve (
        const int N,
        const int M,
        const vector<int> x,
        const vector<int> y,
        const vector<int> a,
        const vector<int> b)
    {
        int K = 2;
        vector<int> v = {a[0], b[0]};
        return make_tuple(K, v);
    }
};

int main ()
{
    int N,M;
    cin >> N >> M;

    vector < int > x(N),y(N);
    for (int i = 0; i < N; i++) {
        cin >> x[i] >> y[i];
    }

    vector < int > a(M),b(M);
    for (int i = 0; i < M; i++) {
        cin >> a[i] >> b[i];
    }

    LongestPath lp;
    int K;
    vector<int> v;
    tie(K, v) = lp.solve(N, M, x, y, a, b);
    cout << K << endl;
    for (int i = 0; i < K; i++) {
        cout << v[i] << endl;
    }

    cout.flush();

    return 0;
}
