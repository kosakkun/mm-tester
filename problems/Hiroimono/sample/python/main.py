import sys

class Hiroimono:
    def solve (self, N, x, y):
        M = 1
        v = []
        for i in range(N):
            v.append(i)
        return M, v

def main():
    N = int(input())
    x = []
    y = []
    for i in range(N):
        xt,yt = map(int,input().split())
        x.append(xt)
        y.append(yt)
    h = Hiroimono()
    M, v = h.solve(N, x, y)

    print(M)
    for i in v:
        print(i)
    sys.stdout.flush()

if __name__ == '__main__':
    main()
