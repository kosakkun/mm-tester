#!/bin/bash

TESTER="../../build/libs/Tester.jar"
TESTNUM=10

for i in `seq 1 $TESTNUM`; do
    java -jar $TESTER -exec "python3 main.py" -seed $i -json
done
