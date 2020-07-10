# Graph Coloring

## 問題文
グラフの彩色問題です．頂点の個数$N$，辺の本数$M$のグラフが与えられます．頂点には$0$から$N-1$までの番号が振られています．隣接する頂点同士が異なる色になるように彩色してください．この時に必要な色の数をなるべく少なくしてください．グラフは多重辺や自己ループを持ちません，また必ずしも連結ではありません．

### 制約
- $N=200$</li>
- $2N \leqq M \leqq N(N-1)/4$

### 入力
$1$行目に頂点の個数$N$，辺の本数$M$，続く$M$行に辺$(a_{i}, b_{i})$が標準入力で与えられます．辺は頂点$a_{i}$と頂点$b_{i}$を繋ぎます．  
$N \ M$  
$a_{0} \ b_{0}$  
$a_{1} \ b_{1}$  
$\vdots$  
$a_{M-1} \ b_{M-1}$  

### 出力
$N$個の頂点の色を整数で標準出力に出力してください．ただし整数は$0$から$N-1$の範囲で選んでください．  
$c_{0}$  
$c_{1}$  
$\vdots$  
$c_{N-1}$  

## スコア
頂点の彩色に使った整数の種類の数がそのままスコアになります．出力が問題の制約を満たさない場合は$-1$が出力されます．

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
