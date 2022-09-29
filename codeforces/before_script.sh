#!/bin/bash

echo "dpz before_script"
python3 "/Users/dpz/Desktop/CS_Knowledge/Alg/codeforces/pre_copy.py" $1
javac $1
# note todo bug : test WA后   文件末尾会多出括号