# Clustering

## 問題文
2次元平面のクラスタリングです．$N$個の頂点$(x_{i},y_{i})$とクラスタ数$K$が与えられます．$K$個のクラスタの中心を，各頂点から最も近いクラスタの中心との距離の和がなるべく小さくなるように配置してください．

### 制約
- $100 \leqq N \leqq 1000$
- $5 \leqq K \leqq 20$
- $0 \leqq x_{i}, y_{i} \leqq 1000$

### 入力
$1$行目に$N$と$K$，続く$N$行に各頂点の座標 $0 \leqq x_{i}, y_{i} \leqq 1000$ が標準入力で与えられます．  
$N \ K$  
$x_{0} \ y_{0}$  
$x_{1} \ y_{1}$  
$\vdots$  
$x_{N-1} \ y_{N-1}$  

### 出力
$K$個のクラスタの中心の座標を標準出力に出力してください．座標は整数で，$0 \leqq cx_{i},cy_{i} \leqq 1000$ の範囲で重複が無いようにしてください．  
$cx_{0} \ cy_{0}$  
$cx_{1} \ cy_{1}$  
$\vdots$  
$cx_{K-1} \ cy_{K-1}$  

## スコア
各頂点から$K$個のクラスタの中心の座標への距離のうち，最短のものの総和をスコアとします．


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
