package com.dpz.leetcode;

import java.util.*;

public class Solution {


    public String multiply1(String num1, String num2) {
        int m = num1.length(), n = num2.length();
        int[] result = new int[m + n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i + j + 1] += (num1.charAt(i) - '0') *
                        (num2.charAt(j) - '0');
            }

        }
        int carry = 0;
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] += carry;
            carry = result[i] / 10;
            result[i] %= 10;

        }
        StringBuilder ans = new StringBuilder();
        if (result[0] != 0) ans.append(result[0]);
        for (int i = 1; i < result.length; i++) {
            ans.append(result[i]);

        }
        return ans.toString();

    }

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";
        int carry;
        String result = "0";
        for (int i = num1.length() - 1; i >= 0; i--) {
            carry = 0;
            StringBuffer sb = new StringBuffer();
            for (int j = num2.length() - 1; j >= 0; j--) {
                int sum = carry + (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                carry = sum / 10;
                sb.append(sum % 10);

            }
            if (carry != 0) sb.append(carry);
            String row = sb.reverse().toString();

            //补0
            for (int j = num1.length() - 1 - i; j > 0; j--) {
                row += "0";
            }
            result = stringAdd(result, row);
        }
        return result;


    }

    public String stringAdd(String num1, String num2) {
        int carry = 0;
        String result = "0";
        StringBuffer sb = new StringBuffer();
        int i, j;
        for (i = num1.length() - 1, j = num2.length() - 1;
             i >= 0 && j >= 0; i--, j--) {

            int sum = num1.charAt(i) - '0' + num2.charAt(j) - '0' + carry;
            carry = sum / 10;
            sb.append(sum % 10);


        }

        for (; i >= 0; i--) {
            int sum = num1.charAt(i) - '0' + carry;
            carry = sum / 10;
            sb.append(sum % 10);

        }
        for (; j >= 0; j--) {
            int sum = num2.charAt(j) - '0' + carry;
            carry = sum / 10;
            sb.append(sum % 10);

        }
        if (carry != 0) {
            sb.append(carry);
        }
        return sb.reverse().toString();

    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || head.next == null || m == n) return head;
        //找到m n的位置 保存前一个 和 后一个
        ListNode f, ff, b, bb;
        int flag = 0;
        ff = head;
        int index = 0;
        ListNode head0 = new ListNode(0);
        head0.next = head;
        head = head0;
        while (index < m - 1) {
            head = head.next;
            index++;

        }
        //此时head指向m的前一个
        ListNode h1 = head;
        head = head.next;
        index++;
        ListNode n0 = null, n1 = null, nn = null;
        while (index <= n) {
            n0 = head.next;
            if (n0 != null)
                nn = n0.next;

            head.next = n1;
            n1 = n0;
            if (index < n) {
                n0.next = head;


                head = nn;
            }

            index += 2;
        }
        ListNode t1;
        if (index == n + 1) {
            head = n0;
            //head指向n
            t1 = nn;
        } else {
            t1 = n0;
        }

        h1.next.next = t1;
        h1.next = head;
        return head0.next;


    }

    //79. 单词搜索
    public boolean exist(char[][] board, String word) {


        return false;
    }

    //94. 二叉树的中序遍历  迭代实现  左中右
    public List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> list = new ArrayList<>();
        while (root != null || stack.isEmpty()) {
            if (root == null) {
                root = stack.pop();
                list.add(root.val);
                root = root.right;
            } else {
                stack.push(root);
                root = root.left;

            }

        }
        return list;
    }

    //77. 组合
//    //给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
//    public List<List<Integer>> combine(int n, int k) {
//        List<List<Integer>> lists = new ArrayList<>();
//        if (k > n) return lists;
//        //当前最后一个数   还差几个数   n  list
//        for (int i = 1; i - 1 + k <= n; i++) {
//            combineHelp(i, k, n, new ArrayList<>(), lists);
//        }
//        return lists;
//
//
//    }
//
//    void combineHelp(int index, int need, int n, List<Integer> list, List<List<Integer>> lists) {
//
//        if (index - 1 + need > n) return;
//        list.add(index);
//        if (need == 1) {
//            lists.add(list);
//            return;
//        }
//        for (int i = index + 1; i <= n; i++) {
//            combineHelp(i, need - 1, n, new ArrayList<>(list), lists);
//        }
//
//
//    }

    //71. 简化路径
    public String simplifyPath(String path) {
        String ans = "";
        //扫描可能遇到的符号   /(/  //)  .(.  ..)  abc
        //   ...可以作为文件名
        Stack<String> stack = new Stack<>();
        String last = "/";
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '.') {
                if (last.equals("."))//     /..
                {
                    if (i + 1 < path.length() && path.charAt(i + 1) != '/')//
                    {
                        last += '.';
                    } else if (!stack.isEmpty()) stack.pop();
                } else if (last.equals("/"))//     /.
                {
                    last = ".";
                } else last += ".";     //   zx.
            } else if (path.charAt(i) == '/') {
                if (!last.equals(".") && !last.equals("/")) {
                    stack.push(last);
                }
                last = "/";
            } else {
                if (last.equals("/")) {
                    last = "" + path.charAt(i);
                } else last += path.charAt(i);
            }
        }
        if (!last.equals(".") && !last.equals("/")) {
            stack.push(last);
        }
        Stack<String> stack1 = new Stack<>();
        while (!stack.isEmpty()) {
            stack1.push(stack.pop());
        }
        while (!stack1.isEmpty()) {
            ans += "/" + stack1.pop();
        }
        if (ans.length() == 0) return "/";
        return ans;

    }

    public List<List<String>> groupAnagrams(String[] strs) {

        List<List<String>> ans = new ArrayList<>();
        //一个list保存字母信息
        HashMap<String, List<String>> map = new HashMap<>();
        int[] tmp;
        if (strs.length == 0) return ans;
        tmp = new int[26];

        for (int i = 0; i < strs.length; i++) {

            tmp = new int[26];
            //统计字母
            for (int j = 0; j < strs[i].length(); j++) {
                tmp[strs[i].charAt(j) - 'a']++;
            }
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < 26; j++) {
                if (tmp[j] != 0) {
                    sb.append((char) ('a' + j));
                    sb.append(tmp[j]);
                }
            }
            String key = sb.toString();

            List list = map.getOrDefault(key, new ArrayList<>());
            list.add(strs[i]);
            map.put(key, list);

        }
//        for (Map.Entry e:map.entrySet()
//             ) {
//            ans.add((List<String>) e.getValue());
//        }
        //return ans;
        return new ArrayList<>(map.values());

    }

    //91. 解码方法
    public int numDecodings(String s) {
        int[] num = new int[s.length() + 1];
        num[s.length()] = 1;
        if (s.charAt(s.length() - 1) != '0') num[s.length() - 1] = 1;
        for (int i = s.length() - 2; i >= 0; i--) {
            if (s.charAt(i) == '1')
                num[i] = num[i + 1] + num[i + 2];
            else if (s.charAt(i) == '2') {
                if (s.charAt(i + 1) > '6')
                    num[i] = num[i + 2];
                else num[i] = num[i + 1] + num[i + 2];

            } else if (s.charAt(i) == '0') {
                num[i] = 0;
            } else num[i] = num[i + 1];


        }
        return num[0];
    }
//        if (s.length() == 0) return 1;
//        if (s.charAt(0) == '0') return 0;
//        if (s.length() == 1) return 1;
//        //至少两位
//        if (s.charAt(0) == '1') {
//            return numDecodings(s.substring(1)) + numDecodings(s.substring(2));
//        } else if (s.charAt(0) == '2') {
//            if (s.charAt(1) > '6')//不能拆开
//            {
//                return numDecodings(s.substring(2));
//            } else return numDecodings(s.substring(1)) + numDecodings(s.substring(2));
//        } else return numDecodings(s.substring(1));

//        //切下来的部分有单独的 0   则sum为0
//        //关心切割后长度大于1的部分
//        List<Integer>list=new ArrayList<>();
//        list.add(-1);
//        for (int i = 0; i <s.length()-1 ; i++) {
//             if(s.charAt(i)==2)
//            {
//                if((i+1<s.length()&&s.charAt(i+1)>'6'))//2后面大于6  切
//                    list.add(i);
//
//            }
//            else if(s.charAt(i)!=1)
//                list.add(i);
//
//
//        }
//        list.add(s.length()-1);

    //113. 路径总和 II
//    List<List<Integer>> lists = new ArrayList<>();
//
//    public List<List<Integer>> pathSum(TreeNode root, int sum) {
//        pathSumHelp(root, sum, new ArrayList<>());
//        return lists;
//    }
//
//    void pathSumHelp(TreeNode root, int sum, List<Integer> list) {
//        if (root == null) return;
//        list.add(root.val);
//        if (root.left == null && root.right == null) {
//            if (sum == root.val) lists.add(list);
//            return;
//        }
//        pathSumHelp(root.left, sum - root.val, new ArrayList<>(list));
//        pathSumHelp(root.right, sum - root.val, new ArrayList<>(list));
//
//    }

    //    解释：x&y  两个数中相同的位  相加除以2((1+1)/2=1) 还是原来的1
//    x^y  两个数中有一个为1的位 1+0=1   再移位除以2
//            剩下两个数都为0的位不用管
    int AverageBit(int x, int y) {
        return (x & y) + ((x ^ y) >> 1);
    }

    //542. 01 矩阵
    //给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。   曼哈顿距离
    // 两个相邻元素间的距离为 1 。

    public int[][] updateMatrix(int[][] matrix) {
        //原来为0的不变
        int r = matrix.length;
        int c = matrix[0].length;
        int[][] map = new int[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (matrix[i][j] != 0)
                    map[i][j] = r + c;
            }
        }
        for (int i = 0; i < r + c - 1; i++) {
            for (int j = 0; j < r; j++) {
                for (int k = 0; k < c; k++) {
                    if (map[j][k] == i) {
                        //四个方向


                        if (k - 1 >= 0) {
                            map[j][k - 1] = Math.min(map[j][k - 1], i + 1);
                        }
                        if (k + 1 < c) {
                            map[j][k + 1] = Math.min(map[j][k + 1], i + 1);
                        }

                        if (j + 1 < r) {
                            map[j + 1][k] = Math.min(map[j + 1][k], i + 1);
                        }
                        if (j - 1 >= 0) {
                            map[j - 1][k] = Math.min(map[j - 1][k], i + 1);
                        }

                    }
                }
            }

        }
        return map;

    }


    //721. 账户合并
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        //判断两个name有相同的邮箱
        AccountUnion accountUnion = new AccountUnion();
        return accountUnion.AccountsResult();
    }

    class AccountUnion {
        //用index来链接
        private Map<String, String> parent;
        Map<String, String> NamePtr;

        String find(String s) {//s所处的连通分量
            if (!parent.containsKey(s)) {
                parent.put(s, s);
            }
            if (!s.equals(find(s))) {
                parent.put(s, find(parent.get(s)));
            }
            return parent.get(s);
        }

        void union(List<String> account) {
            //需要判断它的每一项 属于哪个连通分量
            //name怎么存  最后指的那个项作为Key  name 作为val
            List<String> tmp = new ArrayList<>();
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                NamePtr.put(email, name);
                tmp.add(find(email));

            }
            for (int i = 0; i < tmp.size(); i++) {
                String email = tmp.get(i);
                for (int j = i + 1; j < tmp.size(); j++) {
                    if (!email.equals(tmp.get(j))) {
                        parent.put(email, tmp.get(j));
                    }
                }

            }

        }

        List<List<String>> AccountsResult() {
            List<List<String>> lists = new ArrayList<>();
            return lists;
        }


    }
    //判断两个下标是否可以合并

    //998. 最大二叉树 II    //返回安放val后的根
    public TreeNode insertIntoMaxTree(TreeNode root, int val) {


        TreeNode node = new TreeNode(val);
        if (root == null) return node;
        if (val > root.val) {
            node.left = root;
            return node;
        }

        root.right = insertIntoMaxTree(root.right, val);
        return root;


    }
    //12. 整数转罗马数字   99   "XCIX"  XC90  IX9
//    I             1
//    V             5
//    X             10
//    L             50
//    C             100
//    D             500
//    M             1000
    //特殊规则
//I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
//X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
//C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
//罗马数字时相加得到的    把数拆成加法  先拆大数

    // 1-3  1
    //4   4
    // 5   5
    //6-8   5 +  1-3
    //9    9
    //10   10

    public String intToRoman(int num) {

        StringBuilder sb = new StringBuilder();
        int val = num;
        //拆数字  拆出最大的
        while (val > 0) {
            if (val >= 1000) {
                int count = val / 1000;
                for (int i = 0; i < count; i++) {
                    sb.append("M");
                }
                val -= count * 1000;
            } else if (val >= 900) {
                val -= 900;
                sb.append("CM");
            } else if (val >= 500) {
                val -= 500;
                sb.append("D");
            } else if (val >= 400) {
                val -= 400;
                sb.append("CD");
            } else if (val >= 100) {//399
                int count = val / 100;
                for (int i = 0; i < count; i++) {
                    sb.append("C");
                }
                val -= count * 100;
            } else if (val >= 90) {//99
                val -= 90;
                sb.append("XC");
            } else if (val >= 50) {//89
                val -= 50;
                sb.append("L");
            } else if (val >= 40) {//49
                val -= 40;
                sb.append("XL");
            } else if (val >= 10) {//31
                int count = val / 10;
                for (int i = 0; i < count; i++) {
                    sb.append("X");
                }
                val -= count * 10;
            } else if (val >= 9) {//9
                val -= 9;
                sb.append("IX");
            } else if (val >= 5) {//8
                val -= 5;
                sb.append("V");
            } else if (val >= 4) {//8
                val -= 4;
                sb.append("IV");
            } else {//3

                for (int i = 0; i < val; i++) {
                    sb.append("I");
                }
                val = 0;

            }


        }


        return sb.toString();
    }

    //    1947. 移除最多的同行或同列石头    并查集  以前从来没用过
    public int removeStones(int[][] stones) {
        if (stones.length < 2) return 0;

        UnionFind unionFind = new UnionFind();
        for (int[] stone : stones
        ) {
            //内部类的private变量外部类也可以用！！
            unionFind.union(stone[0] + 10001, stone[1]);
        }
        return stones.length - unionFind.getCount();


    }

    static class UnionFind {//静态内部类   居然可以实例化 ！！
        private Map<Integer, Integer> parent;//图的链接
        private int count;//连通分量个数

        UnionFind() {
            parent = new HashMap<>();//value是父亲   key是儿子
            count = 0;
        }

        public int getCount() {
            return count;
        }

        private int find(int x)//查找x所在的连通分量
        {
            //查x属于的图  没有则增加一个图

            //初始化
            if (!parent.containsKey(x))//如果这个点没作为左侧(parent)节点
            {
                parent.put(x, x);//父节点是自己
                count++;
            }
            //再次遇到x  非初始化
            if (x != parent.get(x)) //x连的不是自己   则x连到它连的点的连通分量上
            {
                parent.put(x, find(parent.get(x)));
            }
            return parent.get(x);//一个连通分量内部 所指向的一定保证是相同的才行
        }

        public void union(int x, int y) {
            //横坐标和纵坐标
            int X = find(x);
            int Y = find(y);
            //  X Y是被指的最后一个   链的末端  祖宗节点
            //横坐标和纵坐标所属的连通分量   如果不一样则连到一起
            if (X == Y) return;
            parent.put(X, Y);//
            count--;
        }
    }

    //    689. 十-二进制数的最少数目
    public int minPartitions(String n) {
        //0  1  10 11 111 101...
        //方程
        //n位数
        int a = 0;
        //(a++)+=a;
        ///(++a)+=a;
        char max = '0';
        for (char c : n.toCharArray()
        ) {
            if (c > max) max = c;
        }
        return max - '0';

    }

    public boolean searchMatrix(int[][] matrix, int target) {
        //二分查找
        int rlen = matrix.length;
        int clen = matrix[0].length;
        int len = rlen * clen;

        int row;
        int col;
        int left = 0;
        int right = len - 1;
        int mid;
        while (left <= right) {
            mid = (left + right) / 2;
            row = mid / clen;
            col = mid % clen;
            if (target == matrix[row][col]) return true;
            else if (target < matrix[row][col])
                right = mid - 1;
            else left = mid + 1;


        }

        return false;

    }

    public int lengthOfLIS(int[] nums) {
        int ans = 0;
        //给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
        //最长为len(nums)
        //选择某个数  不选择某个数  上次选择的数
        //LIS(nums)=LIS(num[len-1]+最后一个数大于之前序列的最后一个数)
        //返回序列最大数 序列长度  数组长
        int[] maxlen = new int[nums.length];
        //以某位置为结尾
        for (int i = 0; i < nums.length; i++) {
            maxlen[i] = 1;
        }

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j])
                    maxlen[i] = Math.max(maxlen[i], maxlen[j] + 1);
            }
            ans = Math.max(ans, maxlen[i]);
        }
        return ans;

    }

//    public int[] countzeroesones(String s) {
//        int[] c = new int[2];
//        for (int i = 0; i < s.length(); i++) {
//            c[s.charAt(i) - '0']++;
//        }
//        return c;
//    }

    //给定一个无序的数组，找出数组在排序之后，相邻元素之间最大的差值。
    //如果数组元素个数小于 2，则返回 0。
    /*
    你可以假设数组中所有元素都是非负整数，且数值在 32 位有符号整数范围内。
    请尝试在线性时间复杂度和空间复杂度的条件下解决此问题
     */
    public int maximumGap(int[] nums) {
        //基数排序  先根据个位分别放到10个桶里 然后 组合起来 按十位继续  直到所有位都排了一遍
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
        long exp = 1;//a当前处理的位  1  10  100  1000
        int[] buf = new int[n];
        int maxVal = Arrays.stream(nums).max().getAsInt();//最大值   需要知道最大值的位数

        while (maxVal >= exp) {
            int[] cnt = new int[10];
            for (int i = 0; i < n; i++) {
                int digit = (nums[i] / (int) exp) % 10;//对应位的值
                cnt[digit]++;
            }
            for (int i = 1; i < 10; i++) { //累计个数
                cnt[i] += cnt[i - 1];
            }
            for (int i = n - 1; i >= 0; i--) {
                int digit = (nums[i] / (int) exp) % 10;
                buf[cnt[digit] - 1] = nums[i];//放回去
                cnt[digit]--;
            }
            System.arraycopy(buf, 0, nums, 0, n);
            exp *= 10;
        }

        int ret = 0;
        for (int i = 1; i < n; i++) {
            ret = Math.max(ret, nums[i] - nums[i - 1]);
        }
        return ret;
    }

    //1395. 统计作战单位数
    int numTeamssum = 0;

    public int numTeams(int[] rating) {
        for (int i = 0; i < rating.length; i++) {
            numTeams(true, i, i + 1, 1, rating);//比当前大
            numTeams(false, i, i + 1, 1, rating);//比当前小
        }
        return numTeamssum;


    }

    void numTeams(boolean flag, int cur, int exp, int count, int[] rating) {
        if (count == 3) {
            numTeamssum++;
            return;
        }
        if (rating.length - exp >= 3 - count) {
            if (flag == rating[cur] < rating[exp]) {
                numTeams(flag, exp, exp + 1, count + 1, rating);//把exp加进来
            }

            numTeams(flag, cur, exp + 1, count, rating);//跳过exp

        }

    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    //1551. 使数组中所有元素相等的最小操作数
    public int minOperations(int n) {
        return n * n / 4;

    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int n = obstacleGrid.length;
        int m = obstacleGrid[0].length;
        int[][] mn = new int[n][m];
        for (int i = n - 1; i >= 0; i--) {
            if (obstacleGrid[i][m - 1] == 0)
                mn[i][m - 1] = 1;
            else break;//前面的全是0

        }
        for (int i = m - 1; i >= 0; i--) {
            if (obstacleGrid[n - 1][i] == 0)
                mn[n - 1][i] = 1;
            else break;
        }
        //从下往上  从右往左
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                if (obstacleGrid[j][i] == 0)
                    mn[j][i] = mn[j + 1][i] + mn[j][i + 1];
            }

        }
        return mn[0][0];


    }

//    //    给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
////
////    candidates 中的每个数字在每个组合中只能使用一次。
//    List<List<Integer>> combinationSum2lists = new ArrayList<>();
//    List<int[]> freq = new ArrayList<int[]>();
//
//    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
//        //需要知道当前的sum
//        //需要知道当前拿的数
//        //不能重复组合
//
//        Arrays.sort(candidates);
//        for (int num : candidates) {
//            int size = freq.size();
//            if (freq.isEmpty() || num != freq.get(size - 1)[0]) {
//                freq.add(new int[]{num, 1});
//            } else {
//                ++freq.get(size - 1)[1];
//            }
//        }
//        combinationSum2(new ArrayList<>(), 0, target);
//
//        return combinationSum2lists;
//
//    }
//
//    void combinationSum2(List<Integer> list, int i, int target) {
//        if (0 == target) {
//            combinationSum2lists.add(new ArrayList<>(list));
//            return;
//        }
//        if (i == freq.size() || target < freq.get(i)[0]) return;
//        //添加当前数  或者不添加
//        int most = Math.min(target / freq.get(i)[0], freq.get(i)[1]);
//
//        combinationSum2(list, i + 1, target);
//        for (int j = 1; j <= most; j++) {
//            list.add(freq.get(i)[0]);
//            combinationSum2(list, i + 1, target - freq.get(i)[0] * j);
//
//        }
//        for (int j = 1; j <= most; j++) {
//            list.remove(list.size() - 1);
//        }
//
//
//    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //    给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
//    k 是一个正整数，它的值小于或等于链表的长度。
//    如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
    public ListNode reverseKGroup(ListNode head, int k) {
//        你的算法只能使用常数的额外空间。
//        你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
        ListNode headpre = new ListNode(0);
        headpre.next = head;
        ListNode node = head;
        ListNode pre = headpre;
        ListNode tail = node;
        ListNode nex;
        while (node != null) {

            for (int i = 1; i < k; i++) {//k-1次
                tail = tail.next;
                if (tail == null) return headpre.next;//不够了 直接结束

            }
            nex = tail.next;
            pre.next = reverseLinkedList(node, tail);
            pre = node;
            node.next = nex;
            tail = node.next;
            node = tail;
        }
        return headpre.next;


    }
    //1658. 将 x 减到 0 的最小操作数

    public int minOperations(int[] nums, int x) {
        int maxPart = -1;
        int sum = Arrays.stream(nums).sum();
        int currentSum = 0;
        int left = 0;
        int right = 0;
        while (right < nums.length) {
            currentSum += nums[right++];
            while (currentSum > sum - x && left < nums.length) {
                currentSum -= nums[left++];
            }
            if (currentSum == sum - x) {
                maxPart = Math.max(maxPart, right - left);
            }
        }
        return maxPart == -1 ? -1 : nums.length - maxPart;
    }

    //57   插入区间
    public int[][] insert(int[][] intervals, int[] newInterval) {
        //找到newinterval的端点在哪两个区间内  也有可能在一个区间
        if (intervals.length == 0) return new int[][]{newInterval};
        List<int[]> list = new ArrayList<>();
        int left = newInterval[0];
        int right = newInterval[1];
        int leftindex = -1;
        int rightindex = intervals.length;
        for (int i = 0; i < intervals.length; i++) {
            if (right >= intervals[i][0] && right <= intervals[i][1]) {
                rightindex = i;
                break;
            }

        }
        for (int i = 0; i < intervals.length; i++) {
            if (left >= intervals[i][0] && left <= intervals[i][1]) {
                leftindex = i;
                break;
            }

        }
//        if(left>intervals[intervals.length-1][1])leftindex=intervals.length;
//        else if(right<intervals[0][0])rightindex=-1;
        //找到位置
        for (int i = 0; i < leftindex; i++) {
            list.add(intervals[i]);
        }
        list.add(new int[]{leftindex == -1 || leftindex == intervals.length ? left : intervals[leftindex][0],
                rightindex == -1 || rightindex == intervals.length ? right : intervals[rightindex][1]});
        for (int i = rightindex + 1; i < intervals.length; i++) {
            list.add(intervals[i]);

        }


        return list.toArray(new int[list.size()][]);

    }

    public static void main(String[] args) {  //一个main函数  public static void main(String[] args) 全都要有
        System.out.println("solution class");

    }

    //73. 矩阵置零   需要区分原来的零和改之后的零   使用常数空间
    public void setZeroes(int[][] matrix) {

    }

    //返回反转后的头指针
    ListNode reverseLinkedList(ListNode head, ListNode tail) {
        ListNode node = head.next;
        head = node;
        while (node != null) {

            if (node == tail) {

                node.next = head;

                break;
            } else {
                ListNode t = node.next;
                node.next = head;
                head = node;
                node = t;
            }


        }

        return tail;

    }

    //47.全排列
    //给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列
    boolean[] used;

//    public List<List<Integer>> permuteUnique(int[] nums) {
//        List<List<Integer>> peresult = new ArrayList<>();
//        int len = nums.length;
//        used = new boolean[len];
//        Arrays.sort(nums);
//        //在当前位置放一个数
//        putpermute(nums, 0, peresult, new ArrayList<>());
//        return peresult;
//
//    }

    void putpermute(int[] nums, int index, List<List<Integer>> r, List<Integer> l) {
        if (index == nums.length) {
            r.add(new ArrayList<>(l));

            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]))//用过了  或者 之前相同的数没用过
            {
                continue;
            }
            //可以用这个数
            l.add(nums[i]);
            used[i] = true;
            putpermute(nums, index + 1, r, l);
            l.remove(l.size() - 1);
            used[i] = false;
            //不用这个数  下一次循环

        }

    }


    public String addStrings(String num1, String num2) {
        int i = num1.length() - 1, j = num2.length() - 1, add = 0;
        StringBuffer ans = new StringBuffer();
        while (i >= 0 || j >= 0 || add != 0) {
            int x = i >= 0 ? num1.charAt(i) - '0' : 0;
            int y = j >= 0 ? num2.charAt(j) - '0' : 0;
            int result = x + y + add;
            ans.append(result % 10);
            add = result / 10;
            i--;
            j--;
        }
        ans.reverse();
        return ans.toString();
    }


//    public int[][] merge(int[][] intervals) {
//
//        //先根据左端点排序
//        Arrays.sort(intervals, (point1, point2) -> {
//            if (point1[0] > point2[0]) {//根据右端点排序
//                return 1;
//            } else if (point1[0] < point2[0]) {
//                return -1;
//            } else {
//                return 0;
//            }
//
//        });
//        int begin = 0;
//        int end = 0;
//        List<List<Integer>> list = new ArrayList<>();
//        for (int i = 0; i < intervals.length; i++) {
//            begin = intervals[i][0];
//            end = intervals[i][1];
//            while (i + 1 < intervals.length && (end >= intervals[i + 1][0])) {
//                if (end < intervals[i + 1][1])
//                    end = intervals[i + 1][1];
//                i++;
//
//            }
//            List<Integer> list1 = new ArrayList<>();
//            list1.add(begin);
//            list1.add(end);
//            list.add(list1);
//
//
//        }
//        int[][] r = new int[list.size()][2];
//        for (int i = 0; i < r.length; i++) {
//            r[i][0] = list.get(i).get(0);
//            r[i][1] = list.get(i).get(1);
//
//        }
//        return r;
//
//
//    }


//    public int uniquePaths(int m, int n) {
//        //当前位置 1 ,1
//        int[][] mn = new int[m][n];
//        for (int i = 0; i < n - 1; i++) {
//            mn[m - 1][i] = 1;
//
//        }
//        for (int i = 0; i < m - 1; i++) {
//
//            mn[i][n - 1] = 1;
//        }
//        //从下往上  从右往左
//        for (int i = n - 2; i >= 0; i--) {
//            for (int j = m - 2; j >= 0; j--) {
//
//                mn[j][i] = mn[j + 1][i] + mn[j][i + 1];
//            }
//
//        }
//        return mn[0][0];
//
//    }
//
//    public int uniquePaths(int m, int n, int x, int y) {
//        //当前位置 1 ,1
//        if (x == m || y == n) return 1;
//        return uniquePaths(m, n, x + 1, y) + uniquePaths(m, n, x, y + 1);
//
//    }

    //1302. 层数最深叶子节点的和
    public int deepestLeavesSum(TreeNode root) {
        //给你一棵二叉树，请你返回层数最深的叶子节点的和。
        //需要先算出树的深度

        //当前深度 是叶子+
        return deepestLeavesSum(TreeHeight(root), 1, root);

    }

    int deepestLeavesSum(int h, int cur, TreeNode root) {
        if (root == null) return 0;
        if (cur == h && isLeave(root)) return root.val;
        if (cur < h)
            return deepestLeavesSum(h, cur + 1, root.right) + deepestLeavesSum(h, cur + 1, root.left);
        return 0;
    }

    int TreeHeight(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(TreeHeight(root.left), TreeHeight(root.right));

    }

    boolean isLeave(TreeNode treeNode) {
        if (treeNode == null) return false;
        return treeNode.left == null && treeNode.right == null;
    }
//    1282. 用户分组
//    有 n 位用户参加活动，他们的 ID 从 0 到 n - 1，每位用户都 恰好 属于某一用户组。
//    给你一个长度为 n 的数组 groupSizes，其中包含每位用户所处的用户组的大小，
//    请你返回用户分组情况（存在的用户组以及每个组中用户的 ID）。
//
//    你可以任何顺序返回解决方案，ID 的顺序也不受限制。
//    此外，题目给出的数据保证至少存在一种解决方案。

    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        //所有人都一组   数组元素最大值 =len

        List<List<Integer>> lists = new ArrayList<>();

        for (int i = 0; i < groupSizes.length; i++) {
            lists.add(new ArrayList<>());
        }
        for (int i = 0; i < groupSizes.length; i++) {
            lists.get(groupSizes[i] - 1).add(i);
        }
        List<List<Integer>> lists1 = new ArrayList<>();
        for (int i = 0; i < groupSizes.length; i++) {
            int n = 0;
            List<Integer> l = new ArrayList<>();
            for (int j = 0; j < lists.get(i).size(); j++) {
                //i+1个一组
                l.add(lists.get(i).get(j));
                n++;
                if (n == i + 1) {
                    n = 0;
                    lists1.add(l);
                    l = new ArrayList<>();
                }
            }

        }
        return lists1;


    }

    //    654. 最大二叉树
//    给定一个不含重复元素的整数数组。一个以此数组构建的最大二叉树定义如下：
//
//    二叉树的根是数组中的最大元素。
//    左子树是通过数组中最大值左边部分构造出的最大二叉树。
//    右子树是通过数组中最大值右边部分构造出的最大二叉树。
//    通过给定的数组构建最大二叉树，并且输出这个树的根节点。
//    public TreeNode constructMaximumBinaryTree(int[] nums) {
//        return constructMaximumBinaryTree(nums, 0, nums.length - 1);
//
//    }
//
//    public TreeNode constructMaximumBinaryTree(int[] nums, int begin, int end) {
//        if (begin > end) return null;
//        int max = Integer.MIN_VALUE;
//        int mid = 0;//最大数的下标
//        for (int i = begin; i <= end; i++) {
//            if (max < nums[i]) {
//                max = nums[i];
//                mid = i;
//            }
//        }
//        TreeNode root = new TreeNode(max);
//        root.left = constructMaximumBinaryTree(nums, begin, mid - 1);
//        root.right = constructMaximumBinaryTree(nums, mid + 1, end);
//        return root;
//
//    }

    //1637. 两点之间不包含任何点的最宽垂直面积
    public int maxWidthOfVerticalArea(int[][] points) {
        int[] x = new int[points.length];
        for (int i = 0; i < x.length; i++) {
            x[i] = points[i][0];

        }
        Arrays.sort(x);
        for (int i = x.length - 1; i > 0; i--) {
            x[i] -= x[i - 1];

        }
        x[0] = Integer.MIN_VALUE;
        return Arrays.stream(x).max().getAsInt();

    }

//    public List<List<Integer>> subsets(int[] nums) {
//        //给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）
//        List<List<Integer>> list = new ArrayList<>();
//        list.add(new ArrayList<>());
//        for (int len = nums.length; len > 0; len--) {
//            List<Integer> list1 = new ArrayList<>();
//            //新子集加到list1中
//            subsetsDFS(list, 0, len, list1, nums);//len为还差的长度 第二个参数是当前准备拿的下标
//        }
//        return list;
//
//    }

    void subsetsDFS(List<List<Integer>> list, int cur, int len, List<Integer> list1, int[] nums) {
        if (len == 0) {
            list.add(new ArrayList<>(list1));
            return;
        }
        //判断剩下的数够不够
        int count = nums.length - cur;
        if (count < len) return;
        //取cur位置的数
        list1.add(nums[cur]);
        subsetsDFS(list, cur + 1, len - 1, new ArrayList<>(list1), nums);
        list1.remove(list1.size() - 1);
        //不取
        subsetsDFS(list, cur + 1, len, new ArrayList<>(list1), nums);

    }
    //leetcode
    //----------------------算法刷题攻略-----------------------------------------------------------

    //数组
    //35.


    //链表
    //206 反转单链表     迭代法  递归法
//    public ListNode reverseList(ListNode head) {
//        if (head == null) return null;
//        ListNode first = null;
//        ListNode second = head;
//        ListNode third = head.next;
//        while (second != null) {
//            second.next = first;
//            first = second;
//            second = third;
//            if (third != null)
//                third = third.next;
//
//
//        }
//        return first;
//    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode back = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return back;

    }

    //环形链表
    public ListNode detectCycle(ListNode head) {
        ListNode t = ExistCycle(head);
        if (t == null) return null;

        //有环
        //遍历 找到pre  环中节点无法走过pre   返回pre.next
        while (head != t) {
            head = head.next;
            t = t.next;
        }

        return t;

    }

    //count正好是圈的长度
    ListNode ExistCycle(ListNode head) {

        ListNode p1 = head;
        ListNode p2 = head;
        while (p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
            if (p1 == p2)
                return p1;
        }
        //没有环
        return null;
    }

    //哈希表
    //1.两数之和
    public int[] twoSum(int[] nums, int target) {
        //然后 找值target-num[i]
        //需要拿到下标  hashmap存数和下标
        Map<Integer, Integer> map = new HashMap<>();
        //直接找一次，put一次

        for (int i = 0; i < nums.length; i++
        ) {
            if (!map.containsKey(target - nums[i])) {
                map.put(nums[i], i);
            } else {
                return new int[]{i, map.get(target - nums[i])};
            }
        }
        return null;

    }

    //454 四数相加II
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        //一个数组里面取一个数   和为0
        //数值，个数
        //降到n^2
        int sum = 0;
        Map<Integer, Integer> mapAB = new HashMap<>();

        for (int a : A
        ) {
            for (int b : B
            ) {
                int r = a + b;
                if (!mapAB.containsKey(r)) {
                    mapAB.put(r, 1);
                } else mapAB.put(r, mapAB.get(r) + 1);
            }
        }
        for (int a : C
        ) {
            for (int b : D
            ) {
                if (mapAB.containsKey(-a - b)) {
                    sum += mapAB.get(-a - b);
                }
            }
        }
        return sum;
    }

    //15. 三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        // 枚举 a
        for (int first = 0; first < n; ++first) {
            if (nums[first] > 0) break;//大于0直接结束

            // 需要和上一次枚举的数不相同
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // c 对应的指针初始指向数组的最右端
            int third = n - 1;
            int target = -nums[first];
            // 枚举 b
            for (int second = first + 1; second < n; ++second) {
                // 需要和上一次枚举的数不相同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                // *需要保证 b 的指针在 c 的指针的左侧*
                while (second < third && nums[second] + nums[third] > target) {
                    --third;
                }
                // 如果指针重合，随着 b 后续的增加
                // 就不会有满足 a+b+c=0 并且 b<c 的 c 了，可以退出循环
                if (second == third) {
                    break;
                }
                if (nums[second] + nums[third] == target) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }

        }
        return ans;
    }

    //18 四数之和  思路和三数之和类似    复杂度n^3
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> lists = new LinkedList<>();
        int len = nums.length;
        for (int a = 0; a < len; a++) {//a
            if (a > 0 && nums[a] == nums[a - 1]) continue;
            for (int d = len - 1; d >= 0; d--) {//d
                if (d < len - 1 && nums[d] == nums[d + 1]) continue;
                if (a + 2 < d) {
                    int b = a + 1;
                    int c = d - 1;
                    while (c > b) {
                        int sum = nums[a] + nums[b] + nums[c] + nums[d];

                        if (b > a + 1 && nums[b] == nums[b - 1]) {
                            b++;
                            continue;
                        }
                        if (c < d - 1 && nums[c] == nums[c + 1]) {
                            c--;
                            continue;
                        }


                        if (sum > target) {
                            c--;
                        } else if (sum < target) {
                            b++;
                        } else {
                            List<Integer> list = new LinkedList<>();
                            list.add(nums[a]);
                            list.add(nums[b]);
                            list.add(nums[c]);
                            list.add(nums[d]);
                            lists.add(list);
                            b++;
                            c--;
                        }
                    }


                }

            }

        }
        return lists;


    }

    //字符串
    public void reverseString(char[] s) {

        int l = 0, r = s.length - 1;
        char t = 0;
        while (l < r) {
            t = s[l];
            s[l] = s[r];
            s[r] = t;

            l++;
            r--;
        }
    }

    //反转单词
    public String reverseWords(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        Stack<StringBuilder> stack = new Stack<>();
        int r = 0;
        while (r < s.length()) {
            if (s.charAt(r) == ' ') {
                if (stringBuilder.length() != 0) {

                    stack.push(stringBuilder);
                    stringBuilder = new StringBuilder();
                }
                r++;
            } else {
                stringBuilder.append(s.charAt(r));
                r++;
            }


        }
        if (stringBuilder.length() != 0) {
            stack.push(stringBuilder);
            stringBuilder = new StringBuilder();
        }
        while (!stack.isEmpty()) {
            stringBuilder.append(stack.pop());
            stringBuilder.append(' ');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();


    }

    public String reverseLeftWords(String s, int n) {
        String sb = s.substring(n) +
                s.substring(0, n);
        return sb;

    }

    //28. 实现 strStr()   模式匹配    KMP
    public int strStr(String haystack, String needle) {
        int[] prefix = new int[needle.length()];//前缀表
        for (int i = 1; i < needle.length(); i++) {//构造前缀表
            int len = 0;
            for (int j = i; j > 0; j--) {//长度3    可能0 1 2
                if (needle.substring(0, j).equals(needle.substring(i - j + 1, i + 1))) {
                    len = j;
                    break;
                }
            }
            prefix[i] = len;
        }
        int start = 0;//匹配到的字符串的最左端
        int i = 0;//haystack位置
        int j = 0;//needle位置
        while (j < needle.length() && i < haystack.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            } else {
                if (j == 0) {
                    i++;
                    start++;
                } else {
                    start = i - prefix[j - 1];
                    j = prefix[j - 1];
                }

            }
        }
        if (j == needle.length())//匹配成功
        {
            return start;
        }
        return -1;
    }


    //栈与队列
    //150  逆波兰
    public int evalRPN(String[] tokens) {
        Set<String> set = new HashSet<>();
        set.add("+");
        set.add("-");
        set.add("*");
        set.add("/");

        Stack<Integer> stack = new Stack<>();
        for (String c : tokens
        ) {
            if (!set.contains(c))//是数字
            {
                stack.push(Integer.valueOf(c));
            } else {
                int v2 = stack.pop();
                int v1 = stack.pop();
                if (c.equals("+")) {
                    stack.push(v1 + v2);
                }
                if (c.equals("-")) {
                    stack.push(v1 - v2);
                }
                if (c.equals("*")) {
                    stack.push(v1 * v2);
                }
                if (c.equals("/")) {
                    stack.push(v1 / v2);
                }
            }
        }
        return stack.pop();
    }

    //347  前k个高频元素
    // 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
    //任意顺序
    public int[] topKFrequent(int[] nums, int k) {
        //先在集合中放四个 数   然后更新  替换最小的
        //hashmap统计  复杂度n  遍历hashmap复杂度不到n
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int val : nums
        ) {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
        int[] freq = new int[k];//数的频率
        int[] top = new int[k];//高频数
        //用Hashmap来存数和频率

        for (Map.Entry entry : map.entrySet()
        ) {
            for (int i = k - 1; i >= 0; i--) {
                if ((int) entry.getValue() > freq[i]) {
                    //移动两个数组
//                    for (int j = 0; j <i ; j++) {
//                        freq[j]=freq[j+1];
//                    }
//                    for (int j = 0; j <i ; j++) {
//                        top[j]=top[j+1];
//                    }
                    System.arraycopy(freq, 1, freq, 0, i);
                    System.arraycopy(top, 1, top, 0, i);
                    freq[i] = (int) entry.getValue();
                    top[i] = (int) entry.getKey();
                    break;
                }

            }
        }
        return top;

    }
    //239滑动窗口最大值

    //进阶：线性时间
//    public int[] maxSlidingWindow(int[] nums, int k) {
//        if(k>nums.length)return null;
//        int []ans=new int[nums.length-k+1];//存结果
////        // 每次获取最大值复杂度k    最后复杂度为nk
////        int []val=new int[k];
////        for (int i = 0; i <k ; i++) {
////            val[i]=nums[i];
////        }
////        int max=val.
//        for (int i = 0; i <nums.length-k+1 ; i++) {
//            //每次移动去掉最左边元素   增加最右边元素
//            int max=nums[i];
//            for (int j = i+1; j <i+k ; j++) {
//                if(max<nums[j])max=nums[j];
//            }
//            ans[i]=max;
//        }
//        return ans;
//
//    }

//    public int[] maxSlidingWindow(int[] nums, int k) {
//        int n = nums.length;
//        Deque<Integer> deque = new LinkedList<>();
//        for (int i = 0; i < k; ++i) {
//            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
//                deque.pollLast();
//                //检索并删除最后一个元素，
//                // 如果此元素为空，则返回null。
//            }
//            deque.offerLast(i);//下标放到队列中   尾部
//            //在末尾插入元素
//        }
//        //此时队列中元素递增排列    数目不定 最少为一个  最大的那个
//
//        int[] ans = new int[n - k + 1];
//        ans[0] = nums[deque.peekFirst()];
//        for (int i = k; i < n; ++i) {//右移
//            //最大复杂度多少呢  可以认为是常数吧
//            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
//                deque.pollLast();
//            }
//            deque.offerLast(i);
//            //此时队列中可能有被移出的那个元素
//            // 只需检查队列最大数的下标！   如果被移出则删除
//            while (deque.peekFirst() <= i - k) {
//                deque.pollFirst();
//            }
//            ans[i - k + 1] = nums[deque.peekFirst()];
//        }
//        return ans;
//    }

    //二叉树
    //144  前序遍历   中左右
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        if (root == null) return list;
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            if (root == null) {
                TreeNode node = stack.pop().right;
                if (node != null) {
                    root = node;
                }


            } else {
                list.add(root.val);
                stack.push(root);
                root = root.left;
            }

        }
        return list;


    }

    //102. 二叉树的层序遍历      //广度优先
    public List<List<Integer>> levelOrder(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;
        queue.add(root);
        List<Integer> l = new LinkedList<>();
        l.add(root.val);
        ans.add(l);
        while (!queue.isEmpty()) {
            int len = queue.size();
            List<Integer> list = new LinkedList<>();
            for (int i = 0; i < len; i++) {
                TreeNode treeNode = queue.poll();//取头
                if (treeNode.left != null) {
                    queue.offer(treeNode.left);
                    list.add(treeNode.left.val);
                }
                if (treeNode.right != null) {
                    queue.offer(treeNode.right);
                    list.add(treeNode.right.val);
                }

            }
            ans.add(list);

        }
        ans.remove(ans.get(ans.size() - 1));
        return ans;


    }

    //226 反转二叉树   迭代实现
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode ans = root;
        //左->反转后的右   右->反转后的左
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                //反转两个子节点
                TreeNode node = root.left;
                root.left = root.right;
                root.right = node;
                //反转右子树
                stack.push(root);
                root = root.right;
            } else {
                root = stack.pop().left;//反转左子树
            }
        }
        return ans;

    }

    //101 对称二叉树

    //递归
//    public boolean isSymmetric(TreeNode root) {
//        if(root==null)return true;
//        return isMirror(root.left,root.right);
//    }
//    boolean isMirror(TreeNode n1,TreeNode n2)
//    {
//        if(n1==null&&n2==null)return true;
//        if(n1==null||n2==null)return false;
//        if(n1.val!=n2.val)return false;
//        return isMirror(n1.left,n2.right)&&isMirror(n1.right,n2.left);
//    }
    //迭代
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        TreeNode l = root.left, r = root.right;
        //判断两个子树是镜像的
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        while ((!stack1.isEmpty() && !stack2.isEmpty()) || (r != null && l != null)) {
            if (r != null && l != null) {
                if (r.val != l.val) return false;
                stack1.push(l);
                l = l.left;
                stack2.push(r);
                r = r.right;
            } else if (r == null && l == null) {
                l = stack1.pop().right;
                r = stack2.pop().left;
            } else return false;
        }

        return true;
    }

    //左叶子之和
    public int sumOfLeftLeaves(TreeNode root) {

        return sumOfLeftLeavesHelp(root.left, true) + sumOfLeftLeavesHelp(root.right, false);

    }

    private int sumOfLeftLeavesHelp(TreeNode root, boolean isLeft) {
        if (root == null) return 0;
        if (isLeft && root.left == null && root.right == null) {
            return root.val;
        }
        return sumOfLeftLeavesHelp(root.left, true) + sumOfLeftLeavesHelp(root.right, false);


    }
    //513. 找树左下角的值   迭代

    //    public int findBottomLeftValue(TreeNode root) {
    //        LinkedList<TreeNode> queue = new LinkedList<>();
    //        if (root == null) return -1;
    //        queue.add(root);
    //        List<Integer> l = new LinkedList<>();
    //        l.add(root.val);
    //        while (!queue.isEmpty()) {
    //            int len = queue.size();
    //            List<Integer> list = new LinkedList<>();
    //            for (int i = 0; i < len; i++) {
    //                TreeNode treeNode = queue.poll();//取头
    //                if (treeNode.left != null) {
    //                    queue.offer(treeNode.left);
    //                    list.add(treeNode.left.val);
    //                }
    //                if (treeNode.right != null) {
    //                    queue.offer(treeNode.right);
    //                    list.add(treeNode.right.val);
    //                }
    //
    //            }
    //            if (list.size() != 0) l = list;
    //        }
    //
    //        return l.get(0);
    //
    //
    //    }
    //递归
    public int findBottomLeftValue(TreeNode root) {
        return findBottomLeftValue(root, 0).root.val;
    }

    public TreeDeep findBottomLeftValue(TreeNode root, int deep)//返回深度和值
    {

        if (root == null) return null;
        TreeDeep l = findBottomLeftValue(root.left, deep + 1);
        TreeDeep r = findBottomLeftValue(root.right, deep + 1);
        if (l == null && r == null) return new TreeDeep(root, deep);
        if (l == null) return r;
        if (r == null) return l;
        if (l.deep >= r.deep) return l;
        else return r;
    }

    class TreeDeep {
        TreeNode root;
        int deep;

        TreeDeep(TreeNode root, int deep) {
            this.root = root;
            this.deep = deep;

        }
    }

    //106. 从中序与后序遍历序列构造二叉树
    HashMap<Integer, Integer> map = new HashMap<>();
    int pr;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);//值的位置
        }

        this.pr = inorder.length - 1;
        return buildTree(inorder, 0, inorder.length - 1, postorder);


    }

    //你可以假设树中没有重复的元素。
    //记录每个数的位置
    public TreeNode buildTree(int[] inorder, int inl, int inr, int[] postorder) {
        if (inl < 0 || inr >= inorder.length || inl > inr || pr < 0) return null;
        TreeNode root = new TreeNode(postorder[pr]);
        int pos = map.get(postorder[pr]);//中序切割点
        //分别处理左侧和右侧   先处理右侧  因为后序遍历 是  左右 中   中前面是右
        //右侧
        pr--;
        root.right = buildTree(inorder, pos + 1, inr, postorder);
        //左侧
        root.left = buildTree(inorder, inl, pos - 1, postorder);
        return root;
    }

    //617. 合并二叉树
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return null;
        if (root1 != null) {
            if (root2 != null) {
                root1.val += root2.val;
                root1.left = mergeTrees(root1.left, root2.left);
                root1.right = mergeTrees(root1.right, root2.right);
                return root1;
            } else return root1;
        } else {//1空2不空
            return root2;
        }


    }

    //654. 最大二叉树   找到根  构造左侧   构造右侧   无重复元素

    //1.   双5%
//    HashMap<Integer,Integer>map654=new HashMap<>();
//    int []ordered654;
//    public TreeNode constructMaximumBinaryTree(int[] nums) {
//        for (int i = 0; i <nums.length ; i++) {
//            map654.put(nums[i],i);
//        }
//        ordered654=nums.clone();
//        Arrays.sort(ordered654);
//        return constructMaximumBinaryTree(nums,0,nums.length-1,nums.length-1);
//
//    }
//    public TreeNode constructMaximumBinaryTree(int[] nums,int l,int r,int maxIndex){
//        if(l>r||maxIndex<0)return null;
//        while(maxIndex>0&&(map654.get(ordered654[maxIndex])<l||map654.get(ordered654[maxIndex])>r)){
//            maxIndex--;
//        }
//        if(maxIndex<0)return null;
//        TreeNode root=new TreeNode(ordered654[maxIndex]);
//        //左右
//        root.left=constructMaximumBinaryTree(nums,l,map654.get(root.val)-1,maxIndex-1);
//        root.right=constructMaximumBinaryTree(nums,map654.get(root.val)+1,r,maxIndex-1);
//        return root;
//    }

    //2.
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        TreeNode root = new TreeNode(nums[0]);
        return constructMaximumBinaryTree(nums, root, root, 1);
    }

    public TreeNode constructMaximumBinaryTree(int[] nums, TreeNode root, TreeNode node, int idx) {
        if (idx == nums.length) return root;
        TreeNode node1 = new TreeNode(nums[idx]);
        if (node.val > node1.val)//放在右子树
        {
            node.right = node1;
            node = node1;
        } else {
            TreeNode t = root;
            TreeNode pre = new TreeNode(0);
            pre.right = root;
            while (node1.val < t.val) {
                t = t.right;
                pre = pre.right;
            }
            pre.right = node1;
            node1.left = t;

            node = node1;
            if (root.val < node.val) root = node;
        }
        return constructMaximumBinaryTree(nums, root, node, idx + 1);

    }

    //98. 验证二叉搜索树
    long val98 = (long) Integer.MIN_VALUE - 1;

    public boolean isValidBST(TreeNode root) {
        //二叉搜索树的最大值在右下角   最小值在左下角
        //左中右遍历打印出来的是从小到大
        return traverse98(root);


    }

    boolean traverse98(TreeNode root) {
        if (root == null) return true;
        if (!traverse98(root.left)) return false;
        //大于前一个值
        if (root.val <= val98) return false;
        val98 = root.val;
        return traverse98(root.right);

    }

    //530. 二叉搜索树的最小绝对差   也是中序遍历
    long min530 = Long.MAX_VALUE;
    long last530 = Integer.MIN_VALUE;

    public int getMinimumDifference(TreeNode root) {
        tvs530(root);
        return (int) min530;
    }

    void tvs530(TreeNode root) {
        if (root == null) return;
        tvs530(root.left);
        //差值用long
        long d = ((long) root.val) - last530;
        min530 = Math.min(d, min530);
        last530 = root.val;
        tvs530(root.right);
    }

    //501. 二叉搜索树中的众数
    //统计数据的频率    当前众数
    int Modefreq501 = 0;// 当前数的频率  众数的频率  结果集
    long cur501 = Long.MAX_VALUE;//当前数
    int curfreq501;

    List<Integer> list501 = new LinkedList<>();

    public int[] findMode(TreeNode root) {
        //

        tvs501(root);
        int[] r = new int[list501.size()];
        for (int i = 0; i < r.length; i++) {
            r[i] = list501.get(i);
        }
        return r;

    }

    void tvs501(TreeNode root) {
        if (root == null) return;
        tvs501(root.left);
        if (root.val == cur501) curfreq501++;
        else curfreq501 = 1;
        cur501 = root.val;
        if (curfreq501 > Modefreq501) {
            Modefreq501 = curfreq501;
            list501.clear();
            list501.add((int) cur501);
        } else if (curfreq501 == Modefreq501) {
            list501.add((int) cur501);
        }
        tvs501(root.right);

    }

    //236. 二叉树的最近公共祖先
    //1. 10%  90%
    //从root找到p的操作 root  left  left     找到q的操作 root left  路径最后一个相同的就是共同祖先
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pPath = new LinkedList<>();
        List<TreeNode> qPath = new ArrayList<>();
        findPath236(pPath, root, p);
        findPath236(qPath, root, q);
        TreeNode ans = root;

        for (int i = 1; i < pPath.size() && i < qPath.size(); i++) {
            if (pPath.get(i) == qPath.get(i)) {
                ans = pPath.get(i);
            } else break;

        }
        return ans;


    }

    //路径  包含root 22%  21%
    boolean findPath236(List<TreeNode> path, TreeNode root, TreeNode p) {
        if (root == null) return false;
        path.add(root);
        if (root == p) {
            return true;
        }
        //找左
        if (root.left != null) {
            if (findPath236(path, root.left, p))
                return true;

        }
        //找右
        if (root.right != null) {
            if (findPath236(path, root.right, p))
                return true;

        }
        path.remove(path.size() - 1);
        return false;

    }
    //2.
//    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//        if (root == p || root == q) {
//            return root;
//        }
//        if (root != null) {
//            //没有公共祖先 返回p或q位置   有则返回公共祖先
//            //左边是否有p或q    右边是否有p或q     两边都有返回root
//            TreeNode lNode = lowestCommonAncestor(root.left, p, q);
//            TreeNode rNode = lowestCommonAncestor(root.right, p, q);
//            if (lNode != null && rNode != null)
//                return root;
//            else if (lNode == null) {//两个都在右子树
//                return rNode;
//            } else { //两个都在左子树里面
//                return lNode;
//            }
//        }
//        return null;
//    }

    //3.   99%   11%
    //当前root是否包含p ,q
    TreeNode ans236 = null;

    private boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return false;

        boolean lson = dfs(root.left, p, q);
        boolean rson = dfs(root.right, p, q);
        //左右都包含p q      或   根是p或q并且包含另一个
        if ((lson && rson) || ((root == p || root == q) && (lson || rson))) {
            ans236 = root;
        }
        //自己本身是 或者字节包含
        return lson || rson || (root == p || root == q);
    }

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        this.dfs(root, p, q);
        return this.ans236;
    }

    //235. 二叉搜索树的最近公共祖先
    //根节点大于等于小的   小于等于大的
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //
        if (p.val < q.val)
            return tvs235(root, p.val, q.val);
        return tvs235(root, q.val, p.val);


    }

    TreeNode tvs235(TreeNode root, int low, int high) {
        if (root == null) return null;
        if (root.val > low && root.val < high) return root;
        if (root.val > high) return tvs235(root.left, low, high);
        if (root.val < low) return tvs235(root.right, low, high);
        //等于
        return root;
    }

    //701. 二叉搜索树中的插入操作
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (root.val < val) root.right = insertIntoBST(root.right, val);
        else root.left = insertIntoBST(root.left, val);
        return root;
    }

    //450. 删除二叉搜索树中的节点
    //删除的可能是   根   中间   叶子   左旋右旋
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (root.val == key) {
            TreeNode node;
            TreeNode pre = new TreeNode(0);
            if (root.left != null) {
                node = root.left;
                if (node.right == null)//没有子节点 则 直接接上
                {
                    node.right = root.right;

                    return node;
                }
                pre.right = node;
                while (node.right != null) {
                    pre = node;
                    node = node.right;

                }
                //这里注意处理 左子树最右节点的左子树
                pre.right = node.left;
                node.left = root.left;
                node.right = root.right;
                return node;
            } else return root.right;
        } else if (root.val < key)
            root.right = deleteNode(root.right, key);
        else root.left = deleteNode(root.left, key);
        return root;

    }

    //669. 修剪二叉搜索树   root在中间  在左边 在右边  切掉的节点还要接上
    //两侧
    //修剪左节点：左节点的右子节点接在根的左
    //修剪右节点：右节点的左子节点接在根
    //修剪根：如果low是根 新的根为root.right  high是根 新的根为root.left

    //同侧
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) return null;
        if (root.val < low) return trimBST(root.right, low, high);
        if (root.val > high) return trimBST(root.left, low, high);
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);

        return root;
    }

    //108. 将有序数组转换为二叉搜索树  选中间作为根  递归
    public TreeNode sortedArrayToBST(int[] nums) {
        TreeNode root = new TreeNode(nums[nums.length / 2]);
        root.left = sortedArrayToBST(nums, 0, nums.length / 2 - 1);
        root.right = sortedArrayToBST(nums, nums.length / 2 + 1, nums.length - 1);
        return root;

    }

    public TreeNode sortedArrayToBST(int[] nums, int l, int r) {
        if (l < 0 || r >= nums.length || l > r) return null;
        if (l == r) return new TreeNode(nums[l]);
        int mid = (l + r) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, l, mid - 1);
        root.right = sortedArrayToBST(nums, mid + 1, r);
        return root;

    }

    //538. 把二叉搜索树转换为累加树
    //右中左遍历加
    int sum538 = 0;

    public TreeNode convertBST(TreeNode root) {
        if (root == null) return null;
        convertBST(root.right);
        root.val += sum538;
        sum538 = root.val;
        //
        convertBST(root.left);
        return root;
    }

    //*******************************************************//
    //回溯算法
    //77. 组合
    //给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
    List<List<Integer>> combinList = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        //选择一个数 或者不选择     数量
        backCombin(1, k, n, new ArrayList<Integer>());
        return combinList;


    }

    //   当前下标   还需要几个数     总数
    void backCombin(int index, int need, int n, List<Integer> list) {
        if (n - index + 1 < need || need == 0)//剩余的数不够
            return;
        list.add(index);
        if (need == 1) {
            combinList.add(new ArrayList<>(list));
        }
        //选择这个数
        backCombin(index + 1, need - 1, n, list);
        //不选择这个数
        list.remove(list.size() - 1);
        backCombin(index + 1, need, n, list);//这里应该不用new ArrayList


    }

    //216. 组合总和 III
    List<List<Integer>> combinationSum3lists = new ArrayList<>();

//    public List<List<Integer>> combinationSum3(int k, int n) {
//        //穷举   一共没多少    0=n不知道算不算
//        //从1-9中选k个数
//        backcombinationSum3(1, 0, n, k, new ArrayList<>());
//        return combinationSum3lists;
//
//
//    }

    //    void backcombinationSum3(int index,int sum,int n,int need,List<Integer>list){
//        if(sum>n||index>9||need<0||n-index+1<need)return;
//        list.add(index);
//        if(need==1&&sum+index==n){combinationSum3lists.add(new ArrayList<>(list));}
//        backcombinationSum3(index+1,sum+index,n,need-1,list);
//        list.remove(list.size()-1);
//        backcombinationSum3(index+1,sum,n,need,list);
//
//
//    }
    //另一种写法   这种写法内存比较小 比前一种小了0.5MB
    public List<List<Integer>> combinationSum3(int k, int n) {
        //穷举   一共没多少    0=n不知道算不算
        //从1-9中选k个数
        backcombinationSum3(1, 0, n, k, new ArrayList<>());
        return combinationSum3lists;


    }

    void backcombinationSum3(int index, int sum, int n, int k, List<Integer> list) {
        if (sum > n) return;
        if (list.size() == k) {
            if (sum == n) combinationSum3lists.add(new ArrayList<>(list));
            return;
        }
        for (int i = index; i < 10; i++) {
            sum += i;
            list.add(i);
            backcombinationSum3(i + 1, sum, n, k, list);
            sum -= i;
            list.remove(list.size() - 1);

        }

    }

    //17. 电话号码的字母组合
    List<String> letterCombinationsList = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) return letterCombinationsList;
        backletterCombinations(0, digits, "");
        return letterCombinationsList;
    }

    void backletterCombinations(int index, String digits, String list) {
        if (index == digits.length()) {
            letterCombinationsList.add(list);
            return;
        }
        String letter = Digit2Letter[digits.charAt(index) - '0'];
        for (char c : letter.toCharArray()
        ) {
            backletterCombinations(index + 1, digits, list + c);
        }
    }

    String[] Digit2Letter = new String[]{//根据下标得到可能的字符
            "", // 0
            "", // 1
            "abc", // 2
            "def", // 3
            "ghi", // 4
            "jkl", // 5
            "mno", // 6
            "pqrs", // 7
            "tuv", // 8
            "wxyz", // 9
    };
    //40. 组合总和 II
    //所有数字（包括目标数）都是正整数。
    List<List<Integer>> combinationSum2lists = new ArrayList<>();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        backCS2(candidates, target, new ArrayList<>(), 0);
        return combinationSum2lists;
    }

    void backCS2(int[] candidates, int target, List<Integer> list, int idx) {
        if (target == 0) {
            combinationSum2lists.add(new ArrayList<>(list));
            return;
        }
        if (idx >= candidates.length || target < 0) return;

        //candidates已经排序了
        //如果不拿一个数  则后面和他相同的数直接跳过

        //不拿
        int cur = candidates[idx];
        int i = idx + 1;
        while (i < candidates.length && cur == candidates[i]) {
            i++;
        }
        backCS2(candidates, target, list, i);
        //拿
        list.add(cur);
        backCS2(candidates, target - cur, list, idx + 1);
        list.remove(list.size() - 1);
    }
    //131. 分割回文串

    List<List<String>> partList = new ArrayList<>();

    public List<List<String>> partition(String s) {
        if (s.length() == 0) return partList;
        //每个位置 ： 切  或者不切  (i,j)
        //根据长度遍历子串
        partHelp(new ArrayList<>(), s, 0);
        return partList;

    }

    void partHelp(List<String> list, String s, int idx) {
        if (idx == s.length()) {
            partList.add(new ArrayList<>(list));
            return;
        }
        for (int i = idx + 1; i < s.length() + 1; i++) {//在i后面切 s.sub(idx,i+1)
            String sub = s.substring(idx, i);
            if (isPalindrome(sub)) {//是回文可以切
                list.add(sub);
                partHelp(list, s, i);
                list.remove(list.size() - 1);

            }
            //不切
        }
    }

    boolean isPalindrome(String s) {
        if (s.length() == 0) return false;//空串不是
        int l = 0, r = s.length() - 1;
        while (l <= r) {
            if (s.charAt(l++) != s.charAt(r--)) return false;
        }
        return true;
    }

    //93. 复原IP地址
    //边切边判断合法   还是  切完再判断合法呢
    List<String> list = new ArrayList<>();
    StringBuilder sb = new StringBuilder();

    public List<String> restoreIpAddresses(String s) {
        if (s.length() > 12 || s.length() == 0) return list;
        //四部分  每个部分长1-3    01 02是无效的
        //先切下来一部分   判断这部分
        cutIpAdd(s, 4, 0);
        return list;
    }

    //@need  剩余部分数
    //@index 从index开始cut
    void cutIpAdd(String s, int need, int index) {
        if (index >= s.length() && need == 0) {
            sb.deleteCharAt(sb.length() - 1);
            list.add(sb.toString());
            sb.append('.');
            return;
        }
        if (need == 0) return;

        if (s.length() - index < need || s.length() - index > 3 * need) return;
        if (s.charAt(index) == '0') {
            //只能在这切
            sb.append('0').append('.');
            cutIpAdd(s, need - 1, index + 1);
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        } else
            for (int i = 1; i <= 3 && index + i <= s.length(); i++) {//切割的位置
                String sub = s.substring(index, index + i);
                if (Integer.parseInt(sub) <= 255) {

                    sb.append(sub);
                    sb.append('.');
                    cutIpAdd(s, need - 1, index + i);

                    sb.delete(sb.length() - i - 1, sb.length());
                }


            }

    }

    //78.子集   //幂集
    List<List<Integer>> lists78 = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        //根据长度遍历
        for (int i = 0; i <= nums.length; i++) {
            dfs78(new ArrayList<>(), nums, 0, i);

        }
        return lists78;
    }

    void dfs78(List<Integer> list, int[] nums, int index, int cnt) {
        if (cnt == 0) {
            lists78.add(new ArrayList<>(list));
            return;
        }
        if (nums.length - index < cnt)//剩余元素不够
            return;
        //取
        list.add(nums[index]);
        dfs78(list, nums, index + 1, cnt - 1);
        list.remove(list.size() - 1);
        //不取
        dfs78(list, nums, index + 1, cnt);
    }

    //90. 子集 II
    //给定一个可能包含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
    List<List<Integer>> lists90 = new ArrayList<>();

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        lists90.add(new ArrayList<>());
        dfs90(new ArrayList<>(), nums, 0);
        return lists90;
    }

    void dfs90(List<Integer> list, int[] nums, int index) {
        if (index >= nums.length) return;
        //每次放元素都加
        //如果不放一个元素   注意直接跳到下一个不等的元素
        //放
        list.add(nums[index]);
        lists90.add(new ArrayList<>(list));
        dfs90(list, nums, index + 1);
        list.remove(list.size() - 1);
        int t = nums[index];
        int i = index + 1;
        while (i < nums.length && t == nums[i]) i++;
        dfs90(list, nums, i);


    }

    //491. 递增子序列
    List<List<Integer>> lists491 = new ArrayList<>();
    private List<Integer> list491 = new ArrayList<>();

    //    HashSet<Integer>[] layer=new HashSet[15];
//    List<HashSet<Integer>>layer1=new ArrayList<>();
    public List<List<Integer>> findSubsequences(int[] nums) {
//        for (int i = 0; i <15 ; i++) {
//            layer[i]=new HashSet<>();
//        }
//        for (int i = 0; i <15 ; i++) {
//            layer1.add(new HashSet<>());
//        }
        HashSet<Integer> layer = new HashSet<>();
        //确定子序列的开始元素
        for (int i = 0; i < nums.length; i++) {
            if (!layer.contains(nums[i])) {
                layer.add(nums[i]);
                list491.add(nums[i]);
                dfs491(nums, i + 1);
                list491.remove(list491.size() - 1);
            }


        }

        return lists491;
    }

    void dfs491(int[] nums, int index) {
        if (index >= nums.length) return;
        HashSet<Integer> layer = new HashSet<>();

        for (int i = index; i < nums.length; i++) {
            if (nums[i] >= list491.get(list491.size() - 1)) {
                if (!layer.contains(nums[i])) {
                    layer.add(nums[i]);
                    list491.add(nums[i]);
                    lists491.add(new ArrayList<>(list491));
                    dfs491(nums, i + 1);

                    list491.remove(list491.size() - 1);
                }


            }
        }


    }

    //46. 全排列
    //给定一个 没有重复 数字的序列，返回其所有可能的全排列。
    //used数组   如果nums有重复就不行了
    List<List<Integer>> result46 = new LinkedList<>();
    List<Integer> path46 = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        boolean[] used = new boolean[nums.length];

        back46(nums, used);
        return result46;


    }

    void back46(int[] nums, boolean[] used) {
        if (path46.size() == nums.length) {
            result46.add(new LinkedList<>(path46));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                used[i] = true;
                path46.add(nums[i]);
                back46(nums, used);
                used[i] = false;
                path46.remove(path46.size() - 1);
            }

        }
    }

    //47. 全排列 II
    List<List<Integer>> result47 = new ArrayList<>();
    List<Integer> path47 = new ArrayList<>();

    //一样的元素必须先用了前面的才能用后面的
    public List<List<Integer>> permuteUnique(int[] nums) {
        //如果选择了一个数  下一个位置可能还是这个数
        //如果没选择这个数  则这个位置不能是这个数
        boolean[] used47 = new boolean[nums.length];

        Arrays.sort(nums);
        back47(nums.length, nums, used47);
        return result47;
    }

    void back47(int len, int[] nums, boolean[] used) {
        //如果一个数之前有相同的数  则只有之前的相同数用过了才可以用它
        if (len == 0) {
            result47.add(new ArrayList<>(path47));
            return;
        }
        for (int i = 0; i < nums.length; i++) {

            if (!used[i]) {
                if ((i > 0 && nums[i] == nums[i - 1] && used[i - 1]) ||
                        i == 0 || nums[i] != nums[i - 1]) {//条件合并后运行时间边长了

                    //放
                    used[i] = true;
                    path47.add(nums[i]);
                    back47(len - 1, nums, used);
                    path47.remove(path47.size() - 1);
                    used[i] = false;

                }
            }


        }

    }

    //332. 重新安排行程
    //如果存在多种有效的行程，请你按字符自然排序返回最小的行程组合。
    // 例如，行程 ["JFK", "LGA"] 与 ["JFK", "LGB"] 相比就更小，排序更靠前
    //所有机票必须且只能用一次
    //@官方题解  nb!
    Map<String, PriorityQueue<String>> map332 = new HashMap<>();
    List<String> itinerary = new LinkedList<String>();

    public List<String> findItinerary(List<List<String>> tickets) {
        for (List<String> ticket : tickets) {
            String src = ticket.get(0), dst = ticket.get(1);
            if (!map332.containsKey(src)) {
                map332.put(src, new PriorityQueue<String>());
            }
            map332.get(src).offer(dst);
        }
        dfs("JFK");
        Collections.reverse(itinerary);
        return itinerary;
    }

    public void dfs(String curr) {
        while (map332.containsKey(curr) && map332.get(curr).size() > 0) {
            String tmp = map332.get(curr).poll();
            dfs(tmp);
        }
        itinerary.add(curr);
    }

    //51. N 皇后
    List<List<String>> reslut51 = new ArrayList<>();
    List<String> method51 = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        boolean[][] used = new boolean[n][n];
        back51(n, 0, used);
        return reslut51;


    }

    void back51(int n, int r, boolean[][] used) {
        if (r == n) {
            reslut51.add(new ArrayList<>(method51));
            return;
        }
        //遍历行
        for (int j = 0; j < n; j++) {//遍历列  确认棋子位置
            boolean flag = false;//为true代表不合法

            //三个方向
            for (int k = 0; k < n; k++) {
                if (used[k][j]) {
                    flag = true;//遍历下一个位置
                    break;
                }
            }
            if (flag) continue;
            for (int k = 0; k < n; k++) {
                if (used[r][k]) {
                    flag = true;
                    break;
                }
            }
            if (flag) continue;
            //两条斜线
            int x = r, y = j;
            while (x < n && y < n) {
                if (used[x++][y++]) {
                    flag = true;
                    break;

                }
            }
            x = r;
            y = j;
            while (x >= 0 && y >= 0) {
                if (used[x--][y--]) {
                    flag = true;
                    break;

                }
            }
            x = r;
            y = j;

            while (x < n && y >= 0) {
                if (used[x++][y--]) {
                    flag = true;
                    break;

                }
            }
            x = r;
            y = j;

            while (x >= 0 && y < n) {
                if (used[x--][y++]) {
                    flag = true;
                    break;

                }
            }
            if (flag) continue;
            used[r][j] = true;
            method51.add(draw51(j, n));
            back51(n, r + 1, used);
            method51.remove(method51.size() - 1);
            used[r][j] = false;

        }

    }

    String draw51(int c, int n) {//绘制一行棋盘
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (c == i) sb.append('Q');
            else
                sb.append('.');
        }
        return sb.toString();
    }

    //37. 解数独
    //你可以假设给定的数独只有唯一解
    //把.改成合理的数字
    //给定数独永远是 9x9 形式的。
    //横竖和九宫格出现一次

    //用hashset表示 每行每列  每宫 已经填进的数字    //用set更慢了
    List<HashSet<Character>> row37 = new ArrayList<>();
    List<HashSet<Character>> col37 = new ArrayList<>();
    List<HashSet<Character>> room937 = new ArrayList<>();

    public void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            row37.add(new HashSet<>());
            col37.add(new HashSet<>());
            room937.add(new HashSet<>());
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    row37.get(i).add(board[i][j]);
                    col37.get(j).add(board[i][j]);
                    room937.get((i / 3) * 3 + j / 3).add(board[i][j]);
                }


            }
        }
        back37(board, 0, 0);
    }

    //棋盘  要填的位置   一行一行填
    boolean back37(char[][] board, int r, int c) {
        if (r == 9) return true;
        if (board[r][c] != '.') {
            //本来有数字的位置
            //跳过
            //表示下一个位置

            return back37(board, r + (c + 1) / 9, (c + 1) % 9);
        }
        for (char i = '1'; i <= '9'; i++) {
            if (!row37.get(r).contains(i) && !col37.get(c).contains(i) &&
                    !room937.get((r / 3) * 3 + c / 3).contains(i)) {
                //填数字
                board[r][c] = i;
                row37.get(r).add(i);
                col37.get(c).add(i);
                room937.get((r / 3) * 3 + c / 3).add(i);
                if (!back37(board, r + (c + 1) / 9, (c + 1) % 9)) {
                    board[r][c] = '.';
                    row37.get(r).remove(i);
                    col37.get(c).remove(i);
                    room937.get((r / 3) * 3 + c / 3).remove(i);
                } else return true;
            }

        }
        return false;

    }
    //自己的第一种写法  稍快
    /*
     public void solveSudoku(char[][] board) {
        back37(board, 0, 0);
    }

    //棋盘  要填的位置   一行一行填
    boolean back37(char[][] board, int r, int c) {
        if (r == 9) return true;
        if (board[r][c] != '.') {
            //本来有数字的位置
            //跳过
            //表示下一个位置

            return back37(board, r + (c + 1) / 9, (c + 1) % 9);
        }
        for (char i = '1'; i <= '9'; i++) {
            boolean flag = false;
            //行列九宫不重复
            for (int j = 0; j < 9; j++) {
                if (board[r][j] == i) {
                    flag = true;
                    break;
                }
            }
            if (flag) continue;
            for (int j = 0; j < 9; j++) {
                if (board[j][c] == i) {
                    flag = true;
                    break;
                }
            }
            if (flag) continue;
            //判断是哪个九宫格 0.0 - 2.2
            int r9 = 3 * (r / 3);
            int c9 = 3 * (c / 3);

            for (int j = 0; j < 9; j++)
            {
                if(board[r9+j/3][c9+j%3]==i)
                {
                    flag=true;
                    break;
                }
            }
            if (flag) continue;
            //填数字
            board[r][c] = i;
            if(!back37(board, r + (c + 1) / 9, (c + 1) % 9))
            board[r][c] = '.';
            else return true;
        }
        return false;

    }


     */


    //------------------------------
    //#贪心算法   难点可能在于想到用贪心算法    不过一共就这几种算法  都想一下吧

    //455. 分发饼干  g胃口  s饼干   不知道为什么用贪心就是最优解了
    //每个孩子最多只能给一块饼干
    public int findContentChildren(int[] g, int[] s) {
        //思路  用最小的饼干给需求最小的孩子
        Arrays.sort(g);
        Arrays.sort(s);
        //从最小的开始    //先喂大的还是小的呢？  应该是一样的
        int sum = 0;//答案
        for (int i = 0, j = 0; i < g.length && j < s.length; ) {
            if (s[j] >= g[i]) {
                sum++;
                i++;
                j++;
            } else j++;

        }
        return sum;
    }

    //376. 摆动序列
    //你能否用 O(n) 时间复杂度完成此题?   注意考虑相等的情况
//    public int wiggleMaxLength(int[] nums) {
//        if(nums.length<=1)return nums.length;
//        //改变方向的端点要保留
//        for (int i = nums.length-1; i>=1; i--) {
//            nums[i]=nums[i]-nums[i-1];
//        }
//        nums[0]=0;
//        int sum=1;
//        for (int i = 1; i <nums.length ; i++){
//            if(nums[i]==0){
//                nums[i]=nums[i-1];
//            }
//            else
//            if(nums[i]*nums[i-1]<=0)sum++;//方向改变 后面的数不等于0
//            //注意这种情况   \ - \
//        }
//        return sum;
//
//    }
    //2.另一种写法
    public int wiggleMaxLength(int[] nums) {
        int sum = 1;
        int pre = 0;//之前的方向
        int cur = 0;//当前的方向
        for (int i = 1; i < nums.length; i++) {
            cur = nums[i] - nums[i - 1];
            if ((cur > 0 && pre <= 0) || (cur < 0 && pre >= 0))
            //cur是0或者和之前方向一致  不改变之前的方向
            {
                sum++;
                pre = cur;//改变方向
            }
        }
        return sum;
    }

    //53. 最大子序和
    //1.很慢  n方
//    public int maxSubArray(int[] nums){
//        int max=Integer.MIN_VALUE;
//        //遍历下标从i开始
//        for (int i = 0; i <nums.length ; i++) {
//            int sum=0;
//            for (int j =i; j <nums.length ; j++) {
//                sum+=nums[j];
//                max=Math.max(max,sum);
//            }
//        }
//        return max;
//    }
    //动态规划   以i为结尾的最大和=max(以i-1结尾的最大和+nums[i],nums[i])
    //复杂度n
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int pre = 0;//以i结尾的最大子序和
        for (int i = 0; i < nums.length; i++) {
            pre = Math.max(pre + nums[i], nums[i]);//！！
            max = Math.max(pre, max);
        }
        return max;
    }

    //122. 买卖股票的最佳时机 II
    //抓住每一次局部最小到局部最大
    public int maxProfit(int[] prices) {
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            int t = prices[i] - prices[i - 1];
            ans += Math.max(t, 0);
        }
        return ans;
    }

    //55. 跳跃游戏
    //什么情况跳不到最后：在最后一个位置之前有一个必经的0
    //跨不过这个0的都标为0
    //如果第一个位置被标为0 则false
    //修了一小会bug
//    public boolean canJump(int[] nums){
//        if(nums.length<=1)return true;  //特殊情况  :[0]
//
//        int z;
//        for ( z= nums.length-2; z >=0 ; z--) {
//            if(nums[z]==0)break;
//        }
//        for (int i = z-1; i>=0 ; i--) {
//            if(nums[i]>z-i)
//             //能跨过去  则此位置前面的至少能到此位置
//            {
//                z=i-1;//能跨过i-1
//            }
//        }
//        return z<0;//至少能到0
//
//    }
    //2.持续更新能跳到的最远距离   代码少  但没有上一种快   但是是贪心的思路
    public boolean canJump(int[] nums) {
        int max = 0;//初始时能跳到的最远距离为0
        for (int i = 0; i < nums.length; i++) {
            if (max < i) return false;//跳不到这个位置
            max = Math.max(max, i + nums[i]);//这个位置能跳到的最远距离 和所有位置能的最远距离
        }
        return true;
    }

    //45. 跳跃游戏 II
    //使用最少的跳跃次数到达数组的最后一个位置
    //找到能扩展距离的点  从这个点跳跃可以更远  从每个点及之前跳能到的位置
    //n方复杂度
//    public int jump(int[] nums) {
//        //每个点能到达的下一个最远距离
//        for (int i = 0; i <nums.length ; i++) {
//            nums[i]+=i;
//        }
//        int sum=0;
//        int dist=nums.length-1;
//        while(dist!=0)
//        for (int i = 0; i <dist ; i++) {
//            if(nums[i]>=dist)
//            {
//                    dist=i;
//                    sum++;
//
//                break;
//
//            }
//        }
//        return sum;
//
//    }

    //当前的覆盖范围  下一步的最大覆盖范围
    //每次更新覆盖范围  步数+1 知道覆盖终点nums.length-1
    public int jump(int[] nums) {
        int curdist = 0;
        int nextdist = 0;
        int sum = 0;

        /*
        int i=0;

        while (curdist<nums.length-1)
        {

            for (; i <=curdist ; i++) {
                nextdist=Math.max(nextdist,nums[i]+i);
            }
            sum++;
            i=curdist+1;
            curdist=nextdist;
        }

         */
        //换另一种写法
        for (int i = 0; i < nums.length - 1; i++) {
            nextdist = Math.max(nextdist, nums[i] + i);//更新最大范围
            //当i超出原范围时  更新原范围   步数+1
            if (i == curdist)//使用完之前范围的所有值
            {
                sum++;
                curdist = nextdist;
            }
        }
        return sum;

    }

    //1005. K 次取反后最大化的数组和 -100 <= A[i] <= 100
    //根据数的范围
    public int largestSumAfterKNegations1(int[] A, int K) {
        //从最小的负数开始取反  从小的非负数开始取反
        //取值一共有201个
        int[] values = new int[201];
        for (int i = 0; i < A.length; i++) {
            values[A[i] + 100]++;
        }
        for (int i = 0; i < 100 && K > 0; i++)//-100~-1
        {
            while (values[i] > 0 && K > 0) {
                K--;
                values[i]--;
                values[200 - i]++;
            }
        }
        //K>0
        if (K > 0 && values[100] == 0 && K % 2 == 1)//只需要改一个
        {
            for (int i = 101; i < 201; i++) {
                if (values[i] > 0) {
                    values[i]--;
                    values[200 - i]++;
                    break;
                }
            }
        }
        int sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += (i - 100) * values[i];
        }
        return sum;
    }



    //134. 加油站   答案唯一或者无解
//    public int canCompleteCircuit(int[] gas, int[] cost) {
//        //遍历开始的加油站   太慢了
//        int len=gas.length;
//        for (int i = 0; i <len; i++) {
//            int pos=(i+1)%len;int g=gas[i]-cost[i];
//            if(g>=0)
//            {
//
//                while((g+=gas[pos])>=cost[pos]&&pos!=i){
//                    g-=cost[pos];
//                    pos=(pos+1)%len;
//                }
//                if(pos==i)return i;
//            }
//
//        }
//        return -1;
//    }
    //需要反复思考！！
    public int canCompleteCircuit(int[] gas, int[] cost) {
        for (int i = 0; i < gas.length; i++) {
            gas[i] -= cost[i];
        }
        int start = 0;
        int total = 0;//总剩余油量
        int sum = 0;//局部剩余油量
        for (int i = 0; i < gas.length; i++) {
            total += gas[i];
            sum += gas[i];
            if (sum < 0) {
                start = i + 1;//唯一答案   前面累加出负数了肯定不可能
                sum = 0;
            }
        }
        if (total < 0) return -1;
        return start;
    }


    //135. 分发糖果
    public int candy(int[] ratings) {
        //从左到右
        int[] can = new int[ratings.length];
        can[0] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                can[i] = can[i - 1] + 1;
            } else {
                can[i] = 1;
            }
        }

        //从右到左
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1] && can[i] <= can[i + 1]) {
                can[i] = can[i + 1] + 1;
            }

        }
        int sum = 0;
        for (int j = 0; j < ratings.length; j++) {
            sum += can[j];
        }
        return sum;
    }

    //860. 柠檬水找零
    public boolean lemonadeChange(int[] bills) {
        int five = 0;
        int ten = 0;
        for (int b : bills
        ) {
            if (b == 20) {
                if (ten > 0) {
                    ten--;
                    if (five > 0) {
                        five--;
                    } else return false;
                } else {
                    if (five > 3) {
                        five -= 3;
                    } else return false;
                }
            } else if (b == 10) {
                ten++;
                if (five > 0) {
                    five--;
                } else return false;
            } else five++;

        }
        return true;

    }

    //406. 根据身高重建队列
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    //【1】小的在前面
                    return o1[1] - o2[1];
                } else return o2[0] - o1[0];//大的在前
            }
        });
        List<int[]> ans = new ArrayList<>();
        //插入元素 位置i==【1】
        for (int i = 0; i < people.length; i++) {
            ans.add(people[i][1], people[i]);
        }
        return ans.toArray(new int[ans.size()][]);

    }

    //452. 用最少数量的箭引爆气球
    public int findMinArrowShots(int[][] points) {
        //局部最优是不是全局最优  找不出反例
        //每次射爆最多的气球
        //排序  从后往前  用左端点射气球   后面能射爆 前面一定能射爆
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] > o2[0]) return 1;
                if (o1[0] == o2[0]) return 0;
                return -1;
            }
        });
        int sum = 0;
        for (int i = points.length - 1; i >= 0; i--, sum++) {
            int left = points[i][0];
            while (i - 1 >= 0 && left <= points[i - 1][1])//大于等于前面的右端点
            {
                i--;
            }

        }
        return sum;
    }

    //435. 无重叠区间   感觉和扎气球有点类似
    public int eraseOverlapIntervals(int[][] intervals) {
        //后面重叠的前面一定能重叠   排序后
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] > o2[0]) return 1;
                if (o1[0] == o2[0]) return 0;
                return -1;
            }
        });
        int sum = 0;
        for (int i = intervals.length - 1; i >= 0; i--) {
            int left = intervals[i][0];
            while (i - 1 >= 0 && left < intervals[i - 1][1])//大于等于前面的右端点
            {
                i--;
                sum++;
            }

        }
        return sum;


    }

    //763. 划分字母区间
    public List<Integer> partitionLabels(String S) {

        //前面出现的字母后面不能出现  划分尽可能多
        //每个字母最后出现的位置
        //切割后的第一个字母 出现的最后位置  开始如果不行继续向后(这段包含的字母后面还有)
        List<Integer> list = new LinkedList<>();
        int l = 0;
        int r;

        int[] last = new int[26];//每个字母最后出现的位置  没出现为-1
        for (int i = 0; i < 26; i++) {
            last[i] = -1;
        }
        //最后出现的位置
        for (int i = 0; i < S.length(); i++) {
            last[S.charAt(i) - 'a'] = i;
        }
        while (l < S.length()) {
            r = last[S.charAt(l) - 'a'];
            //遍历这段找到最远的字母   再遍历新的部分
            for (int i = l + 1; i <= r; i++) {
                r = Math.max(last[S.charAt(i) - 'a'], r);
            }
            list.add(r - l + 1);
            l = r + 1;
        }
        return list;


    }

    //56. 合并区间
    public int[][] merge(int[][] intervals) {
        //先排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });
        List<int[]> list = new LinkedList<>();
        for (int i = 0; i < intervals.length; i++) {
            //合并
            int l = intervals[i][0], r = intervals[i][1];
            while (i + 1 < intervals.length && r >= intervals[i + 1][0])//如果下一个接不上 后面的更接不上
            {
                i++;
                r = Math.max(intervals[i][1], r);//更新右侧
            }
            list.add(new int[]{l, r});

        }
        return list.toArray(new int[list.size()][]);


    }

    //738. 单调递增的数字
    public int monotoneIncreasingDigits(int N) {
        //判断是否是单调的
        int bits = 0;//0算0位
        int N1 = N;
        int[] num = new int[10];//
        while (N > 0) {
            num[9 - bits] = N % 10;
            N /= 10;

            bits++;
        }
        //假设已经存好了  正序
        for (int i = 10 - bits; i < 9; i++) {
            if (num[i] > num[i + 1]) {
                while (i - 1 >= 0 && num[i - 1] == num[i]) i--;//这种错误一般运行失败了才好找
                num[i]--;
                for (int j = i + 1; j < 10; j++) {
                    num[j] = 9;
                }
                int sum = 0;
                for (i = 10 - bits; i < 10; i++) {
                    sum = sum * 10 + num[i];
                }
                return sum;
            }
        }
        return N1;

    }

    //714. 买卖股票的最佳时机含手续费
    //再更低点出现之前有利可图则买
    public int maxProfit(int[] prices, int fee) {
        return 0;
    }


    //968. 监控二叉树   负责当前节点和子节点
//    public int minCameraCover(TreeNode root) {
//        if (root == null) return 0;
//        //没有叶子  必须装
//        if (root.left == null && root.right == null) return 1;
//        if(root.left==null){
//            return Math.min(f1968(root),f1968(root.right));
//        }
//        else if(root.right==null)
//        {
//            return Math.min(f1968(root),f1968(root.left));
//
//        }
//        //根装或叶子装        一个叶子必须装
//        return Math.min(f1968(root), Math.min(minCameraCover(root.right) + f1968(root.left),
//                minCameraCover(root.left) + f1968(root.right)));
//
//    }
//
//
//    //根装
//    int f1968(TreeNode root) {
//        if (root == null) return 0;
//        if (root.left == null && root.right == null) return 1;
//        if(root.left==null){//right的子节点 必须有一个装
//            return 1+Math.min(minCameraCover(root.right.right) + f1968(root.right.left),
//                    minCameraCover(root.right.left) + f1968(root.right.right));
//        }
//        else if(root.right==null)
//        {
//            return 1+Math.min(minCameraCover(root.left.right) + f1968(root.left.left),
//                    minCameraCover(root.left.left) + f1968(root.left.right));
//
//        }
//        return 1+Math.min(minCameraCover(root.right.right) + f1968(root.right.left),
//                minCameraCover(root.right.left) + f1968(root.right.right))+
//                Math.min(minCameraCover(root.left.right) + f1968(root.left.left),
//                minCameraCover(root.left.left) + f1968(root.left.right));
//
//    }
//    public int minCameraCover(TreeNode root) {
//        return minCameraCover(root, true);
//    }

    //需要负责当前节点     自己写的  超时   555
//    public int minCameraCover1(TreeNode root, boolean cur) {
//        if (root == null) return 0;
//
//        if (cur) //需要负责根节点   根或叶子装
//        {
//            if (root.left == null && root.right == null)//根必须装
//                return 1;
//            if (root.left == null) {//right和根  必须有一个负责根
//                int m1 = 1 + minCameraCover(root.right, false);
//                //right装 负责根  right的子节点不用负责自己的根
//                int m2 = 1 + minCameraCover(root.right.left, false) + minCameraCover(root.right.right, false);
//                return Math.min(m1, m2);
//            } else if (root.right == null)//left和根  必须有一个负责根
//            {
//                int m1 = 1 + minCameraCover(root.left, false);
//                //left装 负责根  left的子节点不用负责自己
//                int m2 = 1 + minCameraCover(root.left.left, false) + minCameraCover(root.left.right, false);
//                return Math.min(m1, m2);
//
//            }
//            //左右都有
//            //根装  左右不必负责自己
//            int m1 = 1 + minCameraCover(root.right, false) + minCameraCover(root.left, false);
//            //left装   right需要负责自己
//            int m2 = minCameraCover(root.right, true) +
//                    1 + minCameraCover(root.left.left, false) + minCameraCover(root.left.right, false);
//            //right装   left需要负责自己
//            int m3 = minCameraCover(root.left, true) +
//                    1 + minCameraCover(root.right.left, false) + minCameraCover(root.right.right, false);
//            return Math.min(m1, Math.min(m2, m3));
//        } else //可装可不装
//        {
//            //根装  子节点不用负责自己
//            int m1 = 1 + minCameraCover(root.right, false) + minCameraCover(root.left, false);
//            //根不装   子节点必须负责自己
//            int m2 = minCameraCover(root.right, true) + minCameraCover(root.left, true);
//            return Math.min(m1, m2);
//        }
//    }
    public int minCameraCover(TreeNode root) {
        int[] array = dfs(root);
        return array[1];
    }

    public int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[]{Integer.MAX_VALUE / 2, 0, 0};
        }
        int[] leftArray = dfs(root.left);
        int[] rightArray = dfs(root.right);
        int[] array = new int[3];
        array[0] = leftArray[2] + rightArray[2] + 1;
        array[1] = Math.min(array[0], Math.min(leftArray[0] + rightArray[1], rightArray[0] + leftArray[1]));
        array[2] = Math.min(array[0], leftArray[1] + rightArray[1]);
        return array;
    }

    //#动态规划

    //343. 整数拆分
    //给定一个正整数 n，将其拆分为至少两个正整数的和，
    // 并使这些整数的乘积最大化。 返回你可以获得的最大乘积。
    //你可以假设 n 不小于 2 且不大于 58。
    //用一个数组记录每个n的答案
    public int integerBreak(int n) {
        int[] ans = new int[n];
        ans[1] = 1;//n=2
        for (int i = 4; i < n; i++) {
            ans[i] = 1;
            //n- 一个数  从1到n-1
            for (int j = 1; j <= i; j++) {
                ans[i] = Math.max(ans[i], ans[i - j] * j);
                ans[i] = Math.max(ans[i], (i + 1 - j) * j);
            }

        }
        return ans[n - 1];

    }

    //96. 不同的二叉搜索树
    public int numTrees(int n) {
        if (n == 1) return 1;
        //二叉搜索树的几种基本形态：最小数为根  中间数为根   最大数为根
        //小为根  剩余接右侧
        //大为根  剩余接左侧
        //中为根  左小右大
        //(min,max) ---  (0,max-min) (1,max-min+1)  都等价
        //(0)  (1)  只有一种   (2)有2种
        int[] ans = new int[n + 1];
        ans[0] = 1;
        ans[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                ans[i] += ans[j] * ans[i - j - 1];
            }
        }
        return ans[n];

    }

    //#01背包
    //416. 分割等和子集
    public boolean canPartition(int[] nums) {
        //先求和   然后取数能等于sum的一半
        int sum = 0;
        //O(n)
        for (int a : nums) {
            sum += a;
        }
        if (sum % 2 != 0) return false;
        sum /= 2;
        //在容量为sum的情况下最大值如果是sum则true   如果不是则false
        int[] dp = new int[sum + 1];
        //O(n *sum)
        for (int val : nums) {//遍历物品
            for (int i = sum; i >= val; i--) {//遍历容量

                dp[i] = Math.max(dp[i], dp[i - val] + val);//这里的容量和物品值是一个
            }

        }
        return sum == dp[sum];

    }

    //1049. 最后一块石头的重量 II
    public int lastStoneWeightII(int[] stones) {
        //两块石头粉碎后 abs(x-y)
        //最小可能重量  反过来是 粉碎掉的最大重量
        //石头重量作为容量
        int sum = 0;
        //O(n)
        for (int a : stones) {
            sum += a;
        }
        int t = sum;
        sum /= 2;

        //尽量让石头分成重量相同的两堆
        int[] dp = new int[sum + 1];
        for (int val : stones) {//遍历物品
            for (int i = sum; i >= val; i--) {//遍历容量

                dp[i] = Math.max(dp[i], dp[i - val] + val);//这里的容量和物品值是一个
            }

        }
        return t - dp[sum] - dp[sum];
    }
    //换一种思路   思路不对   不一定是最大的相碰
    //    public int lastStoneWeightII(int[] stones){
    //        Arrays.sort(stones);
    //        for (int i = stones.length-1; i>=1 ; i--) {
    //            int t=stones[i]-stones[i-1];
    //            if(t==0){stones[i-1]=0;i--;continue;}
    //            int j;
    //            for (j = i-2; j>=0 ; j--) {
    //                if(t<stones[j])stones[j+1]=stones[j];
    //                else break;
    //
    //            }
    //            stones[j+1]=t;
    //        }
    //        return stones[0];
    //    }
    /**/

    //图论

    //排序
    /**/
//----------------------------------
    //494. 目标和

    //递归

    //    public int findTargetSumWays(int[] nums, int S) {
//        //数组非空，且长度不会超过 20 。
//        //初始的数组的和不会超过 1000 。
//        //保证返回的最终结果能被 32 位整数存下。
//        return findTargetSumWays(nums,S,nums.length-1);
//
//    }
//    private int findTargetSumWays(int[] nums, int S, int len){
//        if(len==0)return (nums[0]==S?1:0)+(nums[0]==-S?1:0);
//        return findTargetSumWays(nums,S+nums[len],len-1)+
//                findTargetSumWays(nums,S-nums[len],len-1);
//
//    }
    public int findTargetSumWays(int[] nums, int S) {
        //问题转化   分为两部分的和  left+right=sum   left-right=S
        //left=(sum+S)/2
        int sum = 0;
        for (int c : nums
        ) {
            sum += c;
        }
        if ((sum + S) % 2 == 1) return 0;
        int left = (sum + S) / 2;
        if (left > 1000 || left < 0) return 0;
        //转化为背包问题     从nums中取    装满left容量的背包的方法数
        int[][] dp = new int[nums.length][left + 1];
        for (int i = 1; i < left + 1; i++) {
            if (i == nums[0]) dp[0][i] = 1;
        }
        dp[0][0] = 1;
        if (nums[0] == 0) dp[0][0]++;//取0或者不取0
        for (int i = 1; i < nums.length; i++) {
//            dp[i][0]=1;//不一定是一   因为 nums中有0
            dp[i][0] = dp[i - 1][0] + (nums[i] == 0 ? dp[i - 1][0] : 0);
        }
        for (int i = 1; i < nums.length; i++) {//物品
            for (int j = left; j >= 1; j--) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i]) dp[i][j] += dp[i - 1][j - nums[i]];

            }
        }
        return dp[nums.length - 1][left];

    }

    //474
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];//0  1
        dp[0][0] = 0;
        int[] zo;
        for (String s : strs) {//遍历物品
            zo = countzeroesones(s);
            for (int i = m; i >= zo[0]; i--) {
                for (int j = n; j >= zo[1]; j--) {
                    dp[i][j] = Math.max(dp[i - zo[0]][j - zo[1]] + 1, dp[i][j]);

                }

            }
        }


        return dp[m][n];

    }

    public int[] countzeroesones(String s) {
        int[] c = new int[2];
        for (int i = 0; i < s.length(); i++) {
            c[s.charAt(i) - '0']++;
        }
        return c;
    }

    //完全背包
    //518. 零钱兑换 II
    public int change(int amount, int[] coins) {

        //物品   硬币    无限数量
        //容量   金额
        //dp值  组合数
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        //当前金额的组合数
        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j < amount + 1; j++) {
                dp[j] += dp[j - coins[i]];
                System.out.print(dp[j]);

            }
            System.out.println();
        }
        return dp[amount];

    }

    public int change1(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        //当前金额的排列数
        for (int j = 0; j < amount + 1; j++) {
            for (int i = 0; i < coins.length; i++) {
                if (j >= coins[i])
                    dp[j] += dp[j - coins[i]];
                System.out.print(dp[j]);


            }
            System.out.println();

        }
        return dp[amount];

    }

    //377. 组合总和 Ⅳ     请注意，顺序不同的序列被视作不同的组合
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int j = 0; j < target + 1; j++) {
            for (int i = 0; i < nums.length; i++) {
                if (j >= nums[i])
                    dp[j] += dp[j - nums[i]];


            }

        }
        return dp[target];
    }

    //322 零钱兑换    凑成amount的最少的硬币数量
//    public int coinChange(int[] coins, int amount) {
//        int []dp=new int[amount+1];
//        //每个容量只有第一个物品
//
//        Arrays.fill(dp, Integer.MAX_VALUE);
//        dp[0]=0;
//        for (int i = 0; i <coins.length ; i++) {
//            for (int j = coins[i]; j <amount+1 ; j++) {
//                if(dp[j-coins[i]]!=Integer.MAX_VALUE)
//                {
//                    dp[j]=Math.min(dp[j], dp[j-coins[i]]+1);
//                }
//
//            }
//        }
//        return dp[amount]==Integer.MAX_VALUE?-1:dp[amount];
//    }
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        //每个容量只有第一个物品
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j < amount + 1; j++) {
                if (dp[j - coins[i]] != -1) {
                    if (dp[j] != -1)
                        dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
                    else dp[j] = dp[j - coins[i]] + 1;
                }

            }
        }
        return dp[amount];
    }

    //279. 完全平方数
    // 给你一个整数 n ，返回和为 n 的完全平方数的 最少数量
    //可以重复
    public int numSquares(int n) {
        //一定有解  因为1是完全平方数
        //先求小于n的完全平方数
        int[] dp = new int[n + 1];
        //每个容量只有第一个物品
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 0; i * i <= n; i++) {
            int val = i * i;
            for (int j = val; j < n + 1; j++) {
                if (dp[j - val] != -1) {
                    if (dp[j] != -1)
                        dp[j] = Math.min(dp[j], dp[j - val] + 1);
                    else dp[j] = dp[j - val] + 1;
                }

            }
        }
        return dp[n];

    }

    //139. 单词拆分
    //给定一个非空字符串 s 和一个包含非空单词的列表 wordDict，
    //判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
    //注意你可以重复使用字典中的单词
    public boolean wordBreak(String s, List<String> wordDict) {
        HashSet<String> wordDictSet = new HashSet<>(wordDict);

        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    //数论
    //62. 不同路径
    int uniquePaths(int m, int n) {
        //处理溢出问题
        return Combin(m + n - 2, m - 1);
    }

    //带防止溢出的组合数
    private int Combin(int m, int n) {//C(m,n)=m!/(n!*(m-n)!)
        long r = 1;
        //分子
        int low = m - n + 1;
        for (int i = m; i >= low; i--) {//m!/(m-n)!
            r *= i;
            while (n > 0 && r % n == 0) {
                r /= n;
                n--;
            }
        }
        return (int) r;

    }

    //高级数据结构


    //**************************自己做***************************************//
    //679. 24 点游戏
    boolean[] used679 = new boolean[4];

    public boolean judgePoint24(int[] nums) {
        //数只能用一次  符号可以用多次

        return back679(new double[]{nums[0], nums[1], nums[2], nums[3]}, 4);
    }

    //挑两个数计算  再重复    直到剩余一个数
    boolean back679(double[] nums, int need) {
        if (need == 1) {
            for (int i = 0; i < 4; i++) {
                if (!used[i]) {
                    return Math.abs(nums[i] - 24) <= 0.0001;
                }
            }
            return false;
        }
        //回溯法遍历
        for (int i = 0; i < 4; i++) {
            if (!used[i]) {
                used[i] = true;
                for (int j = 0; j < 4; j++) {
                    if (!used[j]) {
                        double t = nums[j];
                        nums[j] = t + nums[i];
                        if (back679(nums, need - 1)) return true;
                        nums[j] = t - nums[i];
                        if (back679(nums, need - 1)) return true;
                        nums[j] = nums[i] - t;
                        if (back679(nums, need - 1)) return true;
                        nums[j] = t * nums[i];
                        if (back679(nums, need - 1)) return true;
                        if (t != 0) {
                            nums[j] = nums[i] / t;
                            if (back679(nums, need - 1)) return true;
                        }
                        if (nums[i] != 0) {
                            nums[j] = t / nums[i];
                            if (back679(nums, need - 1)) return true;
                        }
                        nums[j] = t;
                    }
                }
                used[i] = false;

            }
        }
        return false;
    }


    //815
//    public int numBusesToDestination(int[][] routes, int source, int target) {
//        //找到source车
//        //map每个站点所在的车的队列
//        //当前车能到的下一个车 广度优先
//        HashMap<Integer,Deque<Integer>>map=new HashMap<>();
//        List<Integer>listS=new ArrayList<>();
//        Set<Integer>set=new HashSet<>();//target车
//
//        for (int i = 0; i < routes.length ; i++) {
//            for (int j = 0; j < routes[i].length ; j++) {
//                Deque<Integer>t=map.getOrDefault(routes[i][j],new LinkedList<>());
//                t.offerLast(i);
//                map.put(routes[i][j],t);
//                if(source==routes[i][j])listS.add(i);
//                if(routes[i][j]==target)set.add(i);
//            }
//        }
//        //souce车
//        int min=Integer.MAX_VALUE;
//        //当前车
//        int step=0;
//        while (step<min){
//            int stept=0;
//
//            for (int cur:listS
//                 ) {
//                //cur站能去的车
//                Deque<Integer>integerDeque=map.get(cur);
//
//                while (stept<=step){
//                    Deque<Integer>integerDeque1=new LinkedList<>();
//                    while (!integerDeque.isEmpty())
//                    {
//                        int current=integerDeque.pollFirst();
//                        for
//                        integerDeque1.addAll(map.c);
//
//                    }
//                    //每一条路  target在不在这车
//                    if(set.contains(cur)){min=Math.min(stept,min);}
//
//                    stept++;
//                }
//            }
//            step++;
//        }
//        return min;
//        //先0步
//
//
//
//
//    }
//1411
    //超时
    int[] ints = new int[]{4, 4, 4};
    //    public int numOfWays(int n) {
//        if(n==0){r1411++;return 0;}
//        //回溯
//
//        //填120
//        int[]ints1=ints.clone();
//        for (int i = 0; i <3 ; i++) {
//            if(i==ints[0])continue;
//            for (int j = 0; j <3 ; j++) {
//                if(j==ints[1]||j==i)continue;
//                for (int k = 0; k <3 ; k++) {
//                    if(k==ints[2]||k==j)continue;
//                    ints[0]=i;ints[1]=j;ints[2]=k;
//                    numOfWays(n-1);
//                    ints=ints1;
//                    ints1=ints.clone();
//                }
//            }
//        }
//        return (int) (r1411%(Math.pow(10,9) + 7));
//
//
//
//
//    }
    //每种情况下面的可能情况
//超时
    HashMap<List<Integer>, List<List<Integer>>> map1411 = new HashMap<>();

//    long r11411=0;
//    void init1141(){
//        //初始化map
//        for (int i = 0; i <3 ; i++) {
//            for (int j = 0; j <3 ; j++) {
//                for (int k = 0; k <3 ; k++) {
//                    if(i!=j&&j!=k){
//
//                        List<List<Integer>>list=new ArrayList<>();
//                        for (int l = 0; l <3 ; l++) {
//                            for (int m = 0; m <3 ; m++) {
//                                for (int o = 0; o <3 ; o++) {
//                                    if(l!=m&&m!=o&&l!=i&&m!=j&&o!=k)
//                                    {
//                                        list.add(Arrays.asList(l,m,o));
//                                    }
//                                }
//                            }
//                        }
//
//                        map1411.put(Arrays.asList(i,j,k),list);
//
//                    }
//                }
//            }
//        }
//    }
//    public int numOfWays(int n){
//        init1141();
//        for (Map.Entry<List<Integer>, List<List<Integer>>> e:map1411.entrySet()
//             ) {
//            dfs1141(n-1, e.getKey());
//        }
//
//        return (int)(r11411%(Math.pow(10,9)+7));
//
//
//    }
//    void dfs1141(int n, List<Integer> key){
//        if(n==0){r11411++;return;}
//        List<List<Integer>>list=map1411.get(key);
//        for (List<Integer> v:list
//             ) {
//            dfs1141(n-1,v);
//        }
//    }

    /**/
    //12个   6A 6B      A 2A 3B  B 2A 2B
    //超时  改成数组


    public int numOfWays(int n) {
        int div = 1000000007;
        long[][] nums = new long[n][2];

        nums[0][0] = 1;
        nums[0][1] = 1;
        for (int i = 1; i < n; i++) {
            nums[i][0] = 2 * (nums[i - 1][0] % div + nums[i - 1][1] % div);
            nums[i][1] = 2 * nums[i - 1][0] % div + 3 * nums[i - 1][1] % div;
        }
        long r1411 = 6 * (nums[n - 1][0] % div + nums[n - 1][1] % div);

        return (int) (r1411 % div);

    }

    long A1411(int n) {
        if (n == 0) return 1;
        return 2 * A1411(n - 1) + 2 * B1411(n - 1);

    }

    long B1411(int n) {
        if (n == 0) return 1;
        return 2 * A1411(n - 1) + 3 * B1411(n - 1);

    }

    // 42接雨水
    public int trap(int[] height) {
        if (height.length == 0) return 0;
        int[] l = new int[height.length];
        int[] r = new int[height.length];
        int current = height[0];
        for (int i = 1; i < height.length - 1; i++) {
            if (height[i] > current) {
                current = height[i];
                l[i] = height[i];
            } else l[i] = current;
        }
        current = height[height.length - 1];
        for (int i = height.length - 2; i > 0; i--) {
            if (height[i] > current) {
                current = height[i];
                r[i] = height[i];
            } else r[i] = current;

        }
        int sum = 0;
        for (int i = 1; i < height.length - 1; i++) {
            height[i] = (l[i] > r[i]) ? r[i] - height[i] : l[i] - height[i];
            sum += height[i];
        }
        return sum;


        //左侧最近的极值点  右侧最近的极值点


    }

    //124
    int max124 = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        //第一次遍历   计算：以当前节点为头 向下的最大路径(单方向)
        //第二次遍历   经过当前节点的最大路径(双)
        //两次都用后序遍历   左右中
        //list存原节点值
        dfs1241(root);
        return max124;

    }

    int dfs1241(TreeNode root) {
        if (root == null) return 0;
        int l = dfs1241(root.left);
        int r = dfs1241(root.right);
        int v = root.val;
        v = Math.max(v, Math.max(v + l + r, Math.max(v + l, v + r)));
        max124 = Math.max(v, max124);
        root.val = Math.max(root.val, root.val + Math.max(l, r));//加其中一个或者不加
        return root.val;

    }

    //max val , val+l+r val+l val+r
    public boolean isMatch(String s, String p) {
        if (p.equals(".*")) return true;
        //true   s和p都到了末尾
        //a*a   .*
        //a*   **   a*b   ab
        //数组 a  .   最少最多个数
        //s p都转化为   数组    最多最少
        //遇到a*   两种情况   匹配a   不匹配a
        //字符串数组 a* .*  .  a  b
        List<String> list = new ArrayList<>();
        for (int i = 0; i < p.length() - 1; i++) {
            if (p.charAt(i + 1) == '*') {
                list.add(p.charAt(i) + "*");
                i++;
            } else list.add(p.charAt(i) + "");
        }
        if (p.charAt(p.length() - 1) != '*') list.add(p.charAt(p.length() - 1) + "");
        return isMatch10(s.toCharArray(), 0, list, 0);

    }

    boolean isMatch10(char[] s, int l1, List<String> p, int r1) {
        if (l1 == s.length && r1 == p.size()) return true;
        if (r1 == p.size()) return false;
        if (l1 == s.length) {
            //必须后面全是*
            for (int i = r1; i < p.size(); i++) {
                if (p.get(i).length() < 2) return false;
            }
            return true;
        }
        char t = s[l1];
        char tp = p.get(r1).charAt(0);
        if (p.get(r1).length() > 1) {//有 *   可以不匹配当前

            if (tp == '.' || tp == t) {
                //能匹配   3种选择  匹配 下次从pi+1开始   匹配 下次还从pi开始 不匹配
                return isMatch10(s, l1 + 1, p, r1 + 1) ||
                        isMatch10(s, l1 + 1, p, r1) || isMatch10(s, l1, p, r1 + 1);
            } else {
                //无法匹配
                return isMatch10(s, l1, p, r1 + 1);
            }
        } else {
            //必须匹配
            if (tp == '.' || tp == t) {
                return isMatch10(s, l1 + 1, p, r1 + 1);
            } else return false;
        }


    }

    //
    public int maximalSquare(char[][] matrix) {
        //以左上角开始的最大正方形
        //最后一行一列 不用动  1  /  0
        char ans = '0';
        for (int i = matrix.length - 2; i >= 0; i--) {
            for (int j = matrix[0].length - 2; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    if (matrix[i][j + 1] == '0' || '0' == matrix[i + 1][j]) {
                        ans = (char) Math.max(ans, matrix[i][j]);
                        continue;
                    }
                    if (matrix[i][j + 1] != matrix[i + 1][j]) {
                        matrix[i][j] += Math.min(matrix[i][j + 1], matrix[i + 1][j]) - '0';
                    } else {
                        if (matrix[i + matrix[i][j + 1] - '0'][j + matrix[i][j + 1] - '0'] >= '1') {
                            matrix[i][j] += matrix[i][j + 1] - '0';
                        } else matrix[i][j] += matrix[i][j + 1] - '0' - 1;

                    }
                    ans = (char) Math.max(ans, matrix[i][j]);
                }

            }
        }
        ans -= '0';
        if (ans == 0) {
            //最后一行最后一列
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][matrix[0].length - 1] == '1') return 1;
            }
            for (int i = 0; i < matrix[0].length; i++) {
                if (matrix[matrix.length - 1][i] == '1') return 1;
            }

        }
        return ans * ans;
    }

    //32
    public int longestValidParentheses(String s) {
        int ans = 0;
        int len = s.length();
        int[] nums = new int[len];

        for (int i = 1; i < len; i++) {
            if (s.charAt(i) == ')') {

                if (s.charAt(i - 1) == '(') {
                    nums[i] = 2;
                    if (i - 2 >= 0) nums[i] += nums[i - 2];
                } else {//   )

                    if (i - nums[i - 1] - 1 >= 0 && s.charAt(i - nums[i - 1] - 1) == '(') {
                        nums[i] = 2 + nums[i - 1];
                        if (i - nums[i - 1] - 2 >= 0) {
                            nums[i] += nums[i - nums[i - 1] - 2];
                        }
                    }
                }
                ans = Math.max(ans, nums[i]);
            }
        }
        return ans;


    }

    //84
    public int largestRectangleArea(int[] heights) {
        //自己的容量
        int max = 0;
        //基础n方：   遍历每个柱子的高度   用这个高度往左右试探
        for (int i = 0; i < heights.length; i++) {
            int h = heights[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (heights[j] < h) {
                    break;
                }
            }
            int k = i + 1;
            for (; k < heights.length; k++) {
                if (heights[k] < h) {
                    break;
                }

            }
            max = Math.max(max, h * (k - j - 1));
        }
        return max;

    }

    //312. 戳气球
    public int maxCoins(int[] nums) {
        //戳气球的逆过程  装气球   mid的左右时区间边界值！
        //定义数组 表示区间[i，j]  能得到的最大硬币数    化整为零
        int n = nums.length;
        int[][] Coin = new int[n + 2][n + 2];// 两侧的边界

        int[] val = new int[n + 2];
        for (int i = 1; i < n + 1; i++) {
            val[i] = nums[i - 1];
        }
        val[0] = val[n + 1] = 1;
        return find312(val, 1, n, Coin);
    }

    int find312(int[] nums, int left, int right, int[][] Coin) {
        if (left > right) return 0;
        if (Coin[left][right] != 0) return Coin[left][right];

//        int max=1;
//        for (int i = left; i <=right ; i++) {//
//            max=Math.max(max,
//                    find312(nums,i+1,right,Coin)+
//                            nums[left-1]*nums[i]*nums[right+1]+
//                    find312(nums,left,i-1,Coin));
//        }
//        Coin[left][right]=Math.max(max,Coin[left][right]);//这句话理解的不好！
        for (int i = left; i <= right; i++) {//
            Coin[left][right] = Math.max(Coin[left][right],
                    find312(nums, i + 1, right, Coin) +
                            nums[left - 1] * nums[i] * nums[right + 1] +
                            find312(nums, left, i - 1, Coin));
        }
        return Coin[left][right];
    }
    //乘积为负数
    //正数：乘后面连续乘积为正数的
    //负数：乘后面连续乘积为负数的

    //都是正数     乘0变小  乘其他绝地址变大 或保持   计算每个连续的正数
    //每个位置记录 前面到本位置累乘的结果
    //找到最后一个正数和最后一个负数   最后一个负数除以第一个负数 (不能是同一个)    和最后一个正数比较
    //152. 乘积最大子数组
    //如果没有0    全部累乘 如果是负数则找到两侧的负数
    public int maxProduct(int[] nums) {
        if (nums.length == 1) return nums[0];
        //两个数以上  结果至少是0   //0不可跨越
        //从当前到最后   最大连续乘积   //倒数第二个开始
        //f(i)=
        //如果i 是正数 f(i+1)=0  f(i)= i    f(i+1)>0  f(i)*=f(i+1)
        //i为0 f(i)=0;
        //i是负数 f(i+1)=0 f(i)=0     f(i+1)>0  找到
        for (int i = 1; i < nums.length; i++) {
            nums[i] *= nums[i - 1];
        }
        int lastNeg = -1;//可能不存在
        int lastPos = -1;//可能不存在
        int firstNeg = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0) {
                firstNeg = i;
                break;
            }
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] < 0) {
                lastNeg = i;
                break;
            }
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] > 0) {
                lastPos = i;
                break;
            }
        }
        int ans = lastPos == -1 ? 0 : nums[lastPos];
        if (lastNeg == firstNeg) {

        }
        return 0;
    }

    public boolean canCross(int[] stones) {
        //
        int[][] dp = new int[stones.length][stones.length];//石头的下标   步数
//访问到最后一块石头
        for (int i = 0; i < stones.length; i++) {
            dp[stones.length - 1][i] = 1;
        }
        return dfs4438(stones, 0, 0, dp);
    }

    private boolean dfs4438(int[] stones, int i, int k, int[][] dp) {
        if (dp[i][k] != 0) return dp[i][k] == 1;
        //用 k能否到达后面的石头
        for (int j = i + 1; j < stones.length; j++) {//后面的每个石头
            int dist = stones[j] - stones[i];
            //步数可能为0- i
            if (k == dist || k + 1 == dist || k - 1 == dist) {
                if (dfs4438(stones, j, dist, dp)) {
                    dp[i][k] = 1;
                    return true;
                }

            }


        }
        dp[i][k] = -1;

        return false;


    }

    //423
    public String originalDigits(String s) {
        int[] alphabeta = new int[26];
        int[] nums = new int[10];
        for (char c : s.toCharArray()
        ) {
            alphabeta[c - 'a']++;
        }
        if (alphabeta['z' - 'a'] > 0) {
            nums[0] = alphabeta['z' - 'a'];
            alphabeta['e' - 'a'] -= alphabeta['z' - 'a'];
            alphabeta['r' - 'a'] -= alphabeta['z' - 'a'];
            alphabeta['o' - 'a'] -= alphabeta['z' - 'a'];

        }
        if (alphabeta['w' - 'a'] > 0) {
            nums[2] = alphabeta['w' - 'a'];
            alphabeta['t' - 'a'] -= alphabeta['w' - 'a'];
            alphabeta['o' - 'a'] -= alphabeta['w' - 'a'];
        }
        if (alphabeta['u' - 'a'] > 0) {
            nums[4] = alphabeta['u' - 'a'];
            alphabeta['f' - 'a'] -= alphabeta['u' - 'a'];
            alphabeta['o' - 'a'] -= alphabeta['u' - 'a'];
            alphabeta['r' - 'a'] -= alphabeta['u' - 'a'];

        }
        if (alphabeta['x' - 'a'] > 0) {
            nums[6] = alphabeta['x' - 'a'];
            alphabeta['s' - 'a'] -= alphabeta['x' - 'a'];
            alphabeta['i' - 'a'] -= alphabeta['x' - 'a'];
        }
        if (alphabeta['g' - 'a'] > 0) {
            nums[8] = alphabeta['g' - 'a'];
            alphabeta['e' - 'a'] -= alphabeta['g' - 'a'];
            alphabeta['i' - 'a'] -= alphabeta['g' - 'a'];
            alphabeta['h' - 'a'] -= alphabeta['g' - 'a'];
            alphabeta['t' - 'a'] -= alphabeta['g' - 'a'];
        }
        if (alphabeta['o' - 'a'] > 0) {
            nums[1] = alphabeta['o' - 'a'];
            alphabeta['n' - 'a'] -= alphabeta['o' - 'a'];
            alphabeta['e' - 'a'] -= alphabeta['o' - 'a'];
        }
        if (alphabeta['f' - 'a'] > 0) {
            nums[5] = alphabeta['f' - 'a'];
            alphabeta['i' - 'a'] -= alphabeta['f' - 'a'];
            alphabeta['v' - 'a'] -= alphabeta['f' - 'a'];
            alphabeta['e' - 'a'] -= alphabeta['f' - 'a'];
        }
        if (alphabeta['i' - 'a'] > 0) {
            nums[9] = alphabeta['i' - 'a'];
            alphabeta['n' - 'a'] -= alphabeta['i' - 'a'];
            alphabeta['n' - 'a'] -= alphabeta['i' - 'a'];
            alphabeta['e' - 'a'] -= alphabeta['i' - 'a'];
        }
        if (alphabeta['v' - 'a'] > 0) {
            nums[7] = alphabeta['v' - 'a'];
            alphabeta['s' - 'a'] -= alphabeta['v' - 'a'];
            alphabeta['e' - 'a'] -= alphabeta['v' - 'a'];
            alphabeta['e' - 'a'] -= alphabeta['v' - 'a'];
            alphabeta['n' - 'a'] -= alphabeta['v' - 'a'];
        }
        if (alphabeta['h' - 'a'] > 0) {
            nums[3] = alphabeta['h' - 'a'];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < nums[i]; j++) {
                sb.append(i);
            }
        }
        return sb.toString();

    }

    //264. 丑数 II
    public int nthUglyNumber(int n) {
        if (n == 1) return 1;
        int r = 1;
        int two = 0;
        int three = 0;
        int five = 0;
        while (n > 1) {//  6/5  3/2 5/3 4/3 5/4  10/9  1234 5/4 6/5 9/8 10/9 32/27
            //ui/ui-1=u/u ->1 2 3 5
            if (five > 0) {
                r *= 6;
                r /= 5;
                five--;
                three++;
                two++;
            } else if (two >= 2) {
                r *= 5;
                r /= 4;
                two -= 2;
                five++;
            } else if (two > 0) {
                r *= 3;
                r /= 2;
                three++;
                two--;
            } else if (three > 0) {
                r *= 5;
                r /= 3;
                five++;
                three--;
            } else {
                two++;
                r *= 2;
            }
            n--;
        }
        return r;


    }

    //72
    public int minDistance(String word1, String word2) {
        //horse   ros
        //插入1  插入2  修改1
        //horse ros
        //horse rors
        //horse hors
        //horse horse

        return 0;
    }


    /*数组*/
//495
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        if (timeSeries.length == 0) return 0;
        int time = duration;
        int end = timeSeries[0] + duration;//结束位置
        for (int i = 1; i < timeSeries.length; i++) {
            if (timeSeries[i] >= end) {
                time += duration;
                end = timeSeries[i] + duration;
            } else {
                int v = timeSeries[i] + duration;
                if (end < v) {
                    time += v - end;
                    end = v;
                }
            }

        }
        return time;

    }

    //189
    public void rotate(int[] nums, int k) {
        //一次移动一个格子
        for (int i = 0; i < k; i++) {
            int t = nums[nums.length - 1];
            System.arraycopy(nums, 0, nums, 1, nums.length - 1);
            nums[0] = t;

        }

    }

    //54    矩阵顺时针遍历
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new LinkedList<>();
        //右  下  左  上
        int up = 0;
        int down = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        while (up <= down && left <= right) {
            for (int i = left; i <= right; i++) {
                list.add(matrix[up][i]);
            }
            for (int i = up + 1; i < down; i++) {
                list.add(matrix[i][right]);
            }
            if (up != down)
                for (int i = right; i >= left; i--) {
                    list.add(matrix[down][i]);

                }
            if (left != right)
                for (int i = down - 1; i > up; i--) {
                    list.add(matrix[i][left]);

                }
            up++;
            left++;
            down--;
            right--;
        }
        return list;

    }

    //498对角线遍历
    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return new int[0];
        int[] ans = new int[matrix.length * matrix[0].length];
        int index = 0;
        boolean flag = false;//向上
        //如何开始   从左上角  向下 向右     向右向下
        int r = matrix.length;
        int c = matrix[0].length;
        int r1 = 0, c1 = 0, r2 = 0, c2 = 0;
        while (!(r1 == r - 1 && c1 == c - 1)) {
            if (flag) {
                //向下
                for (int i = r2, j = c2; j >= 0 && i < r; i++, j--, index++) {
                    ans[index] = matrix[i][j];
                }
            } else {
                //向上
                for (int i = r1, j = c1; i >= 0 && j < c; i--, j++, index++) {
                    ans[index] = matrix[i][j];
                }
            }
            flag = !flag;
            if (r1 == r - 1) {
                c1++;
            } else r1++;
            if (c2 == c - 1) {
                r2++;
            } else c2++;
        }
        ans[index] = matrix[r - 1][c - 1];
        return ans;
    }

    //48旋转
    public void rotate(int[][] matrix) {
        //由外圈到内圈
        int n = matrix.length;
        int cout = n / 2;//圈数
        int cur;

        for (int i = 0; i < cout; i++) {//上侧 左侧下标
            for (int k = 0; k < n - i - 1; k++) {
                //左上右下
                cur = matrix[i][i];
                for (int j = i; j < n - i - 1; j++) {
                    matrix[j][i] = matrix[j + 1][i];
                }
                int t = matrix[i][n - i - 1];
                for (int j = i + 1; j < n - i - 1; j++) {
                    matrix[i][j + 1] = matrix[i][j];
                }
                matrix[i][i + 1] = cur;
                cur = t;
                t = matrix[n - i - 1][n - i - 1];
                for (int j = i + 1; j < n - i - 1; j++) {
                    matrix[j + 1][n - i - 1] = matrix[j][n - i - 1];
                }
                matrix[i + 1][n - i - 1] = cur;
                cur = t;
                for (int j = n - i - 3; j >= 0; j--) {
                    matrix[n - i - 1][j] = matrix[n - i - 1][j + 1];
                }
                matrix[n - i - 1][n - i - 2] = cur;


            }
        }


    }

    /*数组*/
    /*字符串*/
    //467.环绕字符串中唯一的子字符串
    public int findSubstringInWraproundString(String p) {
        if (p.length() == 0) return 0;
        //前缀和
        //记录以每个字母结尾的最大长度
        int[] MaxLen = new int[26];
        MaxLen[p.charAt(0) - 'a'] = 1;
        int sum = 0;
        int len = 1;
        for (int i = 1; i < p.length(); i++) {
            if (p.charAt(i) - p.charAt(i - 1) == 1 || p.charAt(i) - p.charAt(i - 1) == -25) {
                len++;
            } else len = 1;
            if (len > MaxLen[p.charAt(i) - 'a']) {
                MaxLen[p.charAt(i) - 'a'] = len;
            }
        }
        for (int i = 0; i < 26; i++) {
            sum += MaxLen[i];
        }
        return sum;
    }


    //299. 猜数字游戏
    public String getHint(String secret, String guess) {
        //统计出现的有多少=x+y
        //位置一样的是x  剩下的是y
        //0-9
        int[] s = new int[10];
        int[] g = new int[10];
        for (char c : secret.toCharArray()
        ) {
            s[c - '0']++;
        }
        for (char c : guess.toCharArray()
        ) {
            g[c - '0']++;
        }
        int xy = 0;
        for (int i = 0; i < 10; i++) {
            xy += Math.min(s[i], g[i]);
        }
        int x = 0;
        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i)) x++;
        }
        int y = xy - x;
        return x + "A" + y + "B";


    }

    /*字符串*/
    /*数与位*/
    /*数与位*/
    /*栈与递归*/
    /*栈与递归*/
    /*链表*/
    /*链表*/
    /*哈希表*/
    /*哈希表*/
    /*贪心算法*/
    //646. 最长数对链    [官方题解]
    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, (a, b) -> a[1] - b[1]);
        int cur = Integer.MIN_VALUE, ans = 0;
        for (int[] pair : pairs)
            if (cur < pair[0]) {
                cur = pair[1];
                ans++;
            }
        return ans;
    }

    /*贪心算法*/
    /*双指针*/
    //16. 最接近的三数之和
    public int threeSumClosest(int[] nums, int target) {
        return 0;
    }

    /*双指针*/
    /*树*/
    //114. 二叉树展开为链表
    public void flatten(TreeNode root) {
        flatten114(root);

    }

    TreeNode flatten114(TreeNode root) {
        if (root == null) return null;
        TreeNode right = root.right;
        root.right = flatten114(root.left);
        root.left = null;
        TreeNode tmp = root;
        while (tmp.right != null) {
            tmp = tmp.right;
        }
        tmp.right = flatten114(right);
        return root;


    }

    /*树*/
    /*图与搜索*/
    /*图与搜索*/
    /*二分查找*/
    /*二分查找*/
    /*图与搜索*/
    /*图与搜索*/
    /*动态规划*/
    /*动态规划*/
    /*数据结构*/
    /*数据结构*/


    /*HOT100*/
    //207 课程表
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        return false;
    }

    //560.和为K的子数组
    //前缀和+哈希表
    //pre[i]-pre[j]=k    计算i结尾的个数=找前缀和为pre[i]-k
    HashMap<Integer, Integer> map560 = new HashMap<>();

    public int subarraySum(int[] nums, int k) {

        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1];
        }
        for (int v : nums
        ) {
            map560.put(v, map560.getOrDefault(v, 0) + 1);
        }
        int sum = 0;
        for (int v : nums
        ) {

            sum += map560.getOrDefault(v - k, 0);
        }
        return sum;


    }


    //200 岛屿数量
    public int numIslands(char[][] grid) {

        //每个位置对应的区域号   每个区域包含的位置号
        //上左 如果连通 加入到上左的区域  合并上左：把左包含的位置的区域全改成上  把左包含的位置全加入上中
        int[][][] pos2Region = new int[grid.length][grid[0].length][2];
        HashMap<int[], HashSet<int[]>> reg2Pos = new HashMap<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {//是陆地
                    if (i - 1 >= 0 && grid[i - 1][j] == '1') {
                        pos2Region[i][j] = pos2Region[i - 1][j];
                        reg2Pos.get(pos2Region[i - 1][j]).add(new int[]{i, j});
                    }
                    if (j - 1 >= 0 && grid[i][j - 1] == '1') {

                        pos2Region[i][j] = pos2Region[i][j - 1];
                        reg2Pos.get(pos2Region[i][j - 1]).add(new int[]{i, j});

                    }
                    if (i - 1 >= 0 && j - 1 >= 0 && grid[i][j - 1] == '1' && grid[i - 1][j] == '1') {
                        if (reg2Pos.get(pos2Region[i - 1][j]).contains(new int[]{i, j}) &&
                                reg2Pos.get(pos2Region[i][j - 1]).contains(new int[]{i, j})) {
                            int[] reg = pos2Region[i - 1][j];
                            for (int[] pos : reg2Pos.get(new int[]{i, j - 1})
                            ) {
                                pos2Region[pos[0]][pos[1]] = reg;
                                reg2Pos.get(pos2Region[i - 1][j]).add(pos);
                            }
                            reg2Pos.remove(new int[]{i, j - 1});
                        }
                    }
                    if (i - 1 >= 0 && j - 1 >= 0 && grid[i - 1][j] == '0' && grid[i][j - 1] == '0') {
                        HashSet<int[]> s = new HashSet<>();
                        s.add(new int[]{i, j});
                        reg2Pos.put(new int[]{i, j}, s);
                        pos2Region[i][j] = new int[]{i, j};
                    }
                }
            }
        }
        return reg2Pos.size();
    }

    //437. 路径总和 III
    public int pathSum(TreeNode root, int sum) {
        return pathSum437(root, sum);


    }

    public int pathSum437(TreeNode root, int sum) {
        if (root == null) return 0;
        return pathSum437(root.left, sum) + pathSum437(root.right, sum)
                + help437(root, sum);
    }

    int help437(TreeNode root, int sum) {
        //最简单的方式 递归
        if (root == null) return 0;
        int r;
        if (sum == root.val) r = 1 +

                +help437(root.right, 0)
                + help437(root.left, 0);
            //对每个节点执行算法
        else
            r = help437(root.right, sum - root.val)
                    + help437(root.left, sum - root.val);
        return r;
    }

    //739.每日温度
    public int[] dailyTemperatures(int[] T) {
        //下个个更高气温的位置

        //从后往前遍历每个温度最后的位置
        int[] lastPos = new int[101];//30-100
        for (int i = T.length - 1; i >= 0; i--) {
            lastPos[T[i]] = i;//更新温度最后位置
            int j = T[i] + 1;
            T[i] = Integer.MAX_VALUE;
            for (; j < 101; j++) {
                if (lastPos[j] > i) {
                    T[i] = Math.min(lastPos[j] - i, T[i]);//直接在原数组修改
                }
            }
            //一个都没有
            if (T[i] == Integer.MAX_VALUE)
                T[i] = 0;
        }
        return T;

    }

    //301. 删除无效的括号
    HashSet<String> hashSet301 = new HashSet<>();
    int MaxLen301 = 0;

    public List<String> removeInvalidParentheses(String s) {
        //字母在哪都不影响
        //删除最小数量的无用 ，可能删 0个或多个
        //暴力的话
        //遍历删除数量和删除位置
        // 0-len   i *C(len,i) <2^25*len
        //括号匹配验证 s.len
        //去除的用 * 表示  占位  每次去除一个
        char[] chars = s.toCharArray();
        List<String> list = new ArrayList<>();
        if (isGood301(chars)) {

            list.add(s);
            return list;
        }

        for (int i = 0; i < s.length(); i++) {
            dfs301(chars, i, s.length() - 1);//去除第i个
        }

        for (String string : hashSet301) {
            if (string.length() == MaxLen301) list.add(string);
        }
        return list;
    }

    void dfs301(char[] chars, int index, int len) {
        if (len < MaxLen301) return;
        //只去括号
        if (chars[index] != '(' && chars[index] != ')') {
            return;
        }

        char past = chars[index];
        chars[index] = '*';
        if (isGood301(chars)) {
            hashSet301.add(C2S301(chars));
            MaxLen301 = len;
            chars[index] = past;
            return;
        }
        for (int i = index+1; i < chars.length; i++) {
            dfs301(chars, i, len - 1);
        }
        chars[index] = past;


    }

    //检查括号匹配
    boolean isGood301(char[] chars) {
        //跳过a-z 和 *

        int lcount=0;//左括号数和右括号数
        for (char c : chars) {
            if(c=='(')lcount++;
            else if(c==')'){
                if(lcount>0){lcount--;}
                else return false;
            }
        }

        return lcount==0;

    }

    //char[] to String
    String C2S301(char[] chars) {
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c != '*') sb.append(c);
        }
        return sb.toString();

    }
    /*HOT100*/

    /*其他*/
    //子序和 904 992 1109
    //795. 区间子数组个数
    public int numSubarrayBoundedMax(int[] A, int L, int R) {

        return 0;
    }
    /*其他*/
//面试题59 - I. 滑动窗口的最大值
    class MyQue{
        Deque<Integer> deque = new LinkedList<>();
        Deque<Integer> help = new LinkedList<>();
        void pop(){
            int k=deque.pollFirst();
            if(k==help.peekFirst()){
                help.pollFirst();
            }
        }
        void push(int k){
            while(!help.isEmpty()&&help.peekLast()<k){
                help.pollLast();
            }
            help.offerLast(k);
            deque.offerLast(k);
        }
        int getMax(){
            return help.peekFirst();
        }
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums.length==0)return new int[0];
        int[]ans=new int[nums.length-k+1];

        //让找出最大最小值复杂度都是1  两个辅助栈
        //辅助队列  加入的时候 前面比当前数小的全删掉
        MyQue myQue=new MyQue();
        for(int i=0;i<k;i++){
            myQue.push(nums[i]);
        }
        ans[0]=myQue.getMax();
        for(int i=k,j=1;i<nums.length;i++,j++)
        {
            myQue.pop();
            myQue.push(nums[i]);
            ans[j]=myQue.getMax();
        }
        return ans;



    }
    public boolean isCompleteTree(TreeNode root) {

        //如果本层一个节点子节点为空  其后面节点的子节点都为空 而且下一行是最后一行 即下一层无子节点
        Deque<TreeNode>queue=new LinkedList<>();
        Deque<TreeNode>next=new LinkedList<>();
        queue.offerLast(root);
        int num=1;
        boolean flag=true;
        while(!queue.isEmpty()){
            //元素个数
            int size=queue.size();
            while(!queue.isEmpty()){
                TreeNode node =queue.pollFirst();
                if(flag){  //true 下一行不是最后一行
                    if(node.left==null){
                        flag=false;
                        if(node.right!=null)return false;
                    }
                    else{
                        next.offerLast(node.left);
                    }

                    if(node.right==null){
                        flag=false;
                    }
                    else{
                        next.offerLast(node.right);
                    }

                }
                else{ //前面已有为空的子节点
                    if(node.left!=null||node.right!=null)return false;

                }

            }
            //判断本层元素个数
            if(size!=num){return false;}
            num*=2;
            if(!flag){
                //下一层(最后一层)不可以有子节点
                while(!next.isEmpty()){
                    TreeNode node =next.pollFirst();
                    if(node.left!=null||node.right!=null)return false;
                }
                return true;
            }
            queue=next;
            next=new LinkedList<>();

        }
        return true;


    }
    public List<Integer> findAnagrams(String s, String p) {

        List<Integer>list=new ArrayList<>();
        HashMap<Character,Integer>map=new HashMap<>();
        HashMap<Character,Integer>tmp;
        for(char c:s.toCharArray()){
            map.put(c,map.getOrDefault(c,0)+1);
        }
        tmp= (HashMap<Character, Integer>) map.clone();
        int si=0;
        while(si+p.length()<s.length()){

            if(tmp.isEmpty()){
                list.add(si-p.length());
                tmp= (HashMap<Character, Integer>) map.clone();
            }
            else{
                char c=s.charAt(si);
                if(tmp.containsKey(c)){
                    if(tmp.get(c)==1)
                        tmp.remove(c);
                    else map.put(c,map.get(c)-1);
                    si++;
                }
                else{
                    si++;
                    tmp= (HashMap<Character, Integer>) map.clone();
                }
            }
        }
        return list;

    }



}














