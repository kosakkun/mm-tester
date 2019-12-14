#include <iostream>
#include <vector>
using namespace std;

class TravelingSalesman
{
public:
    vector<int> solve (int N, vector<int> x, vector<int> y)
    {
        vector<int> ret(N);
        for (int i = 0; i < N; i++) {
            ret[i] = i;
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

    TravelingSalesman ts;
    vector<int> ret = ts.solve(N, x, y);
    for (int i = 0; i < ret.size(); i++) {
        cout << ret[i] << endl;
    }

    cout.flush();
    
    return 0;
}
