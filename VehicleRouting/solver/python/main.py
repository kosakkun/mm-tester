import sys

class VehicleRouting:
    def solve (self, N, M, depotX, depotY, x, y, cap, speed):
        ret = []
        while N > 0:
            T = 0
            L = min(N, cap[T])
            truck = [T, L]
            D = []
            for i in range(L):
                N = N - 1
                D.append(N)
            ret.append(truck)
            ret.append(D)
        return ret

def main():
    N,M = map(int,input().split())
    depotX,depotY = map(int,input().split())
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
    ret = vr.solve(N, M, depotX, depotY, x, y, cap, speed)
    print(len(ret) // 2)
    for l in ret:
        print(' '.join(map(str, l)))
    sys.stdout.flush()

if __name__ == '__main__':
    main()
