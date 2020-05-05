<h2>Clustering</h2>

<h3>問題文</h3>
2次元平面のクラスタリングです．$N$個の頂点$(x_{i},y_{i})$とクラスタ数$K$が与えられます．$K$個のクラスタの中心を，各頂点から最も近いクラスタの中心との距離の和がなるべく小さくなるように配置してください．

<h3>制約</h3>
<ul>
<li>$100 \leqq N \leqq 1000$</li>
<li>$5 \leqq K \leqq 20$</li>
<li>$0 \leqq x_{i}, y_{i} \leqq 1000$</li>
</ul>

<h4>入力</h4>
<div class = "iodata">
$N \ K$<br>
$x_{0} \ y_{0}$<br>
$x_{1} \ y_{1}$<br>
$\vdots$<br>
$x_{N-1} \ y_{N-1}$<br>
</div>

<h4>出力</h4>
$K$個のクラスタの中心の座標を出力してください．座標は整数で，$0 \leqq cx_{i},cy_{i} \leqq 1000$の範囲で重複が無いようにしてください．
<div class = "iodata">
$cx_{0} \ cy_{0}$<br>
$cx_{1} \ cy_{1}$<br>
$\vdots$<br>
$cx_{K-1} \ cy_{K-1}$<br>
</div>

<h3>スコア</h3>
各頂点から$K$個のクラスタの中心の座標への距離のうち，最短のものの和をスコアとします．

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
