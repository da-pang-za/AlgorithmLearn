# TODO 浮点数

# max min  支持任意参数个数
import math
import random
from collections import defaultdict
from typing import List

print(max(1, 2, 23, 5))
print(min(1, 2, -9))
print(sum([1, 2, 3]))  # 求和 参数要求：Iterable
# 幂运算2^3
print(2 ** 3)
print(10 ** 9 + 7)  # mod 常用
# 绝对值
print(abs(-12))


print("======位运算=========")
a = 60  # 60 = 0011 1100
b = 13  # 13 = 0000 1101
print(a & b)  # 12 = 0000 1100
print(a | b)  # 61 = 0011 1101
print(a ^ b)  # 49 = 0011 0001
print(~a)  # -61 = 1100 0011
print(a << 2)  # 240 = 1111 0000
print(a >> 2)  # 15 = 0000 1111

print(sum([c for c in range(1, 9)]))


#  布尔运算  and or not
class Solution318:
    def maxProduct(self, words: List[str]) -> int:
        def hashset(word):
            # 用26位位运算表示二十六个字母在word中被使用的情况
            return sum(1 << (ord(c) - ord('a')) for c in set(word))

        d, ans = defaultdict(int), 0
        for w in words:
            h = hashset(w)
            if d[h] < len(w):
                for other in d:
                    # 如果位运算&的结果为0，说明他们没有使用过同样的字母，可以计算答案
                    if not other & h:
                        ans = max(d[other] * len(w), ans)
                d[h] = len(w)
        return ans


# 随机
# 整数random.randint(1, 3) [1,3]
list = [random.randint(1, 3) for _ in range(59)]
print(list)
# 浮点
random.random()  # [0.0, 1.0)
random.uniform(0, 1)  # [0,1]

print([i for i in range(1, 3)])  # [1,3)
print([i for i in range(3, 1, -1)])  # (1,3]

# 逻辑
f = False
f |= True
print(f)
