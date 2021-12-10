import collections
import heapq
import math
import random
from typing import List

from dataStructure.common import ListNode


class Solution:
    # log2(10^2000)  2000*3 =7000
    def superPow(self, a: int, b: List[int]) -> int:
        def gt0(b: List[int]) -> bool:
            for i in b:
                if i != 0:
                    return True
            return False

        def isTwo(b: List[int]) -> bool:
            return b[-1] % 2 == 0

        def half(b: List[int]) -> List[int]:
            last = 0
            for i in range(len(b)):
                cur = b[i]
                b[i] = (10 * last + cur) // 2
                last = (10 * last + cur) % 2
            return b

        mod = 1337
        ans = 1
        while gt0(b):
            if not isTwo(b):
                ans = ans * a % mod
            b = half(b)
            a = a * a % mod
        return ans

print(len("bbaaAaa1aaaaaaaaacccccc"))