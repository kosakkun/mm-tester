# Rectilinear Steiner Tree

## 問題文
$100×100$の盤面にパネルが$N$枚置かれています．パネルの座標は$(x_{i},y_{i})$です．追加でパネルを足してこれら$N$枚を連結にしてください．追加するパネルの数はなるべく小さくしてください．ただし，パネルが連結であるとは，上下左右でパネルが辺を共有しているということです．

## 制約
- $10 \leqq N \leqq 200$
- $0 \leqq x_{i}, y_{i} \leqq 99$

### 入力
$1$行目にパネルの枚数$N$，続く$N$行にパネルの座標$(x_{i},y_{i})$が標準入力で与えられます．  
$N$  
$x_{0} \ y_{0}$  
$x_{1} \ y_{1}$  
$\vdots$  
$x_{N-1} \ y_{N-1}$  

### 出力
$1$行目に盤面に追加するパネルの数$M$，続く$M$行にそれぞれの座標を標準出力に出力してください．もともと配置されているパネルと座標が重複しないようにしてください．また，出力するパネル同士でも座標が重複しないようにしてください．  
$M$  
$ax_{0} \ ay_{0}$  
$ax_{1} \ ay_{1}$  
$\vdots$  
$ax_{M-1} \ ay_{M-1}$  

## スコア
入力で与えられたのパネルが連結になった場合，追加したパネルの数$M$枚がスコアになります．連結になってなかったら$-1$を出力します．

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
