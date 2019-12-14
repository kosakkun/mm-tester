#!/bin/bash

JAVAC="javac"
TESTER="../../build/libs/Tester.jar"
TESTNUM=10

$JAVAC Main.java
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

for i in `seq 1 $TESTNUM`; do
    java -Xss18m -jar $TESTER -exec "java Main" -seed $i -json
done
