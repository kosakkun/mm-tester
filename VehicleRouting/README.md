<h2>Vehicle Routing</h2>

<h3>問題文</h3>
配送最適化問題です．目的地の数$N$，トラックの台数$M$が与えられます．$N$個の目的地には$0$から$N-1$までの番号が振られており，$i$番目の目的地の座標は$(x_{i},y_{i})$です．$M$台のトラックには$0$から$M-1$までの番号が振られており，$j$番目のトラックの容量は$cap_{j}$，スピードは$speed_{j}$です．倉庫の座標は$(depot_{x},depot_{y})$です．全ての目的地に荷物を$1$つ配達し終わるのにかかる時間をなるべく小さくしてください．倉庫ではトラックの容量以下の任意の個数の荷物を積む事が出来ます．各トラックが配達する荷物の総量に上限はありません．必ず全てのトラックを使う必要もありません．

<h4>制約</h4>
<ul>
<li>$50 \leqq N \leqq 500$</li>
<li>$0 \leqq x_{i},y_{i},depot_{x},depot_{y} \leqq 1000$</li>
<li>$When \ i \neq j,(x_{i},y_{i}) \neq (x_{j},y_{j})$</li>
<li>$For \ each \ i (1 \leq i \leq N), (x_{i},y_{i}) \neq (depot_{x},depot_{y})$</li>
<li>$3 \leqq M \leqq 10$</li>
<li>$5 \leqq cap_{j} \leqq 20$</li>
<li>$1 \leqq speed_{j} \leqq 20$</li>
</ul>

<h4>入力</h4>
<div class = "iodata">
$N \ M$<br>
$depot_{x} \ depot_{y}$<br>
$x_{0} \ y_{0}$<br>
$x_{1} \ y_{1}$<br>
$\vdots$<br>
$x_{N-1} \ y_{N-1}$<br>
$cap_{0} \ speed_{0}$<br>
$cap_{1} \ speed_{1}$<br>
$\vdots$<br>
$cap_{M-1} \ speed_{M-1}$<br>
</div>

<h4>出力</h4>
$1$行目にトラックの出撃回数$K$を出力してください．続く$2K$行には，配達に行くトラックの番号$T_{i}(0 \leqq T_{i} \leqq M-1)$，荷物の個数$L_{i}(1 \leqq L_{i} \leqq cap_{T_{i}})$，$L$個の目的地$D_{i,0},D_{i,1},\cdots,D_{i,L_{i}-1} \ (0 \leqq D_{i,j} \leqq N-1)$を以下のフォーマットで出力してください．各トラック毎に出力された順番で倉庫で荷物を積む，配達先に配るという作業を繰り返します．

<div class = "iodata">
$K$<br>
$T_{0} \ L_{0}$<br>
$D_{0,0} \ D_{0,1} \ \cdots \ D_{0,L_{0}-1}$<br>
$T_{1} \ L_{1}$<br>
$D_{1,0} \ D_{1,1} \ \cdots \ D_{1,L_{1}-1}$<br>
$\vdots$<br>
$T_{K-1} \ L_{K-1}$<br>
$D_{K-1,0} \ D_{K-1,1} \ \cdots \ D_{K-1,L_{K-1}-1}$<br>
</div>
<br>

<h5>出力例</h5>
<div class = "iodata">
3<br>
1 5<br>
3 9 4 1 2<br>
0 2<br>
0 8<br>
2 3<br>
5 7 6<br>
</div>

<h3>スコア</h3>
各トラック毎に，担当分の中で最後の荷物を配達するまでの移動距離を算出する．移動距離をトラックのスピードで割り，そのトラックの配達時間とする．スコアは全てのトラックの配達時間の中で最も時間がかかったものとする．制約を満たしていない場合$-1$になります．．

<h3>テスタ</h3>
TopCoder の Marathon Match と同じです．<code>"[command]"</code>にプログラムの実行コマンド，<code>[seed]</code>に乱数のシードを入れてください．
<div class = "iodata">
<pre>
$ java -jar Tester.jar --exec "[command]" --seed [seed]
</pre>
</div>

<h4>その他オプション</h4>
<pre>
usage: Tester.jar
 -d,--debug            write the input and output of [command] as a text file.
 -e,--exec [command]   set the execution command of the solver. (required)
 -h,--help             print this message.
 -o,--save             output the visualized result in png format.
 -s,--seed [seed]      set a random seed. (required)
 -v,--vis              visualize the result.
</pre>
