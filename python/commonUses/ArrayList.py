import functools
from typing import List


# for循环写法  数组创建 array
class Solution1:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        n = len(nums)
        for i in range(n):
            for j in range(i + 1, n):
                if nums[i] + nums[j] == target:
                    return [i, j]

        return []


# todo 数组切片
# 合并两个有序数组
class Solution88:
    def merge(self, nums1: List[int], m: int, nums2: List[int], n: int) -> None:
        nums1[m:] = nums2  # 两个数组 拼接
        nums1.sort()  # 数组排序


# 179 最大数
class Solution179:
    def largestNumber(self, nums: List[int]) -> str:
        strs = map(str, nums)

        def cmp(a, b):
            if a + b == b + a:
                return 0
            elif a + b > b + a:
                return 1
            else:
                return -1

        strs = sorted(strs, key=functools.cmp_to_key(cmp), reverse=True)
        return ''.join(strs) if strs[0] != '0' else '0'


# 自定义排序
# https://blog.csdn.net/song_wheaver/article/details/10933634
print("==自定义排序==")
nums = [_ for _ in range(1, 9)]
print(nums)


def mycmp(a, b):
    return b - a


# 以下几句效果相同
nums.sort(key=functools.cmp_to_key(mycmp))  # 定义复杂排序
# nums.sort(key=lambda i: -i)
# nums.sort(reverse=True)
print(nums)


# list操作
# 全排列II
class Solution47:
    def permuteUnique(self, nums: List[int]) -> List[List[int]]:
        # 函数内部定义函数
        def dfs(nums, size, depth, path, used, res):
            if depth == size:
                res.append(path.copy())  # 复制数组/list   add操作
                return
            for i in range(size):
                if not used[i]:
                    if i > 0 and nums[i] == nums[i - 1] and not used[i - 1]:
                        continue
                    used[i] = True
                    path.append(nums[i])
                    dfs(nums, size, depth + 1, path, used, res)
                    used[i] = False
                    path.pop()  # 直接用pop删除最后一个

        size = len(nums)
        if size == 0:
            return []
        nums.sort()  # 排序
        used = [False] * len(nums)
        res = []
        dfs(nums, size, 0, [], used, res)
        return res


# 62uniquePaths  DP

class Solution62:
    def uniquePaths(self, m: int, n: int) -> int:
        # 创建二维数组（mxn）的同时赋值   第一行为1 第一列为1
        # m=4 n=3 举例
        f = [[1] * n] + [[1] + [0] * (n - 1) for _ in range(m - 1)]
        print(f)  # [[1, 1, 1], [1, 0, 0], [1, 0, 0], [1, 0, 0]]
        for i in range(1, m):
            for j in range(1, n):
                f[i][j] = f[i - 1][j] + f[i][j - 1]
        return f[m - 1][n - 1]
