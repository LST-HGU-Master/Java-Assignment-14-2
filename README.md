# 課題 14-2: ファイル読み込み

### 課題の説明
テキストファイル「textfile1.txt」の読み込みと画面へのprintを行うプログラムProgE2.mainメソッドを以下の２要件を満たし、かつ、下記の２つの実行結果となるように作成しなさい。

- ファイル名はString型変数を定義して実行時引数によって与えること
- ファイル読み込みで用いるFileReaderのコンストラクタやread()、close()はIOExceptionを送出する可能性があるので、例外をキャッチして対処できるようにしておくこと

ヒント: try-with-resources文を使った例外処理(教科書17.5.4 ~17.5.6)を行うと簡潔に書ける

注）BlueJで動作確認をするためには、「textfile1.txt」をプロジェクトunitE2と同じフォルダにコピーする必要がある。
### textfile1.txt
```
1.読み込み確認用ファイル
2.ここは2行目です。
3.
```

### 実行結果１（実行時引数を　textfile1.txt　とし、かつ、ファイルが存在する場合）
```
1.読み込み確認用ファイル
2.ここは2行目です。
3.
```

### 実行結果２（実行時引数を　存在しないファイル名（例えば textfile2.txt)　とした場合）
```
ファイル読み込みで例外が発生しました。処理を中断します！
```