#include <iostream>
#include <vector>
#include <map>
using namespace std;

class Hiroimono
{
public:
    auto solve (
        const int N, 
        const vector<int> x,
        const vector<int> y)
    {
        int M = 1;
        vector<int> v = {0};

        return make_pair(M, v);
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

    Hiroimono h;
    auto [M, v] = h.solve(N, x, y);
    
    cout << M << endl;
    for (auto vt: v) {
        cout << vt << endl;
    }

    cout.flush();
    
    return 0;
}
