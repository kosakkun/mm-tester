import sys

class SlidingPuzzle:
    def solve (self, N, B):
        ret = []
        for x in range(N):
            for y in range(N):
                ret.append([x, y])
        return ret

def main():
    N = int(input())
    B = []
    for i in range(N):
        l = map(int, input().split())
        B.append(l);
    sp = SlidingPuzzle()
    ret = sp.solve(N, B)
    print(len(ret))
    for p in ret:
        print(' '.join(map(str, p)))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
