# Edge Matching

## 問題文
$N×N$の盤面と$N^2$個のパネルがあります．各パネルの4つの辺は，$C$色のうちいずれかの色で塗られています．パネルを上手く盤面に配置して（回転もOK），盤面全体で隣り合うパネルが共有する辺の色が一致する場所をなるべく多くしてください．

### 制約
- $5 \leqq N \leqq 50$
- $5 \leqq C \leqq 50$

### 入力
$1$行目に$N$と$C$，続く$N^2$行に各パネルの上下左右の辺の色 $0 \leqq U_{i}, D_{i}, L_{i}, R_{i} < C$ が標準入力で与えられます．  
$N \ C$  
$U_{0} \ D_{0} \ L_{0} \ R_{0}$  
$U_{1} \ D_{1} \ L_{1} \ R_{1}$  
$\vdots$  
$U_{N^2-1} \ D_{N^2-1} \ L_{N^2-1} \ R_{N^2-1}$  

### 出力
入力に対応するパネル毎に，配置する位置 $0 \leqq x_{i}, y_{i} < N$，時計回りの回転を表す$0$から$3$の数字 $r_{i} \ (0:0°, 1:90°, 2:180°, 3:270°)$を標準出力に出力してください．左上の座標を $(0, 0)$ とします．  
$x_{0} \ y_{0} \ r_{0}$  
$x_{1} \ y_{1} \ r_{1}$  
$\vdots$  
$x_{N^2-1} \ y_{N^2-1} \ r_{N^2-1}$  

## スコア
パネルを出力に従って盤面に並べた時の，隣り合うパネルが共有する辺の色が同じものの総数をスコアとします．盤面外の座標や，他のパネルと重複した座標が指定された場合，範囲外の色が指定された場合のスコアは$-1$となります．

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
