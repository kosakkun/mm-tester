#include <iostream>
#include <vector>
using namespace std;

class Clustering
{
public:
    vector<vector<int>> solve (int N, int K, vector<int> x, vector<int> y)
    {
        vector<vector<int>> ret;
        for (int i = 0; i < K; i++) {
            ret.push_back({(i / 4) * 200 + 100, (i % 4) * 250 + 100});
        }
        return ret;
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
    vector<vector<int>> ret = c.solve(N, K, x, y);
    for (int i = 0; i < ret.size(); i++) {
        cout << ret[i][0] << " " << ret[i][1] << endl;
    }

    cout.flush();

    return 0;
}
