import sys

class Clustering:
    def solve (self, N, K, x, y):
        ret = []
        for i in range(K):
            rx = i // 4 * 200 + 100
            ry = i % 4 * 250 + 100
            ret.append([rx, ry])
        return ret

def main():
    N,K = map(int, input().split())
    x = []
    y = []
    for i in range(N):
        xt,yt = map(int,input().split())
        x.append(xt)
        y.append(yt)
    c = Clustering()
    ret = c.solve(N, K, x, y)
    for p in ret:
        print(' '.join(map(str, p)))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
