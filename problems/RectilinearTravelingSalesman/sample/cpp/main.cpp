#include <iostream>
#include <vector>
using namespace std;

class RectilinearTravelingSalesman
{
public:
    auto solve (
        const int N, 
        const vector<int> x,
        const vector<int> y)
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

    vector<int> x(N), y(N);
    for (int i = 0; i < N; i++) {
        cin >> x[i] >> y[i];
    }

    RectilinearTravelingSalesman rts;
    vector<int> v = rts.solve(N, x, y);
    for (auto vt: v) {
        cout << vt << endl;
    }

    cout.flush();
    
    return 0;
}
