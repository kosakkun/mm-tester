#!/bin/bash

TESTER="../../build/libs/Tester.jar"
TESTNUM=10

for i in `seq 1 $TESTNUM`; do
    java -Xss18m -jar $TESTER --exec "python3 main.py" --seed $i
done
