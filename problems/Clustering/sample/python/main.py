import sys

class Clustering:
    def solve (self, N, K, x, y):
        cx = []
        cy = []
        for i in range(K):
            cx.append(i // 4 * 200 + 100)
            cy.append(i % 4 * 250 + 100)
        return cx, cy

def main():
    N,K = map(int, input().split())
    x = []
    y = []
    for i in range(N):
        xt,yt = map(int,input().split())
        x.append(xt)
        y.append(yt)

    c = Clustering()
    cx, cy = c.solve(N, K, x, y)
    for i in range(len(cx)):
        print(str(cx[i]) + ' ' + str(cy[i]))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
