import builtins
import collections
import heapq

import random
from typing import List

from dataStructure.common import ListNode, TreeNode

from math import gcd, inf
from functools import reduce
from collections import *

from functools import reduce


class Solution:
    def goShopping(self, priceA: List[int], priceB: List[int]) -> int:
        arr = [[a, b] for a, b in zip(priceB, priceA)]
        arr.sort(key=lambda it: -it[0])
        n = len(arr)
        f = [[[inf] * 3 for _ in range(3)] for _ in range(n + 1)]
        f[0][0][0] = 0
        for i in range(n):
            a, b = arr[i]
            for j in range(3):
                for k in range(3):
                    v = inf
                    if k > 0:
                        v = f[i][j][k - 1] + b
                    v = min(v, f[i][(j - 1) % 3][k] + (0 if j == 0 else a))
                    f[i + 1][j][k] = v
        ans = inf
        for i in range(3):
            for j in range(3):
                ans = min(ans, f[n][i][j])

        f = [[[inf] * 4 for _ in range(3)] for _ in range(n + 1)]
        f[0][0][0] = 0
        for i in range(n):
            a, b = arr[i]
            a *= 10
            # b *= 10
            for j in range(3):
                for k in range(4):
                    v = inf
                    if k > 0:
                        v = f[i][j][k - 1] + b * 7
                    if k == 3:
                        v = min(f[i][j][k] + b * 7, v)
                    v = min(v, f[i][(j - 1) % 3][k] + (0 if j == 0 else a))
                    f[i + 1][j][k] = v
        # print(f)
        for i in range(3):
            if f[n][i][3] < inf:
                # print(f[n][i][3])
                ans = min(ans, int(f[n][i][3] // 10))

        return ans