import sys

class EdgeMaching:
    def solve (self, N, C, U, D, L, R):
        x = []
        y = []
        r = []
        for xt in range(N):
            for yt in range(N):
                x.append(xt)
                y.append(yt)
                r.append(0)
        return x,y,r

def main():
    N,C = map(int,input().split())
    U = []
    D = []
    L = []
    R = []
    PN = N * N
    for i in range(PN):
        u,d,l,r = map(int,input().split())
        U.append(u)
        D.append(d)
        L.append(l)
        R.append(r)
    em = EdgeMaching()
    x,y,r = em.solve(N, C, U, D, L, R)
    for i in range(PN):
        print(str(x[i]) + " " + str(y[i]) + " " + str(r[i]))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
