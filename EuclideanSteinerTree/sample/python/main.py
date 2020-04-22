import sys

class EuclideanSteinerTree:
    def solve (self, N, x, y):
        M = 1
        ax = [500]
        ay = [500]
        return M, ax, ay;

def main():
    N = int(input())
    x = [];
    y = [];
    for i in range(N):
        xt,yt = map(int,input().split())
        x.append(xt)
        y.append(yt)

    est = EuclideanSteinerTree()
    M, ax, ay = est.solve(N, x, y)
    print(M)
    for i in range(M):
        print(str(ax[i]) + ' ' + str(ay[i]))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
