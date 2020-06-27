#!/bin/bash

CXX="g++"
CXXFLAGS="-O2 -Wall -std=c++14"
TESTER="../../build/libs/Tester.jar"
TESTNUM=10

$CXX $CXXFLAGS main.cpp -o main
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

for i in `seq 1 $TESTNUM`; do
    java -jar $TESTER --exec "./main" --seed $i
done
