# Traveling Salesman

## Type1
$xy$ 平面上に座標が重複しない $N$ 個の頂点が与えられます．頂点には $0$ から $N-1$ の番号が振られており，$i$ 番目の頂点の座標は $(x_{i}, y_{i})$ です．全ての頂点を通る閉路で，スコアがなるべく小さくなるようなものを一つ出力してください．

### 制約
- $50 \leqq N \leqq 500$
- $0 \leqq x_{i}, y_{i} \leqq 100$
-  $i \neq j の時, \ (x_{i}, y_{i}) \neq (x_{j}, y_{j})$

### 入力
$1$ 行目に頂点の数 $N$，続く $N$ 行に各頂点の座標 $0 \leqq x_{i}, y_{i} \leqq 100$ が標準入力で与えられます．  
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

### スコア
スコアは以下のように計算します．ただし，$v_{N} = v_{0}$ とします．無効な出力の場合はスコアを $-1$ とします．  
$$
score = \sum_{k=0}^{N-1} \sqrt{(x_{v_{k}} - x_{v_{k+1}})^2 + (y_{v_{k}} - y_{v_{k+1}})^2}
$$

## Type2
Type1と同じ設定で，スコアの計算方法だけ異なります．

### スコア
スコアは以下のように計算します．ただし，$v_{N} = v_{0}$ とします．無効な出力の場合はスコアを $-1$ とします．  
$$
score = \sum_{k=0}^{N-1} (|x_{v_{k}}-x_{v_{k+1}}|+|y_{v_{k}}-y_{v_{k+1}}|)
$$

## Type3
Type1と同じ設定で，スコアの計算方法だけ異なります．

### スコア
スコアは以下のように計算します．ただし，$v_{N} = v_{0}$ とします．無効な出力の場合はスコアを $-1$ とします．  
$$
score = \sum_{k=0}^{N-1} max(|x_{v_{k}}-x_{v_{k+1}}|,|y_{v_{k}}-y_{v_{k+1}}|)
$$

## テスタ
ビルドで`build/libs/`に生成される`Tester.jar`がテスタです．テスタは下記のように実行し，`"<command>"`に作成したプログラムの実行コマンド，`<type>`に問題の種類を表す数値，`<seed>`に乱数のシードを入れてください．実行結果として標準出力にJSON形式でスコア情報が，標準エラー出力に実行エラー時のメッセージが出力されます．
```
$ java -jar Tester.jar --exec "<command>" --type <type> --seed <seed>
```
### サンプルプログラム
c++，java，python は`sample/`にそれぞれサンプルプログラムと，実行のスクリプト`run.sh`，`run.py`を用意しています．

### その他オプション
`--vis` オプションは，｀
```
usage: Tester.jar
-d,--debug          export the input and output of <command> as a text file.
-e,--exec <command> set the execution command of the solver. (required)
-h,--help           print this message.
-o,--save           export the visualized result in png format.
-s,--seed <seed>    set a random seed. (required)
-t,--type <type>    set the scoring type [1 ~ 3]. (required)
-v,--vis            visualize the result.
```
