#include <iostream>
#include <vector>
using namespace std;

class PoliticalDistricting
{
public:
    auto solve (
        const int N, 
        const int K,
        const vector<vector<int>> B)
    {
        vector<vector<int>> R(N, vector<int>(N));

        const int size = (N * N + K) / K;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int cnt = r * N + c;
                R[r][(r % 2) ? c : N - 1 - c] = cnt / size;
            }
        }

        return R;
    }
};

int main ()
{
    int N, K; 
    cin >> N >> K;

    vector<vector<int>> B(N, vector<int>(N));
    for (int r = 0; r < N; r++) {
        for (int c = 0; c < N; c++) {
            cin >> B[r][c];
        }
    }

    PoliticalDistricting pd;
    vector<vector<int>> R = pd.solve(N, K, B);
    for (int r = 0; r < N; r++) {
        for (int c = 0; c < N; c++) {
            cout << R[r][c] << (c < N - 1 ? " " : "\n");
        }
    }

    cout.flush();
    
    return 0;
}
