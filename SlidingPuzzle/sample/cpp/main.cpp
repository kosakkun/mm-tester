#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

class SlidingPuzzle
{
public:
    vector<vector<int>> solve (int N, vector<vector<int>> B)
    {
        vector<vector<int>> ret;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                ret.push_back({x, y});
            }
        }
        return ret;
    }
};

int main ()
{
    int N;
    cin >> N;

    vector<vector<int>> B = vector<vector<int>>(N, vector<int>(N));
    for (int x = 0; x < N; x++) {
        for (int y = 0; y < N; y++) {
            cin >> B[x][y];
        }
    }

    SlidingPuzzle sp;
    vector<vector<int>> ret = sp.solve(N, B);
    cout << ret.size() << endl;
    for (int i = 0; i < ret.size(); i++) {
        cout << ret[i][0] << " " << ret[i][1] << endl;
    }

    cout.flush();

    return 0;
}
