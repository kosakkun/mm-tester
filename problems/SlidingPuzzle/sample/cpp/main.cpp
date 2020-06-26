#include <iostream>
#include <vector>
#include <cmath>
#include <tuple>
using namespace std;

class SlidingPuzzle
{
public:
    auto solve (
        const int N,
        const vector<vector<int>> B)
    {
        int M = N * N;
        vector<int> r;
        vector<int> c;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                r.push_back(x);
                c.push_back(y);
            }
        }
        return make_tuple(M, r, c);
    }
};

int main ()
{
    int N;
    cin >> N;

    vector<vector<int>> B(N, vector<int>(N));
    for (int x = 0; x < N; x++) {
        for (int y = 0; y < N; y++) {
            cin >> B[x][y];
        }
    }

    SlidingPuzzle sp;
    int M;
    vector<int> r,c;
    tie(M, r, c) = sp.solve(N, B);
    cout << M << endl;
    for (int i = 0; i < M; i++) {
        cout << r[i] << " " << c[i] << endl;
    }

    cout.flush();

    return 0;
}
