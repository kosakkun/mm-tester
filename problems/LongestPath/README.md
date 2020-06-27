# Longest Path

## 問題文
座標が重複しない平面上の$N$個の頂点，頂点同士を繋ぐ$M$個の辺からなる平面グラフが与えられます．頂点には$0$から$N-1$の番号が振られており，$i$番目の頂点の座標は$(x_{i}, y_{i})$です．辺には$0$から$M-1$の番号が振られており，$j$番目の辺は頂点$a_{j}$と$b_{j}$を繋ぎます．また辺の重みはユークリッド距離です．なるべく距離の長い単純路を見つけてください．

### 制約
- $50 \leqq N \leqq 1000$
- $N - 1 \leqq M \leqq 3N - 6$
- $0 \leqq x_{i}, y_{i} \leqq 1000$

### 入力
$1$行目に頂点の個数$N$，辺の本数$M$，続く$N$行に各頂点の座標 $0 \leqq x_{i}, y_{i} \leqq$ ，続く$M$行に辺 $(a_{i}, b_{i})$ が標準入力で与えられます．  
$N \ M$  
$x_{0} \ y_{0}$  
$x_{1} \ y_{1}$  
$\vdots$  
$x_{N-1} \ y_{N-1}$  
$a_{0} \ b_{0}$  
$a_{1} \ b_{1}$  
$\vdots$  
$a_{M-1} \ b_{M-1}$  

### 出力
$1$行目に訪れる頂点の個数$K$，続く$K$行に訪れる頂点の番号 $0 \leqq v_{i} < N$ を標準出力に出力してください．  
$K$  
$v_{0}$  
$v_{1}$  
$\vdots$  
$v_{K-1}$  

## スコア
$v_{0} \ \rightarrow v_{1} \ \rightarrow \ \cdots \ \rightarrow \ v_{N-1}$と頂点を訪れた時の移動距離をスコアとします．制約を満たしていない場合$-1$となります．

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
 -d,--debug            write the input and output of <command> as a text file.
 -e,--exec <command>   set the execution command of the solver. (required)
 -h,--help             print this message.
 -o,--save             output the visualized result in png format.
 -s,--seed <seed>      set a random seed. (required)
 -v,--vis              visualize the result.
```
