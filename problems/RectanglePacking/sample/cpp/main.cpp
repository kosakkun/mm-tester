#include <iostream>
#include <vector>
#include <tuple>
using namespace std;

class RectanglePacking
{
public:
    auto solve (
        const int N,
        const vector<int> w,
        const vector<int> h)
    {
        vector<int> x(N);
        vector<int> y(N);
        for (int i = 0; i < N; i++) {
            x[i] = 50 * (i % 20);
            y[i] = 50 * (i / 20);
        }
        return make_tuple(x, y);
    }
};

int main ()
{
    int N; 
    cin >> N;

    vector<int> h(N),w(N);
    for (int i = 0; i < N; i++) {
        cin >> w[i] >> h[i];
    }

    RectanglePacking rp;
    vector<int> x,y;
    tie(x, y) = rp.solve(N, w, h);
    for (int i = 0; i < (int)x.size(); i++) {
        cout << x[i] << " " << y[i] << endl;
    }

    cout.flush();

    return 0;
}
