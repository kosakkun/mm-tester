import sys

class RectilinearSteinerTree:
    def solve (self, N, x, y):
        size = 100
        used = set([])
        for i in range(N):
            used.add(x[i] * size + y[i])
        ret = []
        for i in range(size):
            for j in range(size):
                p = i * size + j
                if p not in used:
                    ret.append([i, j])
        return ret

def main():
    N = int(input())
    x = []
    y = []
    for i in range(N):
        xt,yt = map(int,input().split())
        x.append(xt)
        y.append(yt)
    rst = RectilinearSteinerTree()
    ret = rst.solve(N, x, y)
    print(len(ret))
    for p in ret:
        print(' '.join(map(str, p)))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
