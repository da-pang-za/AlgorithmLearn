package com.dpz.dataStructure;

/** todo
 * 线段树
 * 线段树的用处就是，对编号连续的一些点进行修改或者统计操作，修改和统计的复杂度都是O(log2(n)).
 * 线段树中，每个点代表一条线段（区间）
 * <p>
 * 用线段树统计的东西，必须符合区间加法
 * 符合区间加法的例子：
 * 数字之和——总数字之和 = 左区间数字之和 + 右区间数字之和
 * 最大公因数(GCD)——总GCD = gcd( 左区间GCD , 右区间GCD );
 * 最大值——总最大值=max(左区间最大值，右区间最大值)
 * 不符合区间加法的例子：
 * 众数——只知道左右区间的众数，没法求总区间的众数
 */


public abstract class SegmentTree {

    int maxn;//元素总个数 / 区间长度
    int[] Sum, Add;//Sum求和，Add为懒惰标记
    int[] A;//存原数组数据下标[1,n]
    int n;

    SegmentTree(int maxn) {
        this.maxn = maxn;
        Sum = new int[maxn << 2];
        Add = new int[maxn << 2];
        A = new int[maxn];
        Build(1, maxn - 1, 1);
    }

    //PushUp函数更新节点信息 ，这里是求和


    /**
     * Build 建树
     */
    void Build(int l, int r, int rt) { //l,r表示当前节点区间，rt表示当前节点编号
        if (l == r) {//若到达叶节点
            Sum[rt] = A[l];//储存数组值
            return;
        }
        int m = (l + r) >> 1;
        //左右递归
        Build(l, m, rt << 1);
        Build(m + 1, r, rt << 1 | 1);
        //更新信息
        PushUp(rt);
    }

    /**
     * 上推    根据子节点更新当前节点
     *
     * @param rt 父节点下标
     */
    void PushUp(int rt) {
        Sum[rt] = Sum[rt << 1] + Sum[rt << 1 | 1];//左右相加
    }

    /**
     * 点修改
     * 假设A[L]+=C
     */
    void Update(int L, int C, int l, int r, int rt) {//l,r表示当前节点区间，rt表示当前节点编号
        if (l == r) {//到叶节点，修改
            Sum[rt] += C;
            return;
        }
        int m = (l + r) >> 1;
        //先更新子节点
        //根据条件判断往左子树调用还是往右
        if (L <= m) Update(L, C, l, m, rt << 1);
        else Update(L, C, m + 1, r, rt << 1 | 1);
        PushUp(rt);//更新当前节点
    }

    /**
     * 区间修改
     * 假设A[L,R]+=C
     */
    void Update(int L, int R, int C, int l, int r, int rt) {//L,R表示操作区间，l,r表示当前节点区间，rt表示当前节点编号
        //如果本区间完全在操作区间[L,R]以内
        //即当前整个区间都需要修改
        if (L <= l && r <= R) {
            Sum[rt] += C * (r - l + 1);//更新数字和，向上保持正确
            //打标记  下面的子区间不动
            Add[rt] += C;//增加Add标记，表示本区间的Sum正确，子区间的Sum仍需要根据Add的值来调整
            return;
        }
        int m = (l + r) >> 1;
        PushDown(rt, l, r);//下推标记  这个是之前的标记
        //这里判断左右子树跟[L,R]有无交集，有交集才递归
        if (L <= m) Update(L, R, C, l, m, rt << 1);
        if (R > m) Update(L, R, C, m + 1, r, rt << 1 | 1);
        PushUp(rt);//更新本节点信息
    }

    /**
     *下推lazy标记  同时更新当前层，清除当前层标记
     * 查找{@link #Query}和更新{@link #Update}时使用
     *
     * */
    void PushDown(int rt, int l, int r) {
        //l
        if (Add[rt] != 0) {
            //下推标记  下层加上add标记
            Add[rt << 1] += Add[rt];
            Add[rt << 1 | 1] += Add[rt];

            int m = (l + r) >> 1;
            //下推后 更新sum值  并清除标记
            //修改子节点的Sum使之与对应的Add相对应
            Sum[rt << 1] += Add[rt] * (m - l + 1);
            Sum[rt << 1 | 1] += Add[rt] * (r-m);
            //清除本节点标记
            Add[rt] = 0;
        }
    }

    /**
     * 区间查询
     */
    int Query(int L, int R, int l, int r, int rt) {//L,R表示查询区间，l,r表示当前节点区间，rt表示当前节点编号
        if (L <= l && r <= R) {
            //在区间内，直接返回   这个是结果的一部分   区间加法加起来
            return Sum[rt];
        }
        int m = (l + r) >> 1;
        //下推标记，否则Sum可能不正确
        PushDown(rt, m - l + 1, r - m);

        //累计答案
        int ANS = 0;
        //左侧部分
        if (L <= m) ANS += Query(L, R, l, m, rt << 1);
        //右侧部分
        if (R > m) ANS += Query(L, R, m + 1, r, rt << 1 | 1);
        return ANS;
    }
}
//todo  线段树 非数组实现
