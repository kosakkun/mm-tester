import sys
import subprocess
from subprocess import PIPE

BUILD_CMD = 'javac Main.java'
proc = subprocess.run(BUILD_CMD, shell=True)

if proc.returncode != 0:
    print('Compilation failed.')
    sys.exit(1)

TESTER  = '../../../build/libs/Tester.jar'
COMMAND = 'java Main'
OPTIONS = '--type 4'
TESTNUM = 10

for i in range(TESTNUM):
    RUN_CMD = 'java -Xss256M -jar {} --exec "{}" --seed {} {}'.format(TESTER, COMMAND, i + 1, OPTIONS)
    proc = subprocess.run(RUN_CMD, shell=True, stdout=PIPE, stderr=PIPE)
    print(proc.stderr.decode('utf-8'), end='')
    print(proc.stdout.decode('utf-8'), end='')
