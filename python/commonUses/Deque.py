# BFS

# 407. 接雨水 II
import collections
import heapq
from typing import List


# 接雨水II  dijkstra   优先队列
class Solution407:
    def trapRainWater(self, heightMap: List[List[int]]) -> int:
        m, n, ans = len(heightMap), len(heightMap[0]), 0
        visited = [[False] * n for _ in range(m)]
        dirs = [(0, 1), (1, 0), (0, -1), (-1, 0)]
        pq = []
        for i in range(1, n - 1):
            # pq 一个list  用heapq.heappush(pq, 添加   heapq.heappop(pq)弹出
            heapq.heappush(pq, (heightMap[0][i], 0, i))  # 优先队列 priority queue
            heapq.heappush(pq, (heightMap[m - 1][i], m - 1, i))
        for i in range(1, m - 1):
            heapq.heappush(pq, (heightMap[i][0], i, 0))
            heapq.heappush(pq, (heightMap[i][n - 1], i, n - 1))  # () 里面三元组
        while pq:  # 可以直接这样判空
            h, x, y = heapq.heappop(pq)  # 三元组
            for dx, dy in dirs:
                nx, ny = x + dx, y + dy
                if 0 < nx < m - 1 and 0 < ny < n - 1 and not visited[nx][ny]:
                    visited[nx][ny] = True
                    ans += max(0, h - heightMap[nx][ny])
                    heapq.heappush(pq, (max(h, heightMap[nx][ny]), nx, ny))
        return ans


# stack

class Solution946(object):
    def validateStackSequences(self, pushed, popped):
        j = 0
        stack = []
        for x in pushed:
            stack.append(x)
            while stack and j < len(popped) and stack[-1] == popped[j]:
                stack.pop()
                j += 1

        return j == len(popped)


# deque
class MyStack:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.queue1 = collections.deque()
        self.queue2 = collections.deque()

    def push(self, x: int) -> None:
        """
        Push element x onto stack.
        """
        self.queue2.append(x)
        while self.queue1:
            self.queue2.append(self.queue1.popleft())
        self.queue1, self.queue2 = self.queue2, self.queue1

    def pop(self) -> int:
        """
        Removes the element on top of the stack and returns that element.
        """
        return self.queue1.popleft()

    def top(self) -> int:
        """
        Get the top element.
        """
        return self.queue1[0]

    def empty(self) -> bool:
        """
        Returns whether the stack is empty.
        """
        return not self.queue1
