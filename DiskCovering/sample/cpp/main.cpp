#include <iostream>
#include <vector>
using namespace std;

class DiskCovering
{
public:
    auto solve (
        const int N,
        const int R,
        const vector<int> x,
        const vector<int> y)
    {
        int M = N;
        vector<int> rx = x;
        vector<int> ry = y;
        return make_tuple(M, rx, ry);
    }
};

int main ()
{
    int N,R;
    cin >> N >> R;
    
    vector < int > x(N),y(N);
    for (int i = 0; i < N; i++) {
        cin >> x[i] >> y[i];
    }

    DiskCovering dc;
    auto [M, rx, ry] = dc.solve(N, R, x, y);
    cout << M << endl;
    for (int i = 0; i < M; i++) {
        cout << rx[i] << " " << ry[i] << endl;
    }

    cout.flush();

    return 0;
}
