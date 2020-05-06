#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

class SlidingPuzzle
{
public:
    auto solve (
        const int N,
        const vector<vector<int>> B)
    {
        int M = N * N;
        vector<int> r(M);
        vector<int> c(M);
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                r[x * N + y] = x;
                c[x * N + y] = y;
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
    auto [M, r, c] = sp.solve(N, B);
    cout << M << endl;
    for (int i = 0; i < M; i++) {
        cout << r[i] << " " << c[i] << endl;
    }

    cout.flush();

    return 0;
}
