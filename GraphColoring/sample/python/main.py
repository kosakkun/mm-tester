import sys

class GraphColoring:
    def solve (self, N, M, a, b):
        c = []
        for i in range(N):
            c.append(i)
        return c

def main():
    N,M = map(int,input().split())
    a = []
    b = []
    for i in range(M):
        at,bt = map(int,input().split())
        a.append(at)
        b.append(bt)

    gc = GraphColoring()
    c = gc.solve(N, M, a, b)
    for col in c:
        print(col)
    sys.stdout.flush()

if __name__ == '__main__':
    main()
