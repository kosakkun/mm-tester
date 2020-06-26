#include <iostream>
#include <vector>
#include <set>
#include <tuple>
using namespace std;

class RectilinearSteinerTree
{
public:
    auto solve (
        const int N,
        const vector<int> x,
        const vector<int> y)
    {
        set<pair<int,int>> used;
        for (int i = 0; i < N; i++) {
            used.insert(make_pair(x[i], y[i]));
        }
        const int size = 100;
        int M = size * size - N;
        vector<int> ax;
        vector<int> ay;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (used.find(make_pair(i, j)) == used.end()) {
                    ax.push_back(i);
                    ay.push_back(j);
                }
            }
        }
        return make_tuple(M, ax, ay);
    }
};

int main ()
{
    int N;
    cin >> N;

    vector<int> x(N), y(N);
    for (int i = 0; i < N; i++) {
        cin >> x[i] >> y[i];
    }

    RectilinearSteinerTree rst;
    int M;
    vector<int> ax,ay;
    tie(M, ax, ay) = rst.solve(N, x, y);
    cout << M << endl;
    for (int i = 0; i < M; i++) {
        cout << ax[i] << " " << ay[i] << endl;
    }

    cout.flush();

    return 0;
}
