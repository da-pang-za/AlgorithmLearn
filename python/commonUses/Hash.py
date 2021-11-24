# hash


from typing import List


# python hashmap
class Solution2:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        # hashSet = dict()
        map = {}
        for i, num in enumerate(nums):  # 同时获取下标和数字
            if map.__contains__(target - num):
                return [map[target - num], i]
            map[nums[i]] = i
        return []


# hashset
s = set()


# 独一无二的出现次数
class Solution1207:
    def uniqueOccurrences(self, arr: List[int]) -> bool:
        d = {}
        for i in arr:
            # get(i, 0)相当于getOrDefault(i,0)
            d[i] = d.get(i, 0) + 1
        s = [d[i] for i in d]
        return len(set(s)) == len(s)
