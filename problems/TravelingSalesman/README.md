# Traveling Salesman

## 問題文
巡回セールスマン問題です．座標が重複しない$N$個の頂点の座標が与えられます．頂点には$0$から$N-1$の番号が振られており，$i$番目の頂点の座標は$(x_{i}, y_{i})$です．全ての頂点を一度ずつ通って出発点に戻ってくる時の移動距離をなるべく小さくしてください．

### 制約
- $0 \leqq N \leqq 1000$
- $0 \leqq x_{i}, y_{i} \leqq 1000$
- $When \ i \neq j,\ (x_{i}, y_{i}) \neq (x_{j}, y_{j})$

### 入力
$1$行目に頂点の数$N$，続く$N$行に各頂点の座標 $0 \leqq x_{i}, y_{i} \leqq 1000$ が標準入力で与えられます．  
$N$  
$x_{0} \ y_{0}$  
$x_{1} \ x_{1}$  
$\vdots$  
$x_{N-1} \ y_{N-1}$  

### 出力
訪れる順に頂点番号を標準出力に出力してください．  
$v_{0}$  
$v_{1}$  
$\vdots$  
$v_{N-1}$  

## スコア
$v_{0} \ \rightarrow v_{1} \ \rightarrow \ \cdots \ \rightarrow \ v_{N-1} \ \rightarrow \ v_{0}$と頂点を訪れた時の移動距離をスコアとする．訪れていない頂点がある場合のスコアは$-1$となります．

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
