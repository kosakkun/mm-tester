import sys
import subprocess
from subprocess import PIPE


BUILD_CMD = 'javac Main.java'
proc = subprocess.run(BUILD_CMD, shell=True)

if proc.returncode != 0:
    print('Compilation failed.')
    sys.exit(1)


TESTER  = '../../build/libs/Tester.jar'
COMMAND = 'java Main'
OPTIONS = ''
TESTNUM = 10

for i in range(TESTNUM):
    RUN_CMD = f'java -jar {TESTER} --exec "{COMMAND}" --seed {i + 1} {OPTIONS}'
    proc = subprocess.run(RUN_CMD, shell=True, stdout=PIPE, stderr=PIPE, text=True)
    print(proc.stderr, end='')
    print(proc.stdout, end='')
