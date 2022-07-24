package com.dpz.dataStructure;

/**
 * 树状数组
 * 模板题：307
 * 参考资料：https://zhuanlan.zhihu.com/p/93795692
 * 注意：维护的区间index从1开始
 */

public
class BIT {
    //tree数组中 下标i 维护 (i-lowbit(i),i]的和
    int[] tree;

    public BIT(int n) {
        tree = new int[n + 1];
    }

    //得到最低位的1    xxxx10000-> 10000
    public int lowbit(int x) {
        return x & -x;
    }

    //查询[1,x]累和  拼接区间 每次去掉最低位的1
    public int query(int x) {
        int ans = 0;
        for (int i = x; i > 0; i -= lowbit(i)) ans += tree[i];
        return ans;
    }

    /**
     * 更新所有包含x的区间 每个区间都是(i-lowbit(i),i]
     * eg:100110   每次加一个lowbit(i) 刚好是所求区间 巧妙!
     * (10010  ,  100110]   接着找包含i的更大的上界
     * (100000 ,  101000]
     * (100000 ,  110000]
     * (0      , 1000000]
     */
    public void add(int x, int u) {
        assert x>0;//注意add 0 会死循环
        for (int i = x; i < tree.length; i += lowbit(i)) tree[i] += u;
    }
}
