<h2>Hiroimono</h2>

<h3>問題文</h3>
以下のルールで碁盤上の石を全て拾う「ひろいもの（碁石拾い）」というパズルがあります．
<ul>
<li>どの石から拾い始めてもよい．</li>
<li>石を縦・横に進みながら拾う（斜めは不可）．</li>
<li>石の無いところで曲がることはできない．また，元の方向へ引き返すこともできない．</li>
<li>進む途中の石は必ず取らなければならない．</li>
</ul>
$N$個の石が$0 \leqq x_{i},y_{i} \leqq 50$の格子点上に置かれています．$i$番目の石の座標は$(x_{i},y_{i})$です．ひろいもののルールでこれらの石をなるべく多く拾ってください．全ての石を拾う必要はありません．

<h4>制約</h4>
<ul>
<li>$100 \leqq N \leqq 500$</li>
<li>$0 \leqq x_{i}, y_{i} \leqq 50$</li>
<li>$When \ i \neq j,\ (x_{i}, y_{i}) \neq (x_{j}, y_{j})$</li>
</ul>

<h4>入力</h4>
$N$個の石はランダムに座標を決めます．
<div class = "iodata">
$N$<br>
$x_{0} \ y_{0}$<br>
$x_{1} \ x_{1}$<br>
$\vdots$<br>
$x_{N-1} \ y_{N-1}$<br>
</div>

<h4>出力</h4>
拾う順に石の番号を出力してください．
<div class = "iodata">
$M$<br>
$v_{0}$<br>
$v_{1}$<br>
$\vdots$<br>
$v_{M-1}$<br>
</div>

<h3>スコア</h3>
$v_{0} \ \rightarrow v_{1} \ \rightarrow \ \cdots \ \rightarrow \ v_{M-1}$の順番に石を拾っていきます．問題文のルールを満たす場合スコアを$M$とします．ルールを満たさないような拾い方をした場合スコアは$-1$となります．

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
 -g,--save-gif         output gif animation.
 -h,--help             print this message.
 -l,--delay [ms]       frame delay time [ms].
 -p,--save-png         output the visualized result in png format.
 -s,--seed [seed]      set a random seed. (required)
 -v,--vis              visualize the result.
</pre>
