import sys

class EuclideanSteinerTree:
    def solve (self, N, x, y):
        ret = [[500, 500]]
        return ret;

def main():
    N = int(input())
    x = [];
    y = [];
    for i in range(N):
        xt,yt = map(int,input().split())
        x.append(xt)
        y.append(yt)
    est = EuclideanSteinerTree()
    ret = est.solve(N, x, y)
    print(len(ret))
    for p in ret:
        print(' '.join(map(str, p)))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
