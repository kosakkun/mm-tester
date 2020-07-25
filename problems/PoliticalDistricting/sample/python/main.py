import sys

class PoliticalDistricting:
    def solve (self, N, K, B):
        R = [[0] * N for i in range(N)]
        size = (N * N + K) // K;
        for r in range(N):
            for c in range(N):
                num = r * N + c
                R[r][c if r % 2 == 1 else N - 1 - c] = num // size
        return R

def main():
    N,K = map(int,input().split())
    B = []
    for r in range(N):
        l = map(int,input().split())
        B.append(l)
    pd = PoliticalDistricting()
    R = pd.solve(N, K, B)
    for r in range(N):
        print(' '.join(map(str, R[r])))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
