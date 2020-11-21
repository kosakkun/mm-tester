#include <iostream>
#include <vector>
using namespace std;

class TravelingSalesman
{
public:
    auto solve (
        const int N, 
        const vector<vector<int>> c)
    {
        vector<int> v(N);
        
        for (int i = 0; i < N; i++) {
            v[i] = i;
        }

        return v;
    }
};

int main ()
{
    int N; 
    cin >> N;

    vector<vector<int>> c(N, vector<int>(N, 0));
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cin >> c[i][j];
        }
    }

    TravelingSalesman ts;
    vector<int> v = ts.solve(N, c);
    for (auto vt: v) {
        cout << vt << endl;
    }

    cout.flush();
    
    return 0;
}
