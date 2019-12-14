import sys

class GraphColoring:
    def solve (self, N, M, a, b):
        ret = []
        for i in range(N):
            ret.append(i)
        return ret

def main():
    N,M = map(int,input().split())
    a = []
    b = []
    for i in range(M):
        at,bt = map(int,input().split())
        a.append(at)
        b.append(bt)
    gc = GraphColoring()
    ret = gc.solve(N, M, a, b)
    for col in ret:
        print(col)
    sys.stdout.flush()

if __name__ == '__main__':
    main()
