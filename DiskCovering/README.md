<h2>Disk Covering</h2>

<h3>問題文</h3>
平面に点が$N$個有ります．いくつかの半径$R$の円で全ての点をカバーしてください．この時に使用した円の数を小さくしてください．ここで円が点をカバーしているとは，円の中心を$(x_{R}, y_{R})$，点の頂点を$(x_{P}, y_{P})$とした時に以下の条件を満たすことです．

<div class = "iodata">
$$
(x_{R} - x_{P})^2 + (y_{R} - y_{P})^2 \leqq R^2
$$
</div>

<h3>制約</h3>
<ul>
<li>$20 \leqq N \leqq 1000$</li>
<li>$10 \leqq R \leqq 100$</li>
<li>$0 \leqq x_{P_{i}},y_{P_{i}} \leqq 1000$</li>
<li>$When \ i \neq j,\ (x_{P_{i}}, y_{P_{i}}) \neq (x_{P_{j}}, y_{P_{j}})$</li>
</ul>

<h4>入力</h4>
点の個数$N$，円の半径$R$，$N$個の頂点の座標$(x_{P_{i}}, y_{P_{i}})$が与えられます．
<div class = "iodata">
$N \ R$<br>
$x_{0} \ y_{0}$<br>
$x_{1} \ y_{1}$<br>
$\vdots$<br>
$x_{N-1} \ y_{N-1}$<br>
</div>

<h4>出力</h4>
使用した円の数$M$，続く$M$行に円の中心の座標$(x_{R_{i}}, y_{R_{i}})$を出力してください．座標は整数で$0 \leqq x_{R_{i}}, y_{R_{i}} \leqq 1000$です．
<div class = "iodata">
$M$<br>
$x_{0} \ y_{0}$<br>
$x_{1} \ y_{1}$<br>
$\vdots$<br>
$x_{M-1} \ y_{M-1}$<br>
</div>

<h3>スコア</h3>
入力で与えられた点を円で全てカバー出来た場合，円の個数をスコアとします．条件を満たさない場合$-1$になります．

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
-json  : 結果をJSON形式で表示
-debug : 実行コマンドの入出力を保存
</pre>
