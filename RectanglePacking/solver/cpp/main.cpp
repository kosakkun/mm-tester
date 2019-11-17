#include <iostream>
#include <vector>
using namespace std;

class RectanglePacking
{
public:
    vector<vector<int>> solve (int N, vector<int> w, vector<int> h)
    {
        vector<vector<int>> ret(N);
        for (int i = 0; i < N; i++) {
            ret[i].push_back(100 * (i % 10));
            ret[i].push_back(100 * (i / 10));
        }
        return ret;
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
    vector<vector<int>> ret = rp.solve(N, w, h);
    for (int i = 0; i < ret.size(); i++) {
        cout << ret[i][0] << " " << ret[i][1] << endl;
    }

    cout.flush();

    return 0;
}
