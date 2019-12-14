<h2>Traveling Salesman</h2>

<h3>問題文</h3>
巡回セールスマン問題です．座標が重複しない$N$個の頂点の座標が与えられます．頂点には$0$から$N-1$の番号が振られており，$i$番目の頂点の座標は$(x_{i}, y_{i})$です．全ての頂点を一度ずつ通って出発点に戻ってくる時の移動距離をなるべく小さくしてください．

<h4>制約</h4>
<ul>
<li>$0 \leqq N \leqq 1000$</li>
<li>$0 \leqq x_{i}, y_{i} \leqq 1000$</li>
<li>$When \ i \neq j,\ (x_{i}, y_{i}) \neq (x_{j}, y_{j})$</li>
</ul>

<h4>入力</h4>
<div class = "iodata">
$N$<br>
$x_{0} \ y_{0}$<br>
$x_{1} \ x_{1}$<br>
$\vdots$<br>
$x_{N-1} \ y_{N-1}$<br>
</div>

<h4>出力</h4>
訪れる順に頂点番号を出力してください．
<div class = "iodata">
$v_{0}$<br>
$v_{1}$<br>
$\vdots$<br>
$v_{N-1}$<br>
</div>

<h3>スコア</h3>
$v_{0} \ \rightarrow v_{1} \ \rightarrow \ \cdots \ \rightarrow \ v_{N-1} \ \rightarrow \ v_{0}$と頂点を訪れた時の移動距離をスコアとする．訪れていない頂点がある場合のスコアは$-1$となります．

<h3>テスタ</h3>
TopCoder の Marathon Match と同じです．<code>"[command]"</code>にプログラムの実行コマンド，<code>[seed]</code>に乱数のシードを入れてください．
<div class = "iodata">
<pre>
$ java -jar Tester.jar -exec "[command]" -seed [seed]
</pre>
</div>

<h4>その他オプション</h4>
<pre>
-vis   : ビジュアライズ
-save  : 画像の保存
-debug : 実行コマンドの入出力を保存
</pre>
