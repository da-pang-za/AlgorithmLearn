import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;


class Solution {

    int min;

    public int minimumScore(int[] nums, int[][] edges) {
        ArrayList<Integer>[] adj = new ArrayList[nums.length];
        for (int i = 0; i < nums.length; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        int ans = Integer.MAX_VALUE;
        for (int[] edge : edges) {
            int xor0 = dfs(edge[0], edge[1], 0, 0, nums, adj),
                    xor1 = dfs(edge[1], edge[0], 0, 0, nums, adj);
            min = Integer.MAX_VALUE;
            dfs(edge[0], edge[1], xor0, xor1, nums, adj);
            dfs(edge[1], edge[0], xor1, xor0, nums, adj);
            ans = Math.min(ans, min);
        }
        return ans;
    }

    private int dfs(int i, int parent, int xor0, int xor1, int[] nums, ArrayList<Integer>[] list) {
        int xor = nums[i];
        for (int j : list[i]) {
            if (j != parent) {
                int curr = dfs(j, i, xor0, xor1, nums, list);
                min = Math.min(min,
                        Math.max(curr, Math.max(curr ^ xor0, xor1)) - Math.min(curr, Math.min(curr ^ xor0, xor1)));
                xor ^= curr;
            }
        }
        return xor;
    }
}