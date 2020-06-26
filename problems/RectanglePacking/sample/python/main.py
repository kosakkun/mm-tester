import sys

class RectanglePacking:
    def solve (self, N, w, h):
        x = []
        y = []
        for i in range(N):
            x.append(100 * (i % 10))
            y.append(100 * (i // 10))
        return x, y

def main():
    N = int(input())
    w = []
    h = []
    for i in range(N):
        wt,ht = map(int,input().split())
        w.append(wt)
        h.append(ht)

    rp = RectanglePacking()
    x, y = rp.solve(N, w, h)
    for i in range(len(x)):
        print(str(x[i]) + ' ' + str(y[i]))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
