# mm-tester 

## Requirements
- JDK 8 or later to build tester

### Ubuntu
```
$ sudo apt update
$ sudo apt install default-jdk build-essential git python3
```

### macOS
```
$ xcode-select --install
```
```
$ /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
$ brew update
$ brew install openjdk git python3
```

## Usage

### Tester build
```
$ git clone --depth 1 https://github.com/kosakkun/mm-tester.git
$ cd mm-tester
$ chmod +x gradlew
$ ./gradlew build
```

### Tree of each problem
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
```
$ cd problems/TravelingSalesman/sample/type1/java
$ chmod +x run.sh
$ ./run.sh
```

## Problems

If you cannot read the  problem statement, please use [here](https://stackedit.io) or something to read it.

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
- [Traveling Salesman](problems/TravelingSalesman/)

## License
- MIT License
  - [mm-tester](https://github.com/kosakkun/mm-tester/blob/master/LICENSE)
- This software includes the work that is distributed in the [Apache License 2.0.](http://www.apache.org/licenses/LICENSE-2.0)
  - [Jackson Databind](https://github.com/FasterXML/jackson-databind)
  - [Apache Commons CLI](https://commons.apache.org/proper/commons-cli/)
  - [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/)
  - [Spring Boot](https://spring.io/projects/spring-boot)
  - [Gradle](https://gradle.org)
