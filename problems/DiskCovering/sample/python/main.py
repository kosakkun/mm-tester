import sys

class DiskCovering:
    def solve (self, N, R, x, y):
        M = N
        rx = x
        ry = y
        return M, rx, ry

def main():
    N, R = map(int,input().split())
    x = []
    y = []
    for i in range(N):
        xt,yt = map(int,input().split())
        x.append(xt)
        y.append(yt)
    
    dc = DiskCovering()
    M, rx, ry = dc.solve(N, R, x, y) 
    print(M)
    for i in range(M):
        print(str(rx[i]) + ' ' + str(ry[i]))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
