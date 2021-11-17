# BFS

# 407. 接雨水 II
import heapq
from typing import List


# 接雨水II  dijkstra
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
