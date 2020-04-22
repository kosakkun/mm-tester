import sys

class RectilinearSteinerTree:
    def solve (self, N, x, y):
        size = 100
        used = set([])
        for i in range(N):
            used.add(x[i] * size + y[i])
        M = size * size - N
        ax = []
        ay = []
        for i in range(size):
            for j in range(size):
                p = i * size + j
                if p not in used:
                    ax.append(i)
                    ay.append(j)
        return M, ax, ay

def main():
    N = int(input())
    x = []
    y = []
    for i in range(N):
        xt,yt = map(int,input().split())
        x.append(xt)
        y.append(yt)

    rst = RectilinearSteinerTree()
    M, ax, ay = rst.solve(N, x, y)
    print(M)
    for i in range(len(ax)):
        print(str(ax[i]) + ' ' + str(ay[i]))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
