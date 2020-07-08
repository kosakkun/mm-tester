# Disk Covering

## 問題文
平面に点が$N$個有ります．いくつかの半径$R$の円で全ての点をカバーしてください．この時に使用した円の数を小さくしてください．ここで円が点をカバーしているとは，円の中心を$(x_{R}, y_{R})$，点の頂点を$(x_{P}, y_{P})$とした時に以下の条件を満たすことです．

$$
(x_{R} - x_{P})^2 + (y_{R} - y_{P})^2 \leqq R^2
$$

### 制約
- $20 \leqq N \leqq 1000$
- $10 \leqq R \leqq 100$
- $0 \leqq x_{P_{i}},y_{P_{i}} \leqq 1000$
- $When \ i \neq j,\ (x_{P_{i}}, y_{P_{i}}) \neq (x_{P_{j}}, y_{P_{j}})$

### 入力
$1$行目に頂点の個数$N$，円の半径$R$，続く$N$行に頂点の座標 $0 \leqq x_{P_{i}}, y_{P_{i}} \leqq 1000$ が標準入力で与えられます．  
$N \ R$  
$x_{P_{0}} \ y_{P_{0}}$  
$x_{P_{1}} \ y_{P_{1}}$  
$\vdots$  
$x_{P_{N-1}} \ y_{P_{N-1}}$  

### 出力
$1$行目に使用した円の数$M$，続く$M$行に円の中心の座標 $0 \leqq x_{R_{i}}, y_{R_{i}} \leqq 1000$ を標準出力に出力してください．  
$M$  
$x_{R_{0}} \ y_{R_{0}}$  
$x_{R_{1}} \ y_{R_{1}}$  
$\vdots$  
$x_{R_{M-1}} \ y_{R_{M-1}}$  

## スコア
入力で与えられた点を円で全てカバー出来た場合，円の個数をスコアとします．条件を満たさない場合$-1$になります．


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
