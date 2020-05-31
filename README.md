# mm-tester 

## 環境

### Ubuntu
```
$ sudo apt update
$ sudo apt install default-jre default-jdk build-essential git curl python3
```

### macOS
```
$ xcode-select --install
```
```
$ /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
$ brew update
$ brew cask install java
$ brew install git python3
```

## 使い方

### テスタのビルド
```
$ git clone --depth 1 https://github.com/kosakkun/mm-tester.git
$ cd mm-tester
$ chmod +x gradlew
$ ./gradlew build
```

### 問題毎のファイル構成
```
.
├── README.md
├── build.gradle
├── build
│   └── libs
│       └── Tester.jar
├── tester
│   └── *.java
└── sample
    ├── cpp
    │   ├── run.sh
    │   ├── run.py
    │   └── main.cpp
    ├── java
    │   ├── run.sh
    │   ├── run.py
    │   └── Main.java
    └── python
        ├── run.sh
        ├── run.py
        └── main.py
```

### サンプル
Traveling Salesmanのjavaのサンプルプログラムを実行する場合．
```
$ cd TravelingSalesman/sample/java
$ chmod +x run.sh
$ ./run.sh
```
または，
```
$ cd TravelingSalesman/sample/java
$ python3 run.py
```

## 問題
- [Traveling Salesman](TravelingSalesman/)
- [Vehicle Routing](VehicleRouting/) 
- [Rectangle Packing](RectanglePacking/)
- [Graph Coloring](GraphColoring/)
- [Clustering](Clustering/)
- [Sliding Puzzle](SlidingPuzzle)
- [Rectilinear Steiner Tree](RectilinearSteinerTree/)
- [Disk Covering](DiskCovering/)
- [Longest Path](LongestPath/)
- [Euclidean Steiner Tree](EuclideanSteinerTree/)
- [Hiroimono](Hiroimono/)

### 問題文が読めない時
[ここ](http://marxi.co)とかに貼り付けて読んでください．


## License
- mm-tester - [MIT License](https://github.com/kosakkun/mm-tester/blob/master/LICENSE)
- This software includes the work that is distributed in the [Apache License 2.0.](http://www.apache.org/licenses/LICENSE-2.0)
  - [Jackson Databind](https://github.com/FasterXML/jackson-databind)
  - [Apache Commons CLI](https://commons.apache.org/proper/commons-cli/)
  - [Spring Boot](https://spring.io/projects/spring-boot)
  - [Gradle](https://gradle.org)
