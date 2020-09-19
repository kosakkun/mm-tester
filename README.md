# mm-tester 

## 環境

### Ubuntu
Ubuntu 16.04 LTS or later
```
$ sudo apt update
$ sudo apt install default-jre default-jdk build-essential git python3
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

### Sample
Traveling Salesmanのjavaのサンプルプログラムを実行する場合．
```
$ cd problems/TravelingSalesman/sample/java
$ chmod +x run.sh
$ ./run.sh
```
または，
```
$ cd problems/TravelingSalesman/sample/java
$ python3 run.py
```

## Problems
- [Traveling Salesman](problems/TravelingSalesman/)
- [Vehicle Routing](problems/VehicleRouting/) 
- [Rectangle Packing](problems/RectanglePacking/)
- [Graph Coloring](problems/GraphColoring/)
- [Clustering](problems/Clustering/)
- [Sliding Puzzle](problems/SlidingPuzzle)
- [Rectilinear Steiner Tree](problems/RectilinearSteinerTree/)
- [Disk Covering](problems/DiskCovering/)
- [Longest Path](problems/LongestPath/)
- [Euclidean Steiner Tree](problems/EuclideanSteinerTree/)
- [Hiroimono](problems/Hiroimono/)
- [Edge Matching](problems/EdgeMatching)
- [Political Districting](problems/PoliticalDistricting)

### 問題文が読めない時
[ここ](https://stackedit.io)とかに貼り付けて読んでください．


## License
- MIT License
  - [mm-tester](https://github.com/kosakkun/mm-tester/blob/master/LICENSE)
- This software includes the work that is distributed in the [Apache License 2.0.](http://www.apache.org/licenses/LICENSE-2.0)
  - [Jackson Databind](https://github.com/FasterXML/jackson-databind)
  - [Apache Commons CLI](https://commons.apache.org/proper/commons-cli/)
  - [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/)
  - [Spring Boot](https://spring.io/projects/spring-boot)
  - [Gradle](https://gradle.org)
