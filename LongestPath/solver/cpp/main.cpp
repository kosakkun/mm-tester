#include <iostream>
#include <vector>
using namespace std;

class LongestPath
{
public:
    vector<int> solve (int N, int M, vector<int> x, vector<int> y, vector<int> a, vector<int> b)
    {
        vector<int> ret;
        ret.push_back(a[0]);
        ret.push_back(b[0]);
        return ret;
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
    vector<int> ret = lp.solve(N, M, x, y, a, b);
    cout << ret.size() << endl;
    for (int i = 0; i < ret.size(); i++) {
        cout << ret[i] << endl;
    }

    cout.flush();

    return 0;
}
