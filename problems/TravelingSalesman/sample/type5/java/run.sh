#!/bin/bash

JAVAC="javac"
TESTER="../../../build/libs/Tester.jar"
TESTNUM=10

$JAVAC Main.java
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

for i in `seq 1 $TESTNUM`; do
    java -Xss256M -jar $TESTER --exec "java Main" --seed $i --type 5
done
