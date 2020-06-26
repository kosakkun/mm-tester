#include <iostream>
#include <vector>
#include <tuple>
using namespace std;

class Clustering
{
public:
    auto solve (
        const int N,
        const int K,
        const vector<int> x,
        const vector<int> y)
    {
        vector<int> cx(K);
        vector<int> cy(K);

        for (int i = 0; i < K; i++) {
            cx[i] = (i / 4) * 200 + 100;
            cy[i] = (i % 4) * 250 + 100;
        }
        return make_tuple(cx, cy);
    }
};

int main ()
{
    int N,K;
    cin >> N >> K;
    
    vector < int > x(N), y(N);
    for (int i = 0; i < N; i++) {
        cin >> x[i] >> y[i];
    }

    Clustering c;
    vector<int> cx,cy;
    tie(cx, cy) = c.solve(N, K, x, y);
    for (int i = 0; i < (int)cx.size(); i++) {
        cout << cx[i] << " " << cy[i] << endl;
    }

    cout.flush();

    return 0;
}
