#include <iostream>
#include <vector>
#include <set>
using namespace std;

class RectilinearSteinerTree
{
public:
    vector<vector<int>> solve (int N, vector<int> x, vector<int> y)
    {
        set<vector<int>> used;
        for (int i = 0; i < N; i++) {
            used.insert({x[i], y[i]});
        }
        const int size = 100;
        vector<vector<int>> ret;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                vector<int> p = {i, j};
                if (used.find(p) == used.end()) {
                    ret.push_back(p);
                }
            }
        }
        return ret;
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
    vector<vector<int>> ret = rst.solve(N, x, y);
    cout << ret.size() << endl;
    for (int i = 0; i < ret.size(); i++) {
        cout << ret[i][0] << " " << ret[i][1] << endl;
    }

    cout.flush();

    return 0;
}
