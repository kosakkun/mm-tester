import sys

class LongestPath:
    def solve (self, N, M, x, y, a, b):
        ret = [a[0], b[0]]
        return ret

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
    ret = lp.solve(N, M, x, y, a, b)
    print(len(ret))
    for v in ret:
        print(v)
    sys.stdout.flush()

if __name__ == '__main__':
    main()
