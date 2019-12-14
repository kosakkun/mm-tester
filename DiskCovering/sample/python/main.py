import sys

class DiskCovering:
    def solve (self, N, R, x, y):
        ret = []
        for i in range(N):
            ret.append([x[i], y[i]])
        return ret

def main():
    N,R = map(int,input().split())
    x = []
    y = []
    for i in range(N):
        xt,yt = map(int,input().split())
        x.append(xt)
        y.append(yt)
    dc = DiskCovering()
    ret = dc.solve(N, R, x, y) 
    print(len(ret))
    for p in ret:
        print(' '.join(map(str, p)))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
