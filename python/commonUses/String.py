# string  拼接  取其中的位  遍历 todo

# char   数组存char 26个字母举例 todo
import collections
import heapq
from collections import defaultdict
from typing import List


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


# 767. 重构字符串
class Solution767:
    def reorganizeString(self, S: str) -> str:
        res = ""
        counter = collections.Counter(S)
        # 边界条件
        if max(counter.values()) > (len(S) + 1) // 2:
            return res

        # 将字母添加到堆中
        pq = []
        for key, val in counter.items():
            # 都是正数  取反 相当于从大到小排序
            heapq.heappush(pq, (-val, key))

        prev = (0, None)

        # 开始重构字符串
        while pq:
            v, k = heapq.heappop(pq)
            res += k  # 直接+=拼接
            if prev[0] < 0:
                heapq.heappush(pq, prev)
            prev = (v + 1, k)

        return res

#获取字符集合
print(set("aabbbcdde"))  #{'c', 'a', 'd', 'e', 'b'}