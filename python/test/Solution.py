class Solution:
    def kInversePairs(self, n: int, k: int) -> int:
        mod: int = 10 ** 9 + 7
        dp = [[0 for _ in range(k + 1)] for _ in range(n + 1)]
        sums = [[0 for _ in range(k + 1)] for _ in range(n + 1)]
        for i in range(1,n + 1):
            dp[i][0] = 1
            sums[i][0] = 1
            for j in range(1,k + 1):
                if j - i >= 0:
                    dp[i][j] = (sums[i - 1][j] - sums[i - 1][j - i] + mod) % mod
                else:
                    dp[i][j] = sums[i - 1][j]
                sums[i][j] = (dp[i][j] + sums[i][j - 1]) % mod
        return dp[n][k]

if __name__ == '__main__':
    solution = Solution()
    print(solution.kInversePairs(5,7))
