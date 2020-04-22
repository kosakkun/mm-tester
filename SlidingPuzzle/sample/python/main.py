import sys

class SlidingPuzzle:
    def solve (self, N, B):
        M = N * N
        r = []
        c = []
        for x in range(N):
            for y in range(N):
                r.append(x)
                c.append(y)
        return M, r, c

def main():
    N = int(input())
    B = []
    for i in range(N):
        l = map(int, input().split())
        B.append(l)

    sp = SlidingPuzzle()
    M, r, c = sp.solve(N, B)
    print(M)
    for i in range(M):
        print(str(r[i]) + ' ' + str(c[i]))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
