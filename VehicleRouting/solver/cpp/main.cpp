#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

class VehicleRouting
{
public:
    vector<vector<int>> solve (int N, int M, int depotX, int depotY,
        vector<int> x, vector<int> y, vector<int> cap, vector<int> speed)
    {
        vector<vector<int>> ret;
        while (N > 0) {
            int T = 0;
            int L = min(N, cap[T]);
            vector<int> truck = {T, L};
            vector<int> D;
            for (int i = 0; i < L; i++) {
                D.push_back(--N);
            }
            ret.push_back(truck);
            ret.push_back(D);
        }
        return ret;
    }
};

int main ()
{
    int N,M; 
    cin >> N >> M;

    int depotX, depotY;
    vector < int > x(N), y(N);
    vector < int > cap(M), speed(M);

    cin >> depotX >> depotY;
    for (int i = 0; i < N; i++) {
        cin >> x[i] >> y[i];
    }
    for (int i = 0; i < M; i++) {
        cin >> cap[i] >> speed[i];
    }

    VehicleRouting vr;
    vector<vector<int>> ret = vr.solve(N, M, depotX, depotY, x, y, cap, speed);
    cout << ret.size() / 2 << endl;
    for (int i = 0; i < ret.size(); i++) {
        for (int j = 0; j < ret[i].size(); j++) {
            cout << ret[i][j] << (j < (int)ret[i].size() - 1 ? ' ' : '\n');
        }
    }

    cout.flush();

    return 0;
}
