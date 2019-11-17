import sys

class TravelingSalesman:
    def solve (self, N, x, y):
        ret = []
        for i in range(N):
            ret.append(i)
        return ret

def main():
    N = int(input())
    x = []
    y = []
    for i in range(N):
        xt,yt = map(int,input().split())
        x.append(xt)
        y.append(yt)
    ts = TravelingSalesman()
    ret = ts.solve(N, x, y)
    for i in ret:
        print(i)
    sys.stdout.flush()

if __name__ == '__main__':
    main()
