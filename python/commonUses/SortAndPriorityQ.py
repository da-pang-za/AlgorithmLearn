import heapq
from typing import List


# 1834. 单线程 CPU
class Solution:
    def getOrder(self, tasks: List[List[int]]) -> List[int]:
        n = len(tasks)
        indices = list(range(n))
        indices.sort(key=lambda x: tasks[x][0])

        ans = list()
        # 优先队列
        q = list()
        # 时间戳
        timestamp = 0
        # 数组上遍历的指针
        ptr = 0

        for i in range(n):
            # 如果没有可以执行的任务，直接快进
            if not q:
                timestamp = max(timestamp, tasks[indices[ptr]][0])
            # 将所有小于等于时间戳的任务放入优先队列
            while ptr < n and tasks[indices[ptr]][0] <= timestamp:
                heapq.heappush(q, (tasks[indices[ptr]][1], indices[ptr]))
                ptr += 1
            # 选择处理时间最小的任务
            process, index = heapq.heappop(q)
            timestamp += process
            ans.append(index)

        return ans


if __name__ == '__main__':
    solution = Solution()
    print(solution.getOrder([[1, 2], [2, 4], [3, 2], [4, 1]]))
