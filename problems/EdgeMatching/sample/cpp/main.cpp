#include <iostream>
#include <vector>
#include <tuple>
using namespace std;

class EdgeMatching
{
public:
    auto solve (
        const int N,
        const int C,
        const vector<int> U,
        const vector<int> D,
        const vector<int> L,
        const vector<int> R)
    {
        vector<int> x, y, r;
        
        for (int xt = 0; xt < N; xt++) {
            for (int yt = 0; yt < N; yt++) {
                x.push_back(xt);
                y.push_back(yt);
                r.push_back(0);
            }
        }
         
        return make_tuple(x, y, r);
    }
};

int main ()
{
    int N, C; 
    cin >> N >> C;

    const int PN = N * N;
    vector<int> U(PN), D(PN), L(PN), R(PN);
    for (int i = 0; i < PN; i++) {
        cin >> U[i] >> D[i] >> L[i] >> R[i];
    }

    EdgeMatching em;
    vector<int> x, y, r;
    tie(x, y, r) = em.solve(N, C, U, D, L, R);
    for (int i = 0; i < PN; i++) {
        cout << x[i] << " " << y[i] << " " << r[i] << endl;
    }

    cout.flush();
    
    return 0;
}
