#include <iostream>
#include <vector>
using namespace std;

class EuclideanSteinerTree
{
public:
    auto solve (
        const int N,
        const vector<int> x,
        const vector<int> y)
    {
        int M = 1;
        vector<int> ax = {500};
        vector<int> ay = {500};
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
    
    EuclideanSteinerTree est;
    auto [M, ax, ay] = est.solve(N, x, y);
    cout << M << endl;
    for (int i = 0; i < M; i++) {
        cout << ax[i] << " " << ay[i] << endl;
    }
    
    cout.flush();

    return 0;
}
