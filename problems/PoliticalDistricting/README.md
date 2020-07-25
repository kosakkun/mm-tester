# Political Districting

## 問題文
$N×N$ の盤面があります．各マスには数字 $B_{r,c}$ が書かれています．この盤面を $K$ 個の連結な領域に分割してください．この時に $K$ 個の領域それぞれで領域内のマスに書かれた数字の和を計算し，これらの最大値と最小値の差がなるべく小さくなるようにしてください．

### 制約
- $N = 50$
- $3 \leqq K \leqq 100$
- $1 \leqq B_{r,c} \leqq 99$

### 入力
$1$行目に頂点の数 $N$，領域の数 $K$，続く $N$ 行に盤面 $B_{r,c}$ が標準入力で与えられます．  
$N \ K$  
$B_{0,0} \ B_{0,1} \ \cdots \ B_{0,N-1}$  
$B_{1,0} \ B_{1,1} \ \cdots \ B_{1,N-1}$  
$\vdots$  
$B_{N-1,0} \ B_{N-1,1} \ \cdots \ B_{N-1,N-1}$  

### 出力
入力で与えられた盤面と同じ位置のマスの領域番号 $0 \leqq R_{r,c} < K$ を決定して，入力の盤面と同じフォーマットで標準出力に出力してください．同じ領域番号のマス同士は連結である必要があります．  
$R_{0,0} \ R_{0,1} \ \cdots \ R_{0,N-1}$  
$R_{1,0} \ R_{1,1} \ \cdots \ R_{1,N-1}$  
$\vdots$  
$R_{N-1,0} \ R_{N-1,1} \ \cdots \ R_{N-1,N-1}$  

## スコア
$K$ 個の領域それぞれで領域内のマスに書かれた数字の和を計算する．ただしマスが割り当てられていない領域は $0$ とする．これらの最大値と最小値の差をスコアとする．出力の各マスの領域番号に $0$ から $K-1$ 以外の数字が含まれている場合や，同じ領域番号のマスが連結で無い場合のスコアは$-1$となります．

## テスタ
ビルドで`build/libs/`に生成される`Tester.jar`がテスタです．テスタは下記のように実行し，`"<command>"`に作成したプログラムの実行コマンド，`<seed>`に乱数のシードを入れてください．実行結果として標準出力にJSON形式でスコア情報が，標準エラー出力に実行エラー時のメッセージが出力されます．
```
$ java -jar Tester.jar --exec "<command>" --seed <seed>
```
### サンプルプログラム
c++，java，python は`sample/`にそれぞれサンプルプログラムと，実行のスクリプト`run.sh`，`run.py`を用意しています．

### その他オプション
```
usage: Tester.jar
 -d,--debug            export the input and output of <command> as a text file.
 -e,--exec <command>   set the execution command of the solver. (required)
 -h,--help             print this message.
 -o,--save             export the visualized result in png format.
 -s,--seed <seed>      set a random seed. (required)
 -v,--vis              visualize the result.
```
