# Rectangle Packing

## 問題文
長方形の詰め込み問題です．長方形が$N$個与えらます．$i$番目の長方形の横幅は$w_{i}$，縦幅$h_{i}$はです．これらの長方形を$0 \leqq x_{i},y_{i} \leqq 1000$の箱の中にはみ出たり重ならないようにしながら，なるべく小さな領域に敷き詰めてください．長方形は回転できません．

### 制約
- $N = 400$
- $5 \leqq w_{i}, h_{i} \leqq 50$
- 与えられた$N$個の長方形は必ず$0 \leqq x_{i},y_{i} \leqq 1000$の箱の中に収まる

### 入力
$1$行目に長方形の個数$N$，続く$N$行に長方形の横幅と縦幅 $w_{i}, h_{i}$ が標準入力で与えられます．  
$N$  
$w_{0} \ h_{0}$  
$w_{1} \ h_{1}$  
$w_{2} \ h_{2}$  
$\vdots$  
$w_{N-1} \ h_{N-1}$  

### 出力
各長方形の左下の座標 $(x_{i},y_{i})$ を入力の順番に整数で標準出力に出力してください．ただし，詰め込む箱の左下の座標を$(x,y)=(0,0)$とします．  
$x_{0} \ y_{0}$  
$x_{1} \ y_{1}$  
$x_{2} \ y_{2}$  
$\vdots$  
$x_{N-1} \ y_{N-1}$  

## スコア
$N$個全ての長方形を詰め込んだ時の高さをスコアとします．具体的には，全ての長方形の四隅の座標の内，最も$y$座標が大きなものがスコアになります．$0 \leqq x_{i},y_{i} \leqq 1000$から飛び出していたり，長方形同士が重なっている場合$-1$となります．

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