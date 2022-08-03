import java.util.*;


class Solution {
    public List<Integer> minSubsequence(int[] nums) {
        int sum= Arrays.stream(nums).sum();
        Arrays.sort(nums);
        List<Integer>ans=new ArrayList<>();
        int s=0;
        for(int i=nums.length-1;i>0;i--){
            ans.add(nums[i]);
            s+=nums[i];
            sum-=nums[i];
            if(s>sum)break;
        }
        return ans;
    }
}