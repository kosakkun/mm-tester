import sys

class RectanglePacking:
    def solve (self, N, w, h):
        ret = []
        for i in range(N):
            p = [100 * (i % 10), 100 * (i // 10)]
            ret.append(p)
        return ret

def main():
    N = int(input())
    w = []
    h = []
    for i in range(N):
        wt,ht = map(int,input().split())
        w.append(wt)
        h.append(ht)
    rp = RectanglePacking()
    ret = rp.solve(N, w, h)
    for p in ret:
        print(' '.join(map(str, p)))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
