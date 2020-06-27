# Euclidean Steiner Tree

## 問題文
$xy$平面上に$N$個の点が与えられます．これらの点には$0$から$N-1$の番号が振られており，$i$番目の点の座標は$(x_{i},y_{i})$です．座標は整数で重複はありません．この平面に$M$個の点を追加します．入力で与えられた点と，追加した点のすべてを頂点とする完全グラフの最小全域木のコストの総和をなるべく小さくするような$M$個の点を出力してください．辺の重みはユークリッド距離です．

### 制約
- $10 \leqq N \leqq 500$
- $0 \leqq M \leqq 1000$
- $0 \leqq x_{i}, y_{i} \leqq 1000$
- When $i \neq j,\ (x_{i}, y_{i}) \neq (x_{j}, y_{j})$

### 入力
$1$行目に頂点の個数$N$，続く$N$行に頂点の座標 $0 \leqq x_{i}, y_{i} \leqq 1000$ が標準入力で与えられます．  
$N$  
$x_{0} \ y_{0}$  
$x_{1} \ x_{1}$  
$\vdots$  
$x_{N-1} \ y_{N-1}$  

### 出力
$1$行目に追加する点の数$M$，続く$M$行に各点の座標$(ax_{i},ay_{i})$を整数で標準出力に出力してください．出力の点の座標は他の点（入力・出力の点）と重ならないようにしてください．  
$M$  
$ax_{0} \ ay_{0}$  
$ax_{1} \ ay_{1}$  
$\vdots$  
$ax_{M-1} \ ay_{M-1}$  

## スコア
入力で与えられた点と，追加した点のすべてを頂点とする完全グラフを作ります．各辺の重みは頂点間のユークリッド距離です．この完全グラフの最小全域木の辺の重みの総和がスコアです．制約を満たさない場合$-1$となります．

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
