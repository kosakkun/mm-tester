import sys

class TravelingSalesman:
    def solve (self, N, c):
        v = []
        for i in range(N):
            v.append(i)
        return v

def main():
    N = int(input())
    c = []
    for i in range(N):
        ct = list(map(int,input().split()))
        c.append(ct)
    ts = TravelingSalesman()
    v = ts.solve(N, c)
    for i in v:
        print(i)
    sys.stdout.flush()

if __name__ == '__main__':
    main()
