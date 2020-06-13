#include <iostream>
#include <vector>
#include <cmath>
#include <tuple>
using namespace std;

class VehicleRouting
{
public:
    auto solve (
        const int N,
        const int M,
        const int depotX,
        const int depotY,
        const vector<int> x,
        const vector<int> y,
        const vector<int> cap,
        const vector<int> speed)
    {
        int K = 0;
        vector<int> T;
        vector<int> L;
        vector<vector<int>> D;
        
        int rm = N;
        while (rm > 0) {
            K++;
            T.push_back(0);
            L.push_back(min(rm, cap[T.back()]));
            vector<int> Di;
            for (int i = 0; i < L.back(); i++) {
                Di.push_back(--rm);
            }
            D.push_back(Di);
        }
        return make_tuple(K, T, L, D);
    }
};

int main ()
{
    int N,M; 
    cin >> N >> M;

    int depotX, depotY;
    cin >> depotX >> depotY;

    vector<int> x(N), y(N);
    for (int i = 0; i < N; i++) {
        cin >> x[i] >> y[i];
    }

    vector<int> cap(M), speed(M);
    for (int i = 0; i < M; i++) {
        cin >> cap[i] >> speed[i];
    }

    VehicleRouting vr;
    int K;
    vector<int> T,L;
    vector<vector<int>> D;
    tie(K, T, L, D) = vr.solve(N, M, depotX, depotY, x, y, cap, speed);
    cout << K << '\n';
    for (int i = 0; i < K; i++) {
        cout << T[i] << ' ' << L[i] << '\n';
        for (int j = 0; j < L[i]; j++) {
            cout << D[i][j] << (j < L[i] - 1 ? ' ' : '\n');
        }
    }

    cout.flush();

    return 0;
}
