import sys

class LongestPath:
    def solve (self, N, M, x, y, a, b):
        K = 2
        v = [a[0], b[0]]
        return K, v

def main():
    N,M = map(int, input().split())
    x = []
    y = []
    a = []
    b = []
    for i in range(N):
        xt,yt = map(int,input().split())
        x.append(xt)
        y.append(yt)
    for i in range(M):
        at,bt = map(int,input().split())
        a.append(at)
        b.append(bt)

    lp = LongestPath()
    K, v = lp.solve(N, M, x, y, a, b)
    print(K)
    for i in v:
        print(i)
    sys.stdout.flush()

if __name__ == '__main__':
    main()
