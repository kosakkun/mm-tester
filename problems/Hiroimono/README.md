# Hiroimono

## 問題文
以下のルールで碁盤上の石を全て拾う「ひろいもの（碁石拾い）」というパズルがあります．
- どの石から拾い始めてもよい．
- 石を縦・横に進みながら拾う（斜めは不可）．
- 石の無いところで曲がることはできない．また，元の方向へ引き返すこともできない．
- 進む途中の石は必ず取らなければならない．

$N$個の石が$0 \leqq x_{i},y_{i} \leqq 50$の格子点上に置かれています．$i$番目の石の座標は$(x_{i},y_{i})$です．ひろいもののルールでこれらの石をなるべく多く拾ってください．全ての石を拾う必要はありません．

### 制約
- $100 \leqq N \leqq 500$
- $0 \leqq x_{i}, y_{i} \leqq 50$
- $When \ i \neq j,\ (x_{i}, y_{i}) \neq (x_{j}, y_{j})$

### 入力
$1$行目に石の数$N$，続く$N$行に石の座標 $0 \leqq x_{i}, y_{i} \leqq 50$ が標準入力で与えられます．$N$個の石はランダムに座標を決めます．  
$N$  
$x_{0} \ y_{0}$  
$x_{1} \ x_{1}$  
$\vdots$  
$x_{N-1} \ y_{N-1}$  

### 出力
$1$行目に拾う石の数$M$を，続く$M$行に拾う順に石の番号を標準出力に出力してください．  
$M$  
$v_{0}$  
$v_{1}$  
$\vdots$  
$v_{M-1}$  

## スコア
$v_{0} \ \rightarrow v_{1} \ \rightarrow \ \cdots \ \rightarrow \ v_{M-1}$の順番に石を拾っていきます．問題文のルールを満たす場合スコアを$M$とします．ルールを満たさないような拾い方をした場合スコアは$-1$となります．

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
 -g,--save-gif         output gif animation.
 -h,--help             print this message.
 -l,--delay <ms>       frame delay time <ms>.
 -p,--save-png         output the visualized result in png format.
 -s,--seed <seed>      set a random seed. (required)
 -v,--vis              visualize the result.
```
