import sys

class VehicleRouting:
    def solve (self, N, M, depotX, depotY, x, y, cap, speed):
        K = 0
        T = []
        L = []
        D = []
        while N > 0:
            K = K + 1
            T.append(0)
            L.append(min(N, cap[0]))
            Dt = []
            for i in range(L[-1]):
                N = N - 1
                Dt.append(N)
            D.append(Dt)
        return K, T, L, D

def main():
    N, M = map(int,input().split())
    depotX, depotY = map(int,input().split())
    x = []
    y = []
    for i in range(N):
        xt,yt = map(int,input().split())
        x.append(xt)
        y.append(yt)
    cap = []
    speed = []
    for i in range(M):
        c,s = map(int,input().split())
        cap.append(c)
        speed.append(s)
    
    vr = VehicleRouting()
    K, T, L, D = vr.solve(N, M, depotX, depotY, x, y, cap, speed)
    print(K)
    for i in range(K):
        print(str(T[i]) + ' ' + str(L[i]))
        print(' '.join(map(str, D[i])))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
