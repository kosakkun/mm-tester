#include <iostream>
#include <vector>
using namespace std;

class EuclideanSteinerTree
{
public:
    vector<vector<int>> solve (int N, vector<int> x, vector<int> y)
    {
        vector<vector<int>> ret;
        ret.push_back({500, 500});
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
    
    EuclideanSteinerTree est;
    vector<vector<int>> ret = est.solve(N, x, y);
    cout << ret.size() << endl;
    for (int i = 0; i < ret.size(); i++) {
        cout << ret[i][0] << " " << ret[i][1] << endl;
    }
    
    cout.flush();

    return 0;
}
