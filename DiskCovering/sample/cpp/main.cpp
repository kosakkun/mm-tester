#include <iostream>
#include <vector>
using namespace std;

class DiskCovering
{
public:
    vector<vector<int>> solve (int N, int R, vector<int> x, vector<int> y)
    {
        vector<vector<int>> ret(N);
        for (int i = 0; i < N; i++) {
            ret[i] = {x[i], y[i]};
        }
        return ret;
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
    vector<vector<int>> ret = dc.solve(N, R, x, y);
    cout << ret.size() << endl;
    for (int i = 0; i < ret.size(); i++) {
        cout << ret[i][0] << " " << ret[i][1] << endl;
    }

    cout.flush();

    return 0;
}
