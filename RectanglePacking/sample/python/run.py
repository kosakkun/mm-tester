import subprocess
from subprocess import PIPE

TESTER  = '../../build/libs/Tester.jar'
COMMAND = 'python3 main.py'
OPTIONS = ''
TESTNUM = 10

for i in range(TESTNUM):
    RUN_CMD = f'java -jar {TESTER} --exec "{COMMAND}" --seed {i + 1} {OPTIONS}'
    proc = subprocess.run(RUN_CMD, shell=True, stdout=PIPE, stderr=PIPE, text=True)
    print(proc.stderr, end='')
    print(proc.stdout, end='')
