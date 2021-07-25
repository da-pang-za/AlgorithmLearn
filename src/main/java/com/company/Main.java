package com.company;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


public class Main {


    static Solution solution=new Solution();
    public static void main(String[] args) {



        //[[-2147483646,-2147483645],[2147483646,2147483647]]
       // solution.uniquePathsWithObstacles(new int[][]{{0,0,0},{0,1,0},{0,0,0}});
//        c++;




    }

    static int Factorial(int n) {
        if (n == 0) return 1;
        return n * Factorial(n - 1);
    }
/**/
    public int countOdds(int low, int high) {
        int len = high - low + 1;
        if (len % 2 == 0) return len / 2;
        return len / 2 + low % 2;

    }

    public double trimMean(int[] arr) {
        int len = arr.length;
        int k = len / 20;
        double r = 0;
        Arrays.sort(arr);
        for (int i = k; i < len - k; i++) {
            r += arr[i];

        }
        return r / (len - k - k);


    }

    public String[] reorderLogFiles(String[] logs) {
        Arrays.sort(logs, (log1, log2) -> {
            String[] split1 = log1.split(" ", 2);
            String[] split2 = log2.split(" ", 2);
            boolean isDigit1 = Character.isDigit(split1[1].charAt(0));
            boolean isDigit2 = Character.isDigit(split2[1].charAt(0));
            if (!isDigit1 && !isDigit2) {
                int cmp = split1[1].compareTo(split2[1]);
                if (cmp != 0) return cmp;
                return split1[0].compareTo(split2[0]);
            }
            return isDigit1 ? (isDigit2 ? 0 : 1) : -1;
        });
        return logs;
    }

    public int findKthPositive(int[] arr, int k) {
        int n = 0;
        int j = 0;
        for (int i = 0; i < k; i++) {
            n++;
            while (j < arr.length && arr[j] == n) {
                n++;
                j++;
            }


        }
        return n;

    }

    public int majorityElement(int[] nums) {
        //if(nums.length==1)return nums[0];
        int b = nums.length / 2;
        Arrays.sort(nums);

        return nums[b];
    }

    public int maxDepth(String s) {
        int n = 0;
        int max = 0;
        for (char c : s.toCharArray()
        ) {
            if (c == '(') {
                n++;
                if (n > max) max = n;
            } else if (c == ')') n--;
        }
        return max;

    }

    //数字到字母   字母到数字
    int[] letter2number = new int[26];
    //前导零
    int[] leadzero = new int[26];
    int[] number2letter = new int[10];

    public boolean isSolvable(String[] words, String result) {
        //最多出现10个不同字母  最高位不可能是0
        //最多有10!种情况  构造映射
        //递归构造
        int len = result.length();
        for (String s : words) {
            if (s.length() > len) return false;
            leadzero[s.charAt(0) - 'A'] = 1;
        }
        leadzero[result.charAt(0) - 'A'] = 1;


        return true;

    }

    TreeNode pre;
    TreeNode node;

    public TreeNode convertBiNode(TreeNode root) {

        pre = root;
        node = pre;
        convertBiNode1(root);
        return node.right;

    }

    TreeNode convertBiNode1(TreeNode root) {
        if (root == null) return null;
        //左中右遍历
        convertBiNode1(root.left);
        root.left = null;
        pre = root;
        //pre.right;
        convertBiNode1(root.right);
        return null;
    }

    public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {

        int[][] rc = new int[R * C][2];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                rc[i * C + j][0] = i - r0;
                rc[i * C + j][1] = j - c0;

            }

        }
        Comparator<int[]> cmp = new MyComparator();
        Arrays.sort(rc, cmp);
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                rc[i * C + j][0] += r0;
                rc[i * C + j][1] += c0;

            }

        }
        return rc;

    }

    class MyComparator implements Comparator<int[]> {

        @Override
        public int compare(int[] o1, int[] o2) {
            int a = Math.abs(o1[0]) + Math.abs(o1[1]);
            int b = Math.abs(o2[0]) + Math.abs(o2[1]);
            if (a < b)
                return -1;
            if (a > b) return 1;
            return 0;
        }
    }

    public int lastStoneWeight(int[] stones) {
        int[] weights = new int[1001];//每个重量的石头
        for (int i = 0; i < stones.length; i++) {
            weights[stones[i]]++;

        }
        for (int i = 1000; i > 0; i--) {
            if (weights[i] > 0) {
                if (weights[i] % 2 == 1) {
                    weights[i] = 1;
                    for (int j = i - 1; j > 0; j--) {
                        if (weights[j] != 0) {
                            weights[i - j]++;
                            weights[i] = 0;
                            weights[j]--;
                            break;
                        }
                    }
                } else weights[i] = 0;
            }

        }
        for (int i = 1; i < 1001; i++) {
            if (weights[i] > 0) return i;

        }
        return 0;

    }

    public int[] numMovesStones(int a, int b, int c) {
        int max, min;
        int x, y, z;
        x = Math.min(a, Math.min(b, c));
        z = Math.max(a, Math.max(b, c));
        y = a + b + c - x - z;
        max = y - x - 1 + z - y - 1;
        min = (y - x == 1 ? 0 : 1) + (z - y == 1 ? 0 : 1);
        if (y - x == 2 || z - y == 2) min = 1;
        return new int[]{min, max};

    }

    public String modifyString(String s) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == '?') {
                char c = 'z';
                if ((i - 1 >= 0 && sb.charAt(i - 1) == c) ||
                        (i + 1 < s.length() && s.charAt(i + 1) == c)) {
                    c--;
                    if ((i - 1 >= 0 && sb.charAt(i - 1) == c) ||
                            (i + 1 < s.length() && s.charAt(i + 1) == c))
                        c--;
                }
                sb.append(c);
            } else sb.append(s.charAt(i));
            i++;
        }
        return sb.toString();


    }

    public int removePalindromeSub(String s) {
        //本身是回文串  1次
        int i = 0;
        int j = s.length() - 1;
        while (i <= j) {
            if (s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
            } else return 2;
        }
        return 1;
        //否则 2次


    }

    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int[] ws = new int[words.length];
        int[] qs = new int[queries.length];
        for (int i = 0; i < words.length; i++) {
            ws[i] = minwordFre(words[i]);

        }
        for (int i = 0; i < queries.length; i++) {
            int q = minwordFre(queries[i]);
            for (int j = 0; j < ws.length; j++) {
                if (q < ws[j]) qs[i]++;

            }

        }


        return qs;

    }

    int minwordFre(String s) {
        int[] alphabeta = new int[26];
        for (char c : s.toCharArray()
        ) {
            alphabeta[c - 'a']++;

        }
        for (int i = 0; i < 26; i++) {
            if (alphabeta[i] != 0) return alphabeta[i];

        }
        return -1;
    }

    public int[] distributeCandies(int candies, int num_people) {
        int[] r = new int[num_people];
        int c = 1;
        int i = 0;
        while (candies > 0) {
            if (candies >= c) {
                r[i] += c;
                candies -= c;
                c++;
                i = (i + 1) % num_people;
            } else {
                r[i] += candies;
                candies = 0;
            }


        }
        return r;

    }

    public boolean isAlienSorted(String[] words, String order) {
        int[] alphabeta = new int[26];//映射
        for (int i = 0; i < order.length(); i++) {
            alphabeta[order.charAt(i) - 'a'] = i;

        }
        for (int i = 0; i + 1 < words.length; i++) {
            int flag = 0;//全等
            for (int j = 0; j < Math.min(words[i].length(), words[i + 1].length()); j++) {
                int a = alphabeta[words[i].charAt(j) - 'a'];
                int b = alphabeta[words[i + 1].charAt(j) - 'a'];
                if (a > b)
                    return false;
                else if (a < b) {
                    flag = 1;
                    break;
                } else //a==b
                {
                    //continue;
                }
            }
            //如果前面都一样
            if (flag == 0) {
                if (words[i].length() > words[i + 1].length()) return false;
            }

        }
        return true;

    }

    public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
        int sum = 0;

        for (int i = 0; i < A.length; i++) {
            if (A[i] % 2 == 0) sum += A[i];
        }
        int[] r = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][1];
            int val = queries[i][0];
            if (A[index] % 2 == 0) {
                if (val % 2 == 0) sum += val;
                else {
                    sum -= A[index];
                }
                A[index] += val;
            } else {
                if (val % 2 == 1) {
                    sum += A[index] + val;
                }
                A[index] += val;
            }
            r[i] = sum;

        }
        return r;


    }

    public int findJudge(int N, int[][] trust) {
        //每个人被多少个人信任
        int[] BT = new int[N];
        //每个人信任多少人
        int[] T = new int[N];
        for (int i = 0; i < trust.length; i++) {
            int a = trust[i][0];
            int b = trust[i][1];
            BT[b - 1]++;//信任b的人+1
            T[a - 1]++;//a信任的人+1

        }
        for (int i = 0; i < N; i++) {
            if (T[i] == 0)//不信任任何人
            {
                //被其他所有人信任
                if (BT[i] == N - 1) return i + 1;
            }

        }
        return -1;


    }

    public double myPow(double x, int n) {

        if (n >= 0)
            return helpPow(x, n);
        else return 1 / helpPow(x, n);
    }

    double helpPow(double x, int n) {
        if (n == 0) return 1;

        double hlf = helpPow(x, n / 2);
        if (n % 2 == 0)
            return hlf * hlf;
        else return hlf * hlf * x;

    }

    public int[][] transpose(int[][] A) {
        int x = A.length;
        int y = A[0].length;
        int[][] r = new int[y][x];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                r[j][i] = A[i][j];

            }
        }
        return r;

    }

    public String[] uncommonFromSentences(String A, String B) {
        Map<String, Integer> count = new HashMap();
        for (String word : A.split(" "))
            count.put(word, count.getOrDefault(word, 0) + 1);
        for (String word : B.split(" "))
            count.put(word, count.getOrDefault(word, 0) + 1);

        List<String> ans = new LinkedList();
        for (String word : count.keySet())
            if (count.get(word) == 1)
                ans.add(word);

        return ans.toArray(new String[0]);
    }

    public String countAndSay(int n) {

        String t = "1";
        for (int i = 1; i < n; i++) {
            t = countAndSay(t);

        }
        return t;

    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stack.isEmpty()) {
                if (c == '(' || c == '[' || c == '{')
                    stack.push(c);
                else return false;
            } else {
                if (c == '(' || c == '[' || c == '{')
                    stack.push(c);
                else {
                    char d = stack.pop();
                    if ((d == '(' && c == ')')
                            || (d == '[' && c == ']')
                            || (d == '{' && c == '}'))
                        ;
                    else return false;

                }
            }

        }

        return stack.isEmpty();

    }

    public int removeElement(int[] nums, int val) {
        int i = 0;//放置元素的位置
        int j = 0;//当前检查的元素的位置
        int count = 0;//移除的元素的个数
        while (j < nums.length) {
            if (val == nums[j])//删除
            {
                j++;
                count++;
            } else {
                nums[i] = nums[j];
                i++;
                j++;
            }
        }
        return nums.length - count;

    }

    public boolean canConstruct(String ransomNote, String magazine) {
        int[] note = new int[26];
        int[] mag = new int[26];
        for (int i = 0; i < ransomNote.length(); i++) {
            note[ransomNote.charAt(i) - 'a']++;
        }
        for (int i = 0; i < magazine.length(); i++) {
            mag[magazine.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (note[i] > mag[i]) return false;

        }
        return true;
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {

        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j < k + 1 && i + j < nums.length; j++) {
                if (nums[i] == nums[i + j]) return true;

            }

        }
        return false;


    }

    public int dominantIndex(int[] nums) {
        int[] n = nums.clone();
        Arrays.sort(nums);
        int len = nums.length;
        if (nums[len - 1] >= 2 * nums[len - 2]) {
            for (int i = 0; i < n.length; i++) {
                if (n[i] == nums[len - 1]) return i;

            }
        }
        return -1;

    }

    //    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
//
//
//    }
    public int maxScore(String s) {
        int score = '1' - s.charAt(0);//左侧的0
        for (int i = 1; i < s.length(); i++) {
            //右侧的1
            score += s.charAt(i) - '0';

        }
        int max = score;
        for (int i = 1; i < s.length() - 1; i++) {//分隔符右滑
            //右侧的1
            score += (s.charAt(i) == '1' ? -1 : 1);
            if (score > max) max = score;

        }
        return max;


    }

    public static char slowestKey(int[] releaseTimes, String keysPressed) {
        int[] t = new int[26];
        for (int i = releaseTimes.length - 1; i > 0; i--) {
            releaseTimes[i] -= releaseTimes[i - 1];

        }
        for (int i = 0; i < keysPressed.length(); i++) {
            t[keysPressed.charAt(i) - 'a'] += releaseTimes[i];

        }
        int max = 0;
        int r = -1;
        for (int i = 0; i < 26; i++) {
            if (t[i] >= max) {
                max = t[i];
                r = i;
            }

        }
        return (char) ('a' + r);
    }

    public boolean canFormArray(int[] arr, int[][] pieces) {
        int len = arr.length;
        int row = pieces.length;
        int num = 0;
        for (int i = 0; i < row; i++) {
            num += pieces[i].length;

        }
        if (len != num) return false;
        Arrays.sort(arr);
        Comparator<int[]> comparator = new HeadCmp();
        Arrays.sort(pieces, comparator);
        int i = 0;
        int j = 0;
        num = 0;
        while (num + j < len) {
            for (; j < pieces[i].length && num + j < len; j++, num++) {
                if (arr[num] != pieces[i][j]) return false;
            }
            i++;

        }
        return true;

    }

    class HeadCmp implements Comparator<int[]> {

        @Override
        public int compare(int[] o1, int[] o2) {
            int a = o1[0];
            int b = o2[0];
            if (a < b)
                return -1;
            if (a > b) return 1;
            return 0;
        }
    }

    public void nextPermutation(int[] nums) {
        //第一步  高位只能变大
        int len = nums.length;
        if (len == 0 || len == 1) return;

        int flag = 0;//没找到可交换的
        int index = -1, index1 = Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;
        for (int j = len - 1; j >= 0; j--) {
            int low = nums[j];//低位
            for (int i = j - 1; i >= 0; i--) {
                if (i > index) {
                    if (nums[i] < low) {
                        //找到
                        flag = 1;
                        //交换这两个   标记位置    交换的位置index应该尽可能低
                        index = i;
                        index1 = j;

                    }
                } else break;
            }

        }
        if (flag == 0) Arrays.sort(nums);
        else {
            int low = nums[index];
            nums[index] = nums[index1];
            nums[index1] = low;
            //对i+1到最后排序
            Arrays.sort(nums, index + 1, len);

        }


    }

    public int minMoves(int[] nums) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < min) min = nums[i];
        }
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i] - min;
        }
        return sum;

    }

    public int numUniqueEmails(String[] emails) {
        HashSet<String> strings = new HashSet<>();
        //分割为两部分
        //
        String first;
        String second;
        int num = 0;
        for (String s : emails
        ) {
            //以@作为分隔符
            int t = -1;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '@') {
                    t = i;
                    break;
                }
            }
            first = s.substring(0, t);
            second = s.substring(t + 1);
            StringBuilder sb = new StringBuilder();
            for (char c : first.toCharArray()) {
                if (c == '.')
                    ;
                else if (c == '+')
                    break;
                else sb.append(c);
            }
            sb.append('@').append(second);
            String string = sb.toString();
            if (!strings.contains(string)) {
                num++;
                strings.add(string);
            }


        }
        return num;

    }


    class BitComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            int c1 = countOne(o1);
            int c2 = countOne(o2);
            if (c1 < c2) return -1;
            if (c1 > c2) return 1;
            return 0;


        }
    }

    int countOne(int n) {
        int mask = 1;
        int count = 0;
        while (n > 0) {
            count += n & mask;
            n >>= 1;

        }
        return count;
    }


    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])) return true;
            else set.add(nums[i]);

        }
        return false;

    }

    public String reverseVowels(String s) {
        char[] ss = s.toCharArray();
        List<Character> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < ss.length; i++) {
            if (ss[i] == 'a' || ss[i] == 'A' ||
                    ss[i] == 'e' || ss[i] == 'E' ||
                    ss[i] == 'i' || ss[i] == 'I' ||
                    ss[i] == 'o' || ss[i] == 'O' ||
                    ss[i] == 'u' || ss[i] == 'U') {
                list.add(ss[i]);
                list1.add(i);
            }

        }
        int len = list1.size();
        for (int i = 0; i < len; i++) {
            ss[list1.get(i)] = list.get(len - 1 - i);

        }
        return new String(ss);


    }

    public int[] intersect(int[] nums1, int[] nums2) {
        List<Integer> l = new ArrayList<>();
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0, j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) i++;
            else if (nums1[i] > nums2[j]) j++;
            else {
                l.add(nums1[i]);
                i++;
                j++;
            }
        }
        int[] r = new int[l.size()];
        for (int k = 0; k < l.size(); k++) {
            r[k] = l.get(k);

        }
        return r;

    }

    public boolean isPowerOfThree(int n) {
        int k = (int) Math.pow(3, (int) (Math.log(Integer.MAX_VALUE) / Math.log(3)));
        return n > 0 && k % n == 0;


    }

    public int getSum(int a, int b) {
        //位运算
        //1.按位异或   无进位   保留进位信息
        //if(b==0)return a;
        while (b != 0) {
            //无进位加
            int t = a ^ b;
            //进位信息
            b = a & b << 1;
            a = t;
        }
        return a;

    }

    public boolean isUgly(int num) {
        //若干个 2  3  5的乘积
        if (num == 1) return true;
        if (num < 1) return false;
        //0 false
        while (num > 1) {
            if (num % 2 == 0) num /= 2;
            else if (num % 3 == 0) {
                num /= 3;
            } else if (num % 5 == 0) {
                num /= 5;
            } else return false;


        }
        return true;

    }

    public List<String> summaryRanges(int[] nums) {
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int l = 0;
        int r = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                r = i;
            } else {
                if (l != r)
                    list.add(sb.append(nums[l]).append("->").append(nums[r]).toString());
                else list.add(sb.append(nums[l]).toString());
                sb = new StringBuilder();
                l = r + 1;
                r = l;

            }

        }
        if (l != r)
            list.add(sb.append(nums[l]).append("->").append(nums[r]).toString());
        else list.add(sb.append(nums[l]).toString());
        return list;


    }

    public ListNode removeElements(ListNode head, int val) {

        ListNode node = head;
        ListNode pre1 = new ListNode(0);
        ListNode pre = pre1;
        pre.next = node;
        while (node != null) {
            if (node.val == val) {
                pre.next = node.next;
            } else {
                pre = pre.next;
            }
            node = node.next;
        }
        return pre1.next;

    }

    public boolean isMatch(String s, String p) {
        //四种情况  . x  x*  .*
        //遇到.*  匹配后一个匹配不上继续后一个...
        //正则表达式词法分析 *和前面一个字符组成一个 符号

        //.  前移指针  x  判断、前移指针
        //.* 看下一个pattern 如果匹配不上前移指针
        // 匹配上也可能不对  需要递归回溯 动态规划
        //两个都移到末尾 匹配成功
        //先把pattern中一样x*的合并
        int is = 0;
        int ip = 0;
        //
        while (ip == p.length() || is == s.length()) ;
        return true;

    }

    public boolean isAnagram(String s, String t) {
        HashMap<Character, Integer> maps = new HashMap<>();

        //t需要包含s中的所有字符  并且不能有多余字符    每种字符都和s相等
        for (char c : s.toCharArray()
        ) {
            if (maps.containsKey(c))
                maps.put(c, maps.get(c) + 1);
            else
                maps.put(c, 1);

        }
        for (char c : t.toCharArray()
        ) {
            if (maps.containsKey(c))
                maps.put(c, maps.get(c) - 1);
            else
                maps.put(c, -1);

        }
        for (Map.Entry entry : maps.entrySet()) {
            if ((int) entry.getValue() != 0)
                return false;
        }

        return true;

    }

    public int[] twoSum(int[] numbers, int target) {
        int i = 0;
        int j = numbers.length - 1;
        int flag = 0;
        while (i < j) {
            if (target == numbers[i] + numbers[j]) {
                flag = 1;
                break;
            }
            if (target < numbers[i] + numbers[j]) {
                j--;
            } else i++;
        }
        if (flag == 1)
            return new int[]{i + 1, j + 1};
        else return new int[]{-1, -1};

    }

    public int searchInsert(int[] nums, int target) {
        int i = 0;
        while (target > nums[i])
            i++;
        return i;

    }

    public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode node = head;
        ListNode node1 = l1, node2 = l2;
        while (node1 != null && node2 != null) {
            int v1 = node1.val, v2 = node2.val;
            if (v1 < v2) {
                node1 = node1.next;
                node.next = new ListNode(v1);
                node = node.next;
            } else {
                node2 = node2.next;
                node.next = new ListNode(v2);
                node = node.next;
            }
        }
        if (node1 != null) {
            node.next = node1;
        } else if (node2 != null) node.next = node2;
        return head.next;

    }

    public int[] fraction(int[] cont) {
        int[] c = new int[]{1, cont[cont.length - 1]};//分子 分母
        for (int i = cont.length - 2; i >= 0; i--) {
            int t = c[1];//分母
            c[1] = c[0] + cont[i] * t;//分子放到分母上
            c[0] = t;

        }
        //分子分母互换//除以最大公约数
        int g = gcd(c[0], c[1]);
        c[0] /= g;
        c[1] /= g;
        return new int[]{c[1], c[0]};

    }

    //最大公约数
    public int gcd(int m, int n) {
        int i, factor = 1;   //factor是求最大公约数
        for (i = 2; i * i <= m && i * i <= n; i++) {
            while (m % i == 0 && n % i == 0) {
                factor = factor * i;
                m = m / i;
                n = n / i;
            }
        }
        return factor;
    }

    public String countAndSay(String s) {

        char pre = s.charAt(0);
        int count = 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == pre) count++;
            else {

                sb.append(count).append(pre);
                count = 1;
                pre = c;
            }
        }

        sb.append(count).append(pre);

        return sb.toString();

    }

    public int romanToInt(String s) {
        //1999 M CM XC IX
        // IV4 IX9  XL40 XC90 CD400  CM900
        int sum = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (i + 1 < s.length()) {
                if (c == 'I') {
                    i++;
                    char h = s.charAt(i);
                    if (h == 'V' || h == 'X') {
                        sum += map.get(h) - map.get(c);
                    } else {
                        sum += map.get(c);
                        i--;
                    }
                } else if (c == 'X') {

                    i++;
                    char h = s.charAt(i);
                    if (h == 'L' || h == 'C') {
                        sum += map.get(h) - map.get(c);
                    } else {
                        sum += map.get(c);
                        i--;
                    }
                } else if (c == 'C') {
                    i++;
                    char h = s.charAt(i);
                    if (h == 'D' || h == 'M') {
                        sum += map.get(h) - map.get(c);
                    } else {
                        sum += map.get(c);
                        i--;
                    }
                } else {
                    sum += map.get(c);
                }
            } else {
                sum += map.get(c);
            }
            i++;

        }
        return sum;


    }

    public static String reorderSpaces(String text) {
        int sum = 0;//空格数
        List<String> list = new ArrayList<>();//分词
        while (text.charAt(sum) == ' ') {
            sum++;
        }
        list.addAll(Arrays.asList(text.substring(sum).split(" ")));
        sum = 0;
        for (char c : text.toCharArray()) {
            if (c == ' ') sum++;
        }
        int n = sum / (list.size() - 1);
        int y = sum % (list.size() - 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            for (int j = 0; j < n; j++) {
                sb.append(' ');

            }

        }
        for (int i = 0; i < y; i++) {
            sb.append(' ');
        }
        return sb.toString();


    }

    public int projectionArea(int[][] grid) {
        //顶部
        int sum = 0;
        for (int i = 0; i < grid.length; i++) {
            //侧面看   加每行最大值
            int max = 0;
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 0) sum++;
                if (grid[i][j] > max) max = grid[i][j];

            }
            sum += max;
        }
        //正面 每列最大值
        for (int i = 0; i < grid[0].length; i++) {
            int max = 0;
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i] > max) max = grid[j][i];

            }
            sum += max;

        }
        return sum;

    }

    public int largestPerimeter(int[] A) {
        int len = 0;
        int min = -1;//最短边
        int[] side = new int[3];
        for (int i = 0; i + 2 < A.length; i++) {
            side[0] = A[i];
            side[1] = A[i + 1];
            side[2] = A[i + 2];
            if (side[0] + side[1] > side[2]
                    && side[1] + side[2] > side[0] &&
                    side[0] + side[2] > side[1]) {
                len = side[0] + side[1] + side[2];
                //min=Math.min()
            }

        }

        return len;

    }

    public TreeNode sortedArrayToBST(int[] nums) {
        int begin = 0;
        int end = nums.length - 1;

        return sortedArrayToBST(nums, begin, end);

    }

    TreeNode sortedArrayToBST(int[] nums, int begin, int end) {
        if (begin > end) return null;
        TreeNode root = new TreeNode(nums[(begin + end) / 2]);
        root.left = sortedArrayToBST(nums, begin, (begin + end) / 2 - 1);
        root.right = sortedArrayToBST(nums, (begin + end) / 2 + 1, end);
        return root;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null && q != null) return false;
        if (p != null && q == null) return false;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);

    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        //层次遍历二叉树
        //todo    //广度优先
        return null;

    }

    int kkk = -1;
    int rrr = -1;

    public int kthLargest(TreeNode root, int k) {
        if (root == null) return -1;
        //直接放到list中

        //进行右中左遍历
        kkk = k;
        lcr(root);
        return rrr;


    }

    void lcr(TreeNode root) {
        if (root == null) return;

        lcr(root.right);
        if (kkk == 1)//找到结果
        {
            if (rrr == -1)
                rrr = root.val;
            return;
        } else kkk--;
        lcr(root.left);
    }

    public boolean hasPathSum(TreeNode root, int sum) {
        //到叶子节点的路径
        if (root == null) return false;

        if (root.val == sum && root.left == null && root.right == null) return true;
        return hasPathSum(root.right, sum - root.val) ||
                hasPathSum(root.left, sum - root.val);
    }

    public String[] findOcurrences(String text, String first, String second) {
        List<String> result = new ArrayList<>();
        //切词
        List<String> list = new ArrayList<>();
        int i = 0, j = 1;
        while (j < text.length()) {
            if (text.charAt(j) == ' ') {
                list.add(text.substring(i, j));
                i = j + 1;
            }
            j++;

        }
        j--;
        if (text.charAt(j) != ' ')
            list.add(text.substring(i, j + 1));
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index).equals(first)) {
                if (index + 1 < list.size() && list.get(index + 1).equals(second)) {
                    if (index + 2 < list.size()) {
                        result.add(list.get(index + 2));
                    }
                    index++;

                }
            }
        }
        return result.toArray(new String[result.size()]);

    }

    public boolean isPalindrome(int x) {
        String s = String.valueOf(x);
        int begin = 0;
        int end = s.length() - 1;
        while (begin <= end) {
            if (s.charAt(begin) != s.charAt(end)) return false;
            begin++;
            end--;
        }
        return true;

    }

    void DFSisSolvable(String[] words, String result, int bit)//需要当前位数
    {


    }

    public String[] spellchecker(String[] wordlist, String[] queries) {
        HashMap<String, String> map = new HashMap<>(); //小写 原
        Set<String> set = new HashSet<>();//原
        HashMap<String, String> mapv = new HashMap<>(); //元音忽略 原
        for (String word : wordlist) {


            map.putIfAbsent(word.toLowerCase(), word);

            set.add(word);
            mapv.putIfAbsent(changeV(word.toLowerCase()), word);
        }

        for (int i = 0; i < queries.length; i++) {
            //
            String word = queries[i];
            String low = word.toLowerCase();
            if (set.contains(word)) {
                //不变
            } else //
                if (map.containsKey(low)) {
                    queries[i] = map.get(low);
                } else queries[i] = mapv.getOrDefault(changeV(low), "");
        }
        return queries;

    }

    String changeV(String word) {//小写
        //替换元音
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            sb.append(isV(c) ? '*' : c);
        }
        return sb.toString();
    }

    boolean isV(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    public int[] plusOne(int[] digits) {
        //最后一位不是9
        if (digits[digits.length - 1] == 9) {
            //全是9
            //不全是9
            int carry = 1;
            digits[digits.length - 1] = 0;
            for (int i = digits.length - 2; i >= 0; i--) {
                digits[i] = digits[i] + carry;
                if (digits[i] == 10) {
                    digits[i] = 0;
                    carry = 1;
                } else {
                    carry = 0;
                }

            }
            if (digits[0] == 0) {
                int[] r = new int[digits.length + 1];
                r[0] = 1;
                return r;
            } else {
                return digits;
            }
        } else {
            digits[digits.length - 1]++;
            return digits;
        }

    }

    public static List<Integer> getRow(int rowIndex) {
        List<Integer> result = new ArrayList<>();
        //组合数C(k,0) C(k,k)
        for (int i = 0; i <= rowIndex; i++) {
            result.add(Factorial(rowIndex) / Factorial(i) / Factorial(rowIndex - i));
        }
        return result;

    }

    public List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> list = new ArrayList<>();
        int l = 0;
        int r = 0;//长度l-r+1
        char crr = ' ';
        for (int i = 0; i < s.length(); i++) {
            if (crr != s.charAt(i)) {
                if (r - l + 1 >= 3) {
                    List<Integer> t = new ArrayList<>();
                    t.add(l);
                    t.add(r);
                    list.add(t);
                }
                crr = s.charAt(i);
                l = r = i;
            } else {
                r++;

            }
        }
        if (r - l + 1 >= 3) {
            List<Integer> t = new ArrayList<>();
            t.add(l);
            t.add(r);
            list.add(t);
        }
        return list;

    }

    public int[] shortestToChar(String S, char C) {
        int l, r, len;
        int[] result = new int[S.length()];
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            result[i] = c == C ? 0 : 1;

        }
        for (int i = 0; i < S.length(); i++) {
            //


            if (result[i] != 0) {
                len = 1;
                l = i - 1;
                r = i + 1;
                while (l >= 0 && result[l] != 0) {
                    len++;
                    l--;
                }
                if (l >= 0)
                    result[i] = len;
                len = 1;
                while (r < S.length() && result[r] != 0) {
                    len++;
                    r++;
                }
                if (r < S.length()) {
                    if (l >= 0)
                        result[i] = Math.min(len, result[i]);
                    else result[i] = len;
                }

            }
        }
        return result;

    }

    public ListNode middleNode(ListNode head) {
        int len = 0;

        ListNode node = head;
        while (node != null) {
            len++;
            node = node.next;
        }
        node = new ListNode(0);
        node.next = head;
        len /= 2;
        while (len + 1 > 0) {
            node = node.next;
            len--;
        }
        return node;

    }

    public List<Integer> mostVisited(int n, int[] rounds) {
        int[] statistics = new int[rounds.length];
        int i = rounds[0];
        for (int j = 0; j + 1 < rounds.length; j++) {
            while (true) {
                statistics[i - 1]++;
                if (i == rounds[j + 1]) {
                    i = (i) % n + 1;
                    break;
                }
                i = (i) % n + 1;
            }
            //


        }
        int max = Integer.MIN_VALUE;
        List<Integer> l = new ArrayList<>();
        for (int k = 0; k < n; k++) {
            if (statistics[k] > max) max = statistics[k];
        }
        for (int k = 0; k < n; k++) {
            if (statistics[k] == max) l.add(k + 1);
        }
        return l;
    }

    public void moveZeroes(int[] nums) {
        for (int i = 0, j = 0; j < nums.length; j++) {
            if (nums[j] != 0) {
                int k = nums[i];
                nums[i] = nums[j];
                nums[j] = k;
                i++;

            }
        }
    }

    public String makeGood(String s) {
        int i = 0;
        int j = -1;
        //Stack<Character> stack=new Stack<>();
        StringBuilder sb = new StringBuilder();
        while (i < s.length()) {
            if (sb.length() == 0) {
                sb.append(s.charAt(i));

                j++;
            } else {
                char c = s.charAt(i);
                if (Character.toLowerCase(sb.charAt(j)) ==
                        Character.toLowerCase(c)
                        && sb.charAt(j) != c) {
                    sb.deleteCharAt(j);

                    j--;
                } else {
                    sb.append(s.charAt(i));

                    j++;
                }


            }
            i++;

        }
        return sb.toString();

    }

    public int missingNumber(int[] nums) {
        int n = nums.length;
        int sum = n * (n + 1) / 2;
        for (int i : nums) {
            sum -= i;
        }
        return sum;

    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        //从后往前比较
        int i = m - 1, j = n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[i + j + 1] = nums1[i];
                i--;
            } else {
                nums1[i + j + 1] = nums2[j];
                j--;
            }
        }
        while (j >= 0) {
            nums1[j] = nums2[j];
            j--;
        }


    }

    public int minDepth(TreeNode root) {
        //
        if (root == null) return 0;
        if (root.left == null) return minDepth(root.right) + 1;
        if (root.right == null) return minDepth(root.left) + 1;
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;

    }

    //int[]KthL;
    int Kth;
    int pos;
    int[] statistics = new int[20001];

    public void KthLargest(int k, int[] nums) {
        //KthL=new int[nums.length];
        //Arrays.sort(KthL);
        for (int i = 0; i < nums.length; i++) {
            statistics[nums[i] + 10000]++;

        }
        Kth = k;
        pos = statistics.length - 1;
    }

    public int add(int val) {
        statistics[val + 10000]++;
        //int i=Kth;
        if (val + 10000 > pos) Kth--;
        if (Kth == 0) {
            while (statistics[++pos] == 0) ;
            Kth = statistics[pos];
            return pos - 10000;
        }
        for (int k = pos; k >= 0; k--) {
            if (Kth > statistics[k]) {
                Kth -= statistics[k];
            } else {
                pos = k;
                return k - 10000;
            }
        }
        return 0;


    }

    public boolean findTarget(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        inorder(root, list);
        int i = 0;
        int j = list.size() - 1;
        while (i < j) {
            if (list.get(i) + list.get(j) < k) i++;
            else if (list.get(i) + list.get(j) > k) j--;
            else return true;
        }
        return false;

    }

    void inorder(TreeNode root, List<Integer> list)//左中右
    {
        if (root == null) return;
        inorder(root.left, list);
        list.add(root.val);
        inorder(root.right, list);

    }

    public String toGoatLatin(String S) {
        List<String> list = new ArrayList<String>();//切词
        int j = 0;
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == ' ') {
                list.add(S.substring(j, i));
                j = i + 1;
            }
        }
        list.add(S.substring(j));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(' ');
            char f = list.get(i).charAt(0);
            if (f == 'a' || f == 'e' || f == 'i' || f == 'o' || f == 'u'
                    || f == 'A' || f == 'E' || f == 'I' || f == 'O' || f == 'U') {
                sb.append(list.get(i)).append("ma");
            } else {
                sb.append(list.get(i).substring(1)).append(f).append("ma");
            }
            for (int jj = 0; jj < i + 1; jj++)
                sb.append('a');
        }
        return sb.toString().substring(1);

    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        //复用数组
        for (int i = 0; i < nums.length; i++) {
            int k = (nums[i] > 0) ? (nums[i]) : -nums[i];
            if (nums[k - 1] > 0) nums[k - 1] *= -1;

        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) list.add(i + 1);
        }
        return list;

    }

    public int[] constructRectangle(int area) {
        //因式分解
        int W = (int) Math.sqrt(area);
        if (W * W == area) return new int[]{W, W};
        while (true) { //

            for (int i = W - 1; ; i--) {
                if (area % i == 0) return new int[]{area / i, i};
            }

        }
    }

    // Definition for Employee.
    class Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;
    }

    ;

    public int getImportance(List<Employee> employees, int id) {

        return countImportance(id, employees);

    }

    int countImportance(int id, List<Employee> employees) {
        Employee employee = null;
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).id == id) employee = employees.get(i);
        }
        assert employee != null;
        int sum = employee.importance;
        for (int i = 0; i < employee.subordinates.size(); i++) {
            sum += countImportance(employee.subordinates.get(i), employees);
        }
        return sum;

    }

    public double largestTriangleArea(int[][] points) {
        double max = 0;
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    double a, b, c, p;
                    a = Math.sqrt((points[i][0] - points[j][0]) * (points[i][0] - points[j][0])
                            + (points[i][1] - points[j][1]) * (points[i][1] - points[j][1]));
                    b = Math.sqrt((points[k][0] - points[j][0]) * (points[k][0] - points[j][0])
                            + (points[k][1] - points[j][1]) * (points[k][1] - points[j][1]));
                    c = Math.sqrt((points[i][0] - points[k][0]) * (points[i][0] - points[k][0])
                            + (points[i][1] - points[k][1]) * (points[i][1] - points[k][1]));
                    p = (a + b + c) / 2;
                    double area = Math.sqrt(p * (p - a) * (p - c) * (p - b));
                    if (area > max) max = area;
                }

            }
        return max;

    }

    public int pivotIndex(int[] nums) {
        if (nums.length < 2) return -1;
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        int j = 0;
        int left = 0;
        for (; j < nums.length; j++) {
            if (sum == 2 * left + nums[j]) return j;
            left += nums[j];
        }
        return -1;

    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public String replaceSpace(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') sb.append("%20");
            else sb.append(s.charAt(i));
        }
        return sb.toString();

    }

    public String reverseOnlyLetters(String S) {
        Set<Integer> set = new HashSet<>();//非字母的索引
        //Stack<Character>s = new Stack<>();
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if ((c <= 'Z' && c >= 'A') || (c <= 'z' && c >= 'a')) {

            } else {
                set.add(i);
            }


        }
        int k = S.length() - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < S.length(); i++) {
            if (set.contains(i)) {
                sb.append(S.charAt(i));
            } else {
                char c = S.charAt(k);
                while (!(c <= 'Z' && c >= 'A') && !(c <= 'z' && c >= 'a'))//不是字母
                {
                    k--;
                    c = S.charAt(k);
                }
                sb.append(c);
                k--;
            }

        }
        return sb.toString();
    }

    public int hammingWeight(int n) {
        int mask = 1;
        int num = 0;
        while (mask < n) {
            num += (n & mask) > 0 ? 1 : 0;
            //mask+=mask;
            mask <<= 1;
        }
        return num;

    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode node = new ListNode(0);
        ListNode head = node;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                node.next = new ListNode(l1.val);
                l1 = l1.next;

            } else {
                node.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            node = node.next;
        }
        if (l1 != null) {
            while (l1 != null) {
                node.next = new ListNode(l1.val);
                l1 = l1.next;
                node = node.next;
            }
        } else if (l2 != null) {
            while (l2 != null) {
                node.next = new ListNode(l2.val);
                l2 = l2.next;
                node = node.next;
            }
        }
        return head.next;
    }

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        //二维二分查找
        //matrix[i][j] <=target<=matrix[i+1][j+1]
        if (matrix.length == 0 || matrix[0].length == 0) return false;
        int i = 0, j = matrix[0].length - 1;
        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] == target) return true;
            else if (matrix[i][j] < target) i++;
            else j--;
        }
        return false;
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) return true;
        return isMirror(root.left, root.right);
    }

    boolean isMirror(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null && root2 != null) return false;
        if (root1 != null && root2 == null) return false;
        if (root1.val != root2.val) return false;
        return isMirror(root1.left, root2.right) && isMirror(root1.right, root2.left);
    }

    public int removeDuplicates(int[] nums) {
        if (nums.length <= 1) return nums.length;//不用修改

        int index = 0;
        int t = 1;
        while (t < nums.length) {
            if (nums[t] != nums[index]) {
                index++;
                nums[index] = nums[t];


            }
            t++;
        }
        return index + 1;

    }

    public int findSecondMinimumValue(TreeNode root) {
        //如果两个子节点不相等  对小的调用此函数  得到的结果和大的比 选择较小的
        //如果相等则对子节点调用此函数
        if (root == null || root.left == null) return -1;
        int l, r;
        if (root.right.val == root.left.val) {
            l = findSecondMinimumValue(root.left);
            r = findSecondMinimumValue(root.right);
        } else {
            if (root.right.val == root.val) {
                r = findSecondMinimumValue(root.right);
                l = root.left.val;
            } else {
                l = findSecondMinimumValue(root.left);
                r = root.right.val;
            }

        }

        if (l == -1 && r == -1) return -1;
        else if (l == -1 && r != -1) return r;
        else if (l != -1 && r == -1) return l;
        return Math.min(l, r);


    }

    public boolean isToeplitzMatrix(int[][] matrix) {

        int[] len = new int[]{matrix.length, matrix[0].length};
        if (len[0] == 1 || len[1] == 1) return true;//对角线只有一个元素
        for (int i = 0; i < len[0] - 1; i++) {
            int k = matrix[i][0];
            for (int j = 1; j < len[1] && i + j < len[0]; j++) {
                if (matrix[i + j][j] != k) return false;
            }
        }
        for (int i = 1; i < len[1] - 1; i++) {
            int k = matrix[0][i];
            for (int j = 1; j < len[0] && i + j < len[1]; j++) {
                if (matrix[j][i + j] != k) return false;

            }

        }
        return true;
    }

    Stack<Integer> stack = new Stack<Integer>();
    Stack<Integer> stack1 = new Stack<Integer>();

    void appendTail(int value) {
        stack.push(value);
    }

    public int findRepeatNumber(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] m = new int[1000000];
        for (int i : nums) {
            m[i]++;
        }
        for (int i = 1; i < m.length; i++) {
            if (m[i] > 1) return i;
        }
        return -1;
    }

    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) return null;
        TreeNode t = root.left;
        root.left = root.right;
        root.right = t;
        mirrorTree(root.left);
        mirrorTree(root.right);
        return root;

    }

    public int minArray(int[] numbers) {
        for (int i = 0; i + 1 < numbers.length; i++) {
            if (numbers[i + 1] < numbers[i]) return numbers[i + 1];

        }


        return numbers[0];

    }

    public int numWays(int n) {
        if (n == 0 || n == 1) return 1;
        if (n == 2) return 2;
        int a = 1, b = 2, c = 3;
        for (int i = 3; i <= n; i++) {
            c = (a + b) % (int) (1e9 + 7);
            a = b;
            b = c;
        }
        return c;

    }

    int deleteHead() {
        if (stack.isEmpty()) return -1;
        while (!stack.isEmpty()) {
            stack1.push(stack.pop());
        }

        while (!stack1.isEmpty()) {
            stack.push(stack1.pop());
        }
        return 0;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root.val == p.val || root.val == q.val) return root;
        TreeNode l = lowestCommonAncestor(root, p, q);
        TreeNode r = lowestCommonAncestor(root, p, q);
        if (l != null && r != null) return root;
        return l != null ? l : r;
    }

    boolean lowestCommonAncestorOne(TreeNode root, int val)//当前节点下有val
    {
        if (root == null) return false;
        if (root.val == val) return true;
        return lowestCommonAncestorOne(root.left, val) ||
                lowestCommonAncestorOne(root.right, val);
    }

    int sum = 0;

    public int sumRootToLeaf(TreeNode root) {
        sumLeave(root, 0);
        return sum;

    }

    void sumLeave(TreeNode root, int v) {
        if (root == null) return;

        int leave = (v * 2 + root.val) % (int) (1e9 + 7);
        sumLeave(root.left, leave);
        sumLeave(root.right, leave);
        if (root.left == null && root.right == null)
            sum = (leave + sum) % (int) (1e9 + 7);


    }

    public static char nextGreatestLetter(char[] letters, char target) {
        if (target == 'z' && letters.length == 2 && letters[0] == 'a' && letters[1] == 'b')
            return 'a';
        //二分查找
        int i = letters.length / 2;
        int begin = 0;
        int end = letters.length - 1;
        while (begin <= end) {
            if (target >= letters[i]) {
                begin = i + 1;
                i = (begin + end) / 2;
            } else {
                if (target < letters[i] && (i == 0 || target >= letters[i - 1]))
                    return letters[i];
                else {
                    end = i;
                    i = (begin + end) / 2;
                }
            }
        }
        return ' ';

    }

    public int[] numberOfLines(int[] widths, String S) {
        int len = 0;
        int line = 1;
        for (int i = 0; i < S.length(); i++) {

        }
        for (char c : S.toCharArray()) {
            len += widths[c - 'a'];
            if (len > 100) {
                line++;
                len = widths[c - 'a'];
            }
        }
        return new int[]{line, len};


    }

    public int findLengthOfLCIS(int[] nums) {
        if (nums.length == 0) return 0;
        int max = 1;
        int len = 1;
        for (int i = 0; i + 1 < nums.length; i++) {
            if (nums[i] < nums[i + 1]) {
                len++;
                if (len > max) max = len;
            } else {
                len = 1;
            }
        }
        return max;

    }

    public String longestWord(String[] words) {
        if (words.length == 0) return "";
        //每个长度需要一个hashset
        List<String> r = new ArrayList<>();
        List<HashSet<String>> l = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            l.add(new HashSet<>());
        }
        for (String word : words) {
            l.get(word.length() - 1).add(word);
        }
        for (int i = 29; i >= 0; i--) {
            //不为空   结果可能在这个长度中
            for (String s : l.get(i)) {
                int flag = 0;
                for (int j = 0; j < s.length() - 1; j++) {
                    if (!l.get(j).contains(s.substring(0, j + 1))) {
                        flag = 1;
                        break;
                    }

                }
                if (flag == 0) r.add(s);
            }
        }
        String result = r.get(0);
        for (int i = 1; i < r.size(); i++) {
            int j = 0;
            while (j < result.length()) {
                if (r.get(i).charAt(j) == result.charAt(j)) j++;
                else if (r.get(i).charAt(j) < result.charAt(j)) {
                    result = r.get(i);
                    break;
                } else break;
            }
        }
        return result;

    }

    public String tree2str(TreeNode t) {
        if (t == null) return "()";
        String s = tree2str1(t);
        return s.substring(1, s.length() - 1);


    }

    public String tree2str1(TreeNode t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(t.val);
        if (t.left == null && t.right != null) sb.append("()");
        sb.append(tree2str1(t.left));
        sb.append(tree2str1(t.right));
        sb.append(')');
        return sb.toString();

    }

    public boolean isMonotonic(int[] A) {
        if (A.length <= 2) return true;
        int i = 0, j = 1;
        while (i < A.length && j < A.length) {
            if (A[i] == A[j]) {
                i++;
                j++;
            } else break;
        }

        if (A[i] < A[j]) {
            i++;
            j++;
            while (i < A.length && j < A.length) {
                if (A[i] > A[j]) return false;
                i++;
                j++;
            }
        } else {
            i++;
            j++;
            while (i < A.length && j < A.length) {
                if (A[i] < A[j]) return false;
                i++;
                j++;
            }
        }
        return true;

    }

    public int[] fairCandySwap(int[] A, int[] B) {
        int a = 0, b = 0;
        for (int i = 0; i < A.length; i++) {
            a += A[i];
        }
        for (int i = 0; i < B.length; i++) {
            b += B[i];
        }
        int t = (b - a) / 2;
        //找到B-A差值等于t
        for (int i : A)
            for (int j : B) {
                if (j - i == t) return new int[]{i, j};
            }
        return null;

    }

    public boolean lemonadeChange(int[] bills) {
        int five = 0;//手里的钱   找钱有5 15两种  20的钞票无法找钱
        int ten = 0;
        for (int i = 0; i < bills.length; i++) {
            if (bills[i] - 5 == 15)//20
            {
                //3*5   10+5
                if (ten > 0) {
                    if (five == 0) return false;
                    else {
                        ten--;
                        five--;
                    }
                } else //3*5
                {
                    if (five < 3) return false;
                    else five -= 3;
                }
            } else if (bills[i] - 5 == 5)//10
            {
                if (five == 0) return false;
                five--;
                ten++;
            } else //5
            {
                five++;
            }


        }
        return true;
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;
        ListNode t = new ListNode(0);
        t.next = head;
        ListNode node = head;
        while (true) {
            if (node.next != null) {
                if (node.next.val == node.val) node.next = node.next.next;
                else node = node.next;
            } else break;
        }
        return t.next;


    }

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> l1 = new ArrayList<>();//nums1
        List<List<Integer>> l2 = new ArrayList<>();//nums2
        for (int i = Math.min(nums1.length, k); i > 0; i--) {
            // 长度为i
            if (l1.size() == 0) {

            }

        }
        for (int i = 1; i < Math.min(nums2.length, k); i++) {

        }
        return null;

    }

    public boolean isCousins(TreeNode root, int x, int y) {
        int[] highs = new int[100];//1-100
        CousinsHigh(root, highs, 0);
        return (highs[x - 1] / 101 != highs[y - 1] / 101)
                && (highs[x - 1] % 101 == highs[y - 1] % 101);

    }

    void CousinsHigh(TreeNode root, int[] highs, int high) {
        if (root == null) return;
        highs[root.val - 1] = high + 1;
        CousinsHigh(root.left, highs, high + 1 + 101 * root.val);
        CousinsHigh(root.right, highs, high + 1 + 101 * root.val);
    }

    int diameterOfBinaryTreeMax = 1;

    public int diameterOfBinaryTree(TreeNode root) {

        diameterOfBinaryTreeTraverse(root);
        return diameterOfBinaryTreeMax - 1;

    }

    int diameterOfBinaryTreeTraverse(TreeNode root) {
        if (root == null) return 0;
        int l = diameterOfBinaryTreeTraverse(root.left);
        int r = diameterOfBinaryTreeTraverse(root.right);
        if (l + r + 1 > diameterOfBinaryTreeMax) diameterOfBinaryTreeMax = l + r + 1;
        return Math.max(l, r) + 1;
    }

    public int largestSumAfterKNegations(int[] A, int K) {
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

    int[] helperMaxNums(int len, int[] num) {
        if (len == num.length) return num;
        for (int i = 0; i < len; i++) {
            if (num[0] < num[1]) ;
        }
        return null;
    }

    public static int[] threeEqualParts(int[] A) {

        int[] IMP = new int[]{-1, -1};
        int N = A.length;

        int S = 0;
        for (int x : A) S += x;
        if (S % 3 != 0) return IMP;
        int T = S / 3;
        int sum = 0;
        int[] interveles = new int[6];
        int p = 0;
        for (int i = 0; i < N; i++) {

            sum += A[i];
            if ((sum % T == 0 || sum % T == 1) && p < 6 && A[i] == 1) {
                interveles[p] = i;
                p++;
            }

        }
        int c = A.length - interveles[5] - 1;
        if (interveles[1] + c < interveles[2] && interveles[3] + c < interveles[4])
            return new int[]{interveles[1] + c, interveles[3] + c + 1};
        else return IMP;

    }

    public int[] sortArrayByParityII(int[] A) {
        //int[] B = new int[A.length];
        //
        int i = 0;//偶数
        int j = 1;//奇数
        for (; i < A.length; i += 2) {
            if (A[i] % 2 == 1)//偶数的位置是奇数
            {
                while (A[j] % 2 == 1)//找到奇数位置的偶数
                {
                    j += 2;
                }
                //交换一个奇数和一个偶数错误元素
                int t = A[j];
                A[j] = A[i];
                A[i] = t;
            }
        }
        return A;
        /*
        for (int k = 0; k <A.length; k++) {
            if(A[k]%2==0)
            {
                B[i]=A[k];
                i+=2;
            }
            else
            {
                B[j]=A[k];
                j+=2;
            }

        }
        */
        //return B;

    }

    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        preTraverse(root1, l1);
        preTraverse(root2, l2);
        return l1.equals(l2);
    }

    void preTraverse(TreeNode root, List<Integer> l) {
        if (root == null) return;
        if (root.left == null && root.right == null) l.add(root.val);
        preTraverse(root.left, l);
        preTraverse(root.right, l);
    }

    public String shortestCompletingWord(String licensePlate, String[] words) {
        int[][] alphabet = new int[words.length][26];
        int[] ab = new int[26];//
        licensePlate = licensePlate.toLowerCase();
        for (int i = 0; i < licensePlate.length(); i++) {
            if (licensePlate.charAt(i) >= 'a' && licensePlate.charAt(i) <= 'z')
                ab[licensePlate.charAt(i) - 'a']++;
        }
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            for (int j = 0; j < words[i].length(); j++) {
                if (word.charAt(j) >= 'a' && word.charAt(j) <= 'z')
                    alphabet[i][word.charAt(j) - 'a']++;
            }
        }

        List<Integer> index = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            int flag = 0;
            for (int j = 0; j < 26; j++) {
                if (alphabet[i][j] < ab[j]) {
                    flag++;
                    break;
                }
            }
            if (flag == 0)
                index.add(i);
        }
        int min = licensePlate.length();
        int r = -1;
        for (int i = 0; i < index.size(); i++) {
            if (words[index.get(i)].length() < min) {
                min = words[index.get(i)].length();
                r = index.get(i);
            }
        }
        return words[r];

    }

    public int findShortestSubArray(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : nums) {
            if (map.containsKey(i))
                map.put(i, map.get(i) + 1);
            else map.put(i, 1);
        }
        int max = 1;
        int key = -1;
        for (Map.Entry entry : map.entrySet()) {
            if ((int) entry.getValue() > max) {
                max = (int) entry.getValue();
                //key=(int)entry.getKey();
            }
        }
        int min = Integer.MAX_VALUE;
        for (Map.Entry entry : map.entrySet()) {
            if ((int) entry.getValue() == max) {
                key = (int) entry.getKey();
                int i = 0;
                int j = nums.length - 1;
                while (i < j) {
                    if (nums[i] == key)
                        break;
                    i++;
                }
                while (i < j) {
                    if (nums[j] == key) break;
                    j--;
                }
                if (j - i + 1 < min) min = j - i + 1;
            }

        }

        return min;


    }

    public static String toHex(int num) {
        if (num == 0) return "0";
        //先转化为二进制
        StringBuilder sb = new StringBuilder();
        //String bin="";
        //String hex=null;
        long n = num;
        if (n < 0)//负数  去掉符号的正数部分反码加一
        {
            n = -n;
            //取反码加一   //Integer.MAX_VALUE 2^31-1  不够
            n = 0xffffffffL - n + 1;//2^32-1-num+1  (long)0xffffffff

            // 转16进制

        }

        while (n > 0) {
            sb.append(Int2Hex((int) (n % 16)));
            n /= 16;
        }
        return sb.reverse().toString();

    }

    static char Int2Hex(int n) {
        if (n < 10)
            return (char) (n + '0');
        else
            return (char) (n - 10 + 'a');
    }

    public int numberOfBoomerangs(int[][] points) {
        int count = 0;
        for (int i = 0; i < points.length; i++) {
            HashMap<Integer, Integer> map = new HashMap<>();//以i为中心的距离 统计
            for (int j = 0; j < points.length; j++) {
                if (j != i) {
                    int distance = (points[i][0] - points[j][0]) * (points[i][0] - points[j][0]) +
                            (points[i][1] - points[j][1]) * (points[i][1] - points[j][1]);
                    if (map.containsKey(distance))
                        map.put(distance, map.get(distance) + 1);
                    else map.put(distance, 1);
                }

            }
            //遍历map
            for (Map.Entry entry : map.entrySet()) {
                int n = (int) entry.getValue();
                count += n * (n - 1);//c(n,2)*2
            }

        }
        return count;

    }

    public int findLUSlength(String a, String b) {
        //最长特殊子序列
        //？？没思
        if (a.length() == b.length()) {
            return a.equals(b) ? -1 : a.length();
        } else return Math.max(a.length(), b.length());

    }

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums2.length; i++) {
            for (int j = i + 1; j < nums2.length; j++) {
                if (nums2[j] > nums2[i]) {
                    map.put(nums2[i], nums2[j]);
                    break;
                }
            }
        }
        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = map.getOrDefault(nums1[i], -1);
        }
        return nums1;
    }

    public boolean isSubtree(TreeNode s, TreeNode t) {
        //t是s的子树
        if (s == null && t != null) return false;
        if (s == null && t == null) return true;
        if (s != null && t == null) return false;
        boolean flag = false;
        if (s.val == t.val) {
            flag = isSubtree1(s.left, t.left) && isSubtree1(s.right, t.right);
        }
        if (flag) return true;
        flag = isSubtree(s.right, t);
        if (flag) return true;
        flag = isSubtree(s.left, t);
        return flag;

    }

    public boolean isSubtree1(TreeNode s, TreeNode t) {
        if (s == null && t != null) return false;
        if (s == null && t == null) return true;
        if (s != null && t == null) return false;
        boolean flag = false;
        if (s.val == t.val) {
            flag = isSubtree1(s.left, t.left) && isSubtree1(s.right, t.right);
        }
        return flag;
    }

    public int tribonacci(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 1;
        int a = 0;
        int b = 1, c = 1, d = 2;
        for (int i = 2; i < n; i++) {
            d = a + b + c;
            a = b;
            b = c;
            c = d;
        }
        return d;


    }

    public int countLargestGroup(int n) {
        int r = 0;
        int[] sum = new int[36];
        for (int i = 1; i <= n; i++) {
            sum[cbitSum(i) - 1]++;
        }
        int max = -1;
        for (int i = 0; i < 36; i++) {
            if (sum[i] > max) max = sum[i];
        }
        for (int i = 0; i < 36; i++) {
            if (sum[i] == max) r++;
        }
        return r;

    }

    int cbitSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    public int specialArray(int[] nums) {
        int[] count = new int[1001];
        for (int i = 0; i < nums.length; i++) {
            count[nums[i]]++;
        }
        for (int i = 1000; i - 1 >= 0; i--) {
            count[i] += count[i - 1];
        }
        for (int i = 0; i + 1 < 1001; i++) {
            if (count[i] == i + 1) return i;
        }
        return -1;

    }

    public int rotatedDigits(int N) {
        //0 1 8    2 5 6 9          3 47
        //至少含一位 2  5  6 9
        int good = 0;
        for (int i = 1; i <= N; i++) {
            int must = 0;
            while (i > 0) {
                int t = i % 10;
                if (t == 3 || t == 4 || t == 7) {
                    must = 0;
                    break;
                } else if (t == 5 || t == 6 || t == 2 || t == 9) {
                    must++;
                }

                i /= 10;
            }
            if (must > 0) good++;
        }
        return good;

    }

    public int maxCount(int m, int n, int[][] ops) {
        //a b找到最小的a  b
        int mina = m + 1, minb = n + 1;
        for (int i = 0; i < ops.length; i++) {
            if (ops[i][0] < mina) mina = ops[i][0];
            if (ops[i][1] < minb) minb = ops[i][1];
        }

        return mina * minb;
    }

    public int firstUniqChar(String s) {
        int[] alphabet = new int[26];
        for (int i = 0; i < s.length(); i++) {
            alphabet[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < s.length(); i++) {
            if (alphabet[s.charAt(i) - 'a'] == 1) return i;
        }
        return -1;

    }

    //s = "abcdefg", k = 2 reverseStr("abcdefg",2)
    public static String reverseStr(String s, int k) {
        StringBuilder sb = new StringBuilder();
        int i = k;
        for (; i < s.length(); i += k) {
            if (i / k % 2 == 1) {
                StringBuilder sb1 = new StringBuilder().append(s, i - k, i);
                sb.append(sb1.reverse());
            } else {
                sb.append(s, i - k, i);
            }
        }
        if (i / k % 2 == 1) {
            StringBuilder sb1 = new StringBuilder().append(s, i - k, s.length());
            sb.append(sb1.reverse());
        } else sb.append(s, i - k, s.length());
        return sb.toString();

    }

    //    public int longestPalindrome(String s) {
//        //统计每个词的次数
//        HashMap<Character, Integer> map = new HashMap<>();
//        for (int i = 0; i < s.length(); i++) {
//            if (map.containsKey(s.charAt(i))) {
//                map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
//            } else map.put(s.charAt(i), 1);
//        }
//        int flag = 0;
//        int count = 0;
//        for (Map.Entry entry : map.entrySet()) {
//            if ((int) entry.getValue() % 2 == 1)
//                flag = 1;
//            count += ((int) entry.getValue() / 2) * 2;
//        }
//        return count;
//    }
    public String longestPalindrome(String s) {
        //最长回文子串
        //根据长度遍历
        for (int i = s.length(); i > 0; i--) {//长度由len到1
            for (int j = 0; j + i <= s.length(); j++) {
                if (isPalindrome(s.substring(j, j + i))) return s.substring(j, j + i);
            }

        }
        return "";

    }

    public int myAtoi(String s) {
        if (s.length() == 0) return 0;
        int i = 0;
        int sum = 0;
        //去开头空格
        for (; i < s.length(); i++) {
            if (s.charAt(i) != ' ') break;
        }
        if (i < s.length())
            if (s.charAt(i) == '-') {
                i++;

                for (; i < s.length(); i++) {
                    if (!Character.isDigit(s.charAt(i))) break;
                    else {

                        //判断是否溢出
                        int t = s.charAt(i) - '0';
                        if ((Integer.MIN_VALUE + t) / 10 <= sum)
                            sum = sum * 10 - t;
                        else return Integer.MIN_VALUE;
                    }
                }

            } else {
                if (s.charAt(i) == '+')
                    i++;
                for (; i < s.length(); i++) {
                    if (!Character.isDigit(s.charAt(i))) break;
                    else {

                        //判断是否溢出
                        int t = s.charAt(i) - '0';
                        if ((Integer.MAX_VALUE - t) / 10 >= sum)
                            sum = sum * 10 + t;
                        else return Integer.MAX_VALUE;
                    }
                }

            }
        return sum;


    }

    boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }

    public String convert(String s, int numRows) {

        List<StringBuilder> list = new ArrayList<>();
        int n = 0;
        int f = 0;//向下.
        for (int i = 0; i < Math.min(numRows, s.length()); i++) {
            list.add(new StringBuilder());
        }
        for (int i = 0; i < s.length(); i++) {

            list.get(n).append(s.charAt(i));
            if (f == 0) n++;
            else n--;
            if (n == -1) {
                f = 1 - f;
                n = 1;
            } else if (n == numRows) {
                n = numRows - 2;
                f = 1 - f;
            }


        }
        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : list) ret.append(row);
        return ret.toString();


    }

    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) return 0;
        if (points.length == 1) return 1;
        Arrays.sort(points, new Comparator<int[]>() {
                    public int compare(int[] point1, int[] point2) {
                        if (point1[1] > point2[1]) {//根据右端点排序
                            return 1;
                        } else if (point1[1] < point2[1]) {
                            return -1;
                        } else {
                            return 0;
                        }

                    }
                }
        );
        int sum = 1;
        int pos = points[0][1];
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] <= pos) ;
            else {
                pos = points[i][1];
                sum++;
            }

        }
        return sum;


    }

    public List<List<Integer>> permute(int[] nums) {
        int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        permuteAdd(list, res, 0);
        return res;


    }

    void permuteAdd(List<Integer> l, List<List<Integer>> res, int first) {
        if (first == l.size())
            res.add(new ArrayList<Integer>(l));
        for (int i = first; i < l.size(); i++) {
            Collections.swap(l, first, i);//交换

            permuteAdd(l, res, first + 1);
            Collections.swap(l, first, i);//撤销

        }
    }


    public boolean isEvenOddTree(TreeNode root) {
        List<List<Integer>> l = new ArrayList<>();
        traverseEO(root, 1, l);
        //检查
        for (int i = 0; i < l.size(); i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < l.get(i).size(); j++) {
                    if (l.get(i).get(j) % 2 == 0) return false;
                    else if (j > 0) {
                        if (l.get(i).get(j) <= l.get(i).get(j - 1))
                            return false;
                    }

                }
            } else {
                for (int j = 0; j < l.get(i).size(); j++) {
                    if (l.get(i).get(j) % 2 == 1) return false;
                    else if (j > 0) {
                        if (l.get(i).get(j) >= l.get(i).get(j - 1))
                            return false;
                    }

                }
            }

        }
        return true;


    }

    //父左右遍历
    void traverseEO(TreeNode root, int h, List<List<Integer>> l) {
        if (root == null) return;
        if (h > l.size()) l.add(new ArrayList<>());
        l.get(h - 1).add(root.val);
        traverseEO(root.left, h + 1, l);
        traverseEO(root.right, h + 1, l);

    }

    public TreeNode pruneTree(TreeNode root) {//注意引用是复制的 改变引用的指向不会改变原来的对象
        //左右父遍历  后序遍历
        //去掉叶子节点的0
        if (root == null) return root;
        if (pruneTree(root.left) == null) root.left = null;
        if (pruneTree(root.right) == null) root.right = null;
        if (root.right == null && root.left == null) {
            if (root.val == 0) root = null;
        }
        return root;

    }

    public ListNode swapPairs(ListNode head) {
        ListNode node = head;
        ListNode node1, node2, node3, node0;
        node3 = new ListNode(0);
        node0 = node3;
        node3.next = node;
        while (node != null && node.next != null) {
            node1 = node;
            node2 = node.next;

            node3.next = node2;
            node1.next = node2.next;
            node2.next = node1;

            node3 = node1;
            node = node1.next;

        }
        return node0.next;


    }

    static List<List<Integer>> com = new ArrayList<>();

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {

        List<Integer> list = new ArrayList<>();
        combinationH(list, 0, 0, candidates, target);
        return com;

    }

    public void sortColors(int[] nums) {
        int r = 0, w = 0, b = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                r++;
            } else if (nums[i] == 1) {
                w++;
            } else {
                b++;
            }
        }
        int i = 0;
        for (; i < r; i++)//0 -r-1
        {
            nums[i] = 0;
        }
        for (; i < r + w; i++)//r r+w-1
        {
            nums[i] = 1;
        }
        for (; i < nums.length; i++) {
            nums[i] = 2;
        }


    }

    public int countNodes(TreeNode root) {
        //父 左右
        if (root == null) return 0;
        return countNodes(root.left) + countNodes(root.right) + 1;

    }

    static void combinationH(List<Integer> list, int sum, int i, int[] candidates, int target) {
        if (sum > target) return;
        if (sum == target) {
            com.add(new ArrayList<Integer>(list));
            return;
        }
        if (i >= candidates.length) return;
        while (sum <= target) {
            //0个candidates[i] 到最大
            //参数  当前队列内容 读到的候选位置 当前和
            combinationH(new ArrayList<Integer>(list), sum, i + 1, candidates, target);
            sum += candidates[i];
            list.add(candidates[i]);
        }
    }

    public int search(int[] nums, int target) {
        if (nums.length == 1) {
            if (target == nums[0]) return 0;
            else return -1;
        }
        int len = nums.length;
        int begin = 0, end = nums.length - 1;
        int mid = 0;
        while (begin <= end) {
            mid = (begin + end) / 2;
            if (nums[mid] >= nums[0] && nums[mid + 1] >= nums[0]) {
                //mid在左侧部分
                begin = mid + 1;
            } else if (nums[mid] <= nums[len - 1] && nums[mid + 1] <= nums[len - 1]) {
                end = mid - 1;

            } else if (nums[mid] >= nums[0] && nums[mid + 1] <= nums[len - 1]) {

                break;
            }
        }
        //mid是最大值下标 mid+1是最小值下标
        //mid+1...len-1 ,0...mid
        begin = mid + 1;
        end = mid;//begin映射到0  end映射到len-1
        int t = len - mid - 1;
        while ((begin + t) % len <= (end + t) % len) {
            mid = (((begin + t) % len + (end + t) % len) / 2 - t + len) % len;
            if (nums[mid] < target) {
                begin = (mid + 1) % len;
            } else if (nums[mid] > target) {
                end = (mid - 1 + len) % len;
            } else return mid;

        }
        return -1;

    }

    public boolean canJump(int[] nums) {
        int farest = 0;//能跳到的最远距离
        for (int i = 0; i < nums.length; i++) {
            if (i > farest)//跳不到这.
                return false;
            farest = Math.max(farest, i + nums[i]);
        }
        return true;
    }


    public List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        if (digits.length() == 0) return list;
        if (digits.length() == 1) {
            switch (digits.charAt(0)) {
                case '2': {
                    list.add("a");
                    list.add("b");
                    list.add("c");

                    break;
                }
                case '3': {
                    list.add("d");
                    list.add("e");
                    list.add("f");
                    break;
                }
                case '4': {
                    list.add("g");
                    list.add("h");
                    list.add("i");

                    break;
                }
                case '5': {
                    list.add("j");
                    list.add("k");
                    list.add("l");

                    break;
                }
                case '6': {
                    list.add("m");
                    list.add("n");
                    list.add("o");


                    break;
                }
                case '7': {
                    list.add("p");
                    list.add("q");
                    list.add("r");
                    list.add("s");
                    break;
                }
                case '8': {
                    list.add("t");
                    list.add("u");
                    list.add("v");
                    break;
                }
                case '9': {
                    list.add("w");
                    list.add("x");
                    list.add("y");
                    list.add("z");
                    break;
                }
            }
            return list;
        } else {
            for (String s : letterCombinations(digits.substring(1))) {
                for (String s1 : letterCombinations(String.valueOf(digits.charAt(0)))) {
                    list.add(s1 + s);
                }
            }
        }
        return list;


    }

    public List<String> generateParenthesis(int n) {
        /*
         * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且
         * 有效的 括号组合。
         * */
        return G(n);


    }

    public List<String> G(int n) {
        //分成两部分
        ArrayList<String> ans = new ArrayList<String>();
        if (n == 0) ans.add("");
        for (int i = 0; i < n; i++) { //(left)+rght
            for (String left : G(i)) {
                for (String right : G(n - i - 1)) {
                    ans.add("(" + left + ")" + right);
                }
            }

        }
        return ans;

    }

    public int maxArea(int[] height) {
        int m = 0;
//        for (int i = 0; i <height.length ; i++) {
//            for (int j = i+1; j <height.length ; j++) {
//                m=Math.max(m,(j-i)*Math.min(height[j], height[i]));
//
//            }
//
//        }
        int l = 0;
        int r = height.length - 1;
        int lh = height[0];
        int rh = height[r];
        m = (r - l) * Math.min(lh, rh);
        while (l < r) {
            if (lh < rh) {
                l++;
                if (lh < height[l]) {
                    lh = height[l];
                    m = Math.max(m, (r - l) * Math.min(lh, rh));
                }
            } else {
                r--;
                if (rh < height[r]) {
                    rh = height[r];
                    m = Math.max(m, (r - l) * Math.min(lh, rh));
                }
            }

        }
        return m;

    }

    public int divide(int dividend, int divisor) {

        if (dividend == 0) return 0;
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == Integer.MIN_VALUE) return 1;
            else {
                return divide(dividend + divisor, divisor) + (divisor < 0 ? 1 : -1);
            }
        }
        int flag = 1;
        int r = 0;
        if ((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0)) {
            //结果是负数
            flag = -1;
        }
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        while (divisor <= dividend) {
            r++;
            dividend -= divisor;
        }
        if (flag == -1) return -r;
        else return r;

    }

    public int lengthOfLongestSubstring(String s) {
        HashSet<Character> set = new HashSet<>();
        int len = 0;
        int r = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0) set.remove(s.charAt(i - 1));

            while (r < s.length() && !set.contains(s.charAt(r))) {
                set.add(s.charAt(r));
                r++;

            }
            len = Math.max(len, r - i);


        }
        return len;

    }

    public String[] findRelativeRanks(int[] nums) {
        int[] grades = new int[nums.length];
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        //分数对应的名次存起来
        int[] k = nums.clone();
        Arrays.sort(k);
        for (int i = 0; i < k.length; i++) {
            map.put(k[i], k.length - i);
        }
        for (int i = 0; i < nums.length; i++) {
            grades[i] = map.get(nums[i]);
        }
        String[] s = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (grades[i] == 1) s[i] = "Gold Medal";
            else if (grades[i] == 2) s[i] = "Silver Medal";
            else if (grades[i] == 3) s[i] = "Bronze Medal";
            else {
                s[i] = String.valueOf(grades[i]);
            }
        }
        return s;

    }

    public boolean detectCapitalUse(String word) {
        if (word.length() == 1) return true;
        if (word.charAt(0) > 'Z')//小写
        {
            for (int i = 1; i < word.length(); i++) {
                if (word.charAt(i) <= 'Z') return false;
            }
            return true;
        } else//大写
        {
            if (word.charAt(1) > 'Z')//小写
            {
                for (int i = 2; i < word.length(); i++) {
                    if (word.charAt(i) <= 'Z') return false;
                }
                return true;
            } else//大写
            {
                for (int i = 2; i < word.length(); i++) {
                    if (word.charAt(i) > 'Z') return false;
                }
                return true;
            }
        }

    }

    public char findTheDifference(String s, String t) {
        int[] ss = new int[26];
        int[] tt = new int[26];
        for (int i = 0; i < s.length(); i++) {
            ss[s.charAt(i) - 'a']++;
            tt[t.charAt(i) - 'a']++;
        }
        tt[t.charAt(t.length() - 1)]++;
        for (int i = 0; i < 26; i++) {
            if (ss[i] != tt[i]) {
                return (char) ('a' + i);
            }
        }
        return ' ';


    }

    public int distributeCandies(int[] candies) {
        HashSet<Integer> set = new HashSet<Integer>();

        for (int i : candies) {
            set.add(i);
        }
        return Math.min(set.size(), candies.length / 2);


    }

    public boolean repeatedSubstringPattern(String s) {
        for (int k = 1; k <= s.length() / 2; k++) {
            int t = 0;
            if (s.length() % k == 0) {
                int flag = 0;
                for (int j = 0; j < k; j++)//子串的每个元素
                {

                    for (int i = j; i + k < s.length(); i += k) {
                        if (s.charAt(i) != s.charAt(i + k)) {
                            flag = 1;
                            break;//k++
                        }

                    }
                    if (flag == 1) {
                        t = 1;
                        break;
                    }
                }
            } else continue;
            if (t == 0) return true;

        }
        return false;

    }

    int[] flag = new int[2500];

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int color = image[sr][sc];
        image[sr][sc] = newColor;
        explore(image, color, sr, sc, newColor);//StackOverflow
        return image;

    }

    void explore(int[][] image, int color, int r, int c, int newColor) {
        //上下左右
        if (c - 1 >= 0) {
            if (flag[r * 50 + c - 1] == 0)//没有访问过的位置
            {
                if (image[r][c - 1] == color) {
                    image[r][c - 1] = newColor;
                    explore(image, color, r, c - 1, newColor);
                }
            }
        }
        if (c + 1 < image[0].length) {
            if (flag[r * 50 + c + 1] == 0)//没有访问过的位置
            {
                if (image[r][c + 1] == color) {
                    image[r][c + 1] = newColor;
                    explore(image, color, r, c + 1, newColor);
                }
            }
        }
        if (r - 1 >= 0) {
            if (flag[(r - 1) * 50 + c] == 0)//没有访问过的位置
            {
                if (image[r - 1][c] == color) {
                    image[r - 1][c] = newColor;
                    explore(image, color, r - 1, c, newColor);
                }
            }
        }
        if (r + 1 < image.length) {
            if (flag[(r + 1) * 50 + c] == 0)//没有访问过的位置
            {
                if (image[r + 1][c] == color) {
                    image[r + 1][c] = newColor;
                    explore(image, color, r + 1, c, newColor);
                }
            }
        }
    }

    public int[][] matrixReshape(int[][] nums, int r, int c) {
        if (r * c != nums.length * nums[0].length
                || (r == nums.length && c == nums[0].length)) return nums;
        int[][] result = new int[r][c];
        int i = 0, j = 0;
        for (int k = 0; k < result.length; k++)
            for (int l = 0; l < result[0].length; l++) {
                result[k][l] = nums[i][j];
                if (++j >= nums.length) {
                    j = 0;
                    i++;
                }
            }
        return result;

    }

    public boolean checkRecord(String s) {
        int A = 0;//A<=1
        int L = 0;//L<=2
        for (char c : s.toCharArray()) {
            if (c == 'L') {
                L++;
                if (L == 3) return false;
            } else L = 0;
            if (c == 'A') A++;
        }
        return A <= 1 && L <= 2;

    }

    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0;
        int count = 0;
        for (int i : nums) {
            if (i == 0) {
                if (count > max) max = count;
                count = 0;
            } else count++;
        }
        return max;

    }

    public int getMinimumDifference(TreeNode root) {
        if (root == null) return Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;
        if (root.left != null) {
            TreeNode node = root.left;
            while (node.right != null) {
                node = node.right;
            }
            if (root.val - node.val < min) min = root.val - node.val;
        }


        if (root.right != null) {
            TreeNode node = root.left;
            while (node.left != null) {
                node = node.left;
            }
            if (root.val - node.val < min) min = root.val - node.val;
        }

        if (min == 1) return 1;
        return Math.min(min, Math.min(getMinimumDifference(root.left), getMinimumDifference(root.right)));
    }

    public int minDiffInBST(TreeNode root) {
        if (root == null) return Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;
        if (root.left != null) {
            TreeNode node = root.left;
            while (node.right != null) {
                node = node.right;
            }
            if (root.val - node.val < min) min = root.val - node.val;
        }


        if (root.right != null) {
            TreeNode node = root.left;
            while (node.left != null) {
                node = node.left;
            }
            if (root.val - node.val < min) min = root.val - node.val;
        }

        if (min == 1) return 1;
        return Math.min(min, Math.min(minDiffInBST(root.left), minDiffInBST(root.right)));
    }

    public String thousandSeparator(int n) {

        String s = String.valueOf(n);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = s.length() - 1; i >= 0; i--) {

            if (count == 3) {
                sb.append('.');
                count = 0;
            }
            sb.append(s.charAt(i));
            count++;
        }
        s = sb.reverse().toString();
        if (s.charAt(0) == '.') s = s.substring(1);
        return s;


    }

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] count = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            count[arr1[i]]++;
        }
        int pos = 0;
        for (int i = 0; i < arr2.length; i++) {
            while (count[arr2[i]] > 0) {
                arr1[pos] = arr2[i];
                count[arr2[i]]--;
                pos++;
            }
        }
        //剩下的数
        for (int num1 = 0; num1 < count.length; num1++) {
            while (count[num1] > 0) {
                arr1[pos++] = num1;
                count[num1]--;
            }
        }

        return arr1;


    }

    public int numEquivDominoPairs(int[][] dominoes) {
        int count = 0;
        int[][] v = new int[9][9];
        //数对i j存在i-1)*9+j-1
        for (int i = 0; i < dominoes.length; i++) {
            v[dominoes[i][0] - 1][dominoes[i][1] - 1]++;
        }
        for (int i = 0; i < v.length; i++)
            for (int j = 0; j < v[0].length; j++) {
                if (v[i][j] == 0) continue;
                else {
                    if (i != j) {
                        v[i][j] += v[j][i];
                        v[j][i] = 0;
                    }
                    count += v[i][j] * (v[i][j] - 1) / 2;

                }
            }
        return count;

    }

    public int minOperations(String[] logs) {
        int count = 0;
        for (String log : logs) {
            if (log.equals("../")) {
                if (count != 0) count--;
            } else if (log.equals("./")) ;
            else count++;

        }
        return count;

    }

    public int findLucky(int[] arr) {

        int[] frequency = new int[500];
        for (int i = 0; i < arr.length; i++) {
            frequency[arr[i]]++;
        }

        for (int i = frequency.length - 1; i >= 0; i--) {
            if (frequency[i] == i)
                return i;
        }

        return -1;

    }

    public int distanceBetweenBusStops(int[] distance, int start, int destination) {
        //两个方向
        if (start == destination) return 0;
        int sum1 = 0;
        for (int i = start; ; i = (i + 1) % distance.length) {
            sum1 += distance[i];
            if ((i + 1) % distance.length == destination)
                break;
        }
        int sum2 = 0;
        for (int i = start; ; i = (i + distance.length - 1) % distance.length) {
            sum2 += distance[(i + distance.length - 1) % distance.length];
            if ((i + distance.length - 1) % distance.length == destination)
                break;
        }
        return Math.min(sum1, sum2);
    }

    public String reformatDate(String date) {
        String[] Months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        //最后四位是年
        String year = date.substring(date.length() - 4);
        StringBuilder sb = new StringBuilder();
        for (char c : date.toCharArray()) {
            if (c >= '0' && c <= '9')
                sb.append(c);
            else break;
        }
        String day = sb.toString();
        if (day.length() == 1) day = "0" + day;
        String month = null;
        for (int i = 3; i < date.length(); i++) {
            if (date.charAt(i) == ' ') {
                month = date.substring(i + 1, i + 4);
                break;
            }


        }
        for (int i = 0; i < Months.length; i++) {
            if (Months[i].equals(month)) month = String.valueOf(i + 1);
        }
        if (month.length() == 1) month = "0" + month;
        sb = new StringBuilder();
        sb.append(year).append('-').append(month).append('-').append(day);
        return sb.toString();

    }

    public int maxNumberOfBalloons(String text) {
        int[] balloon = new int[5];
        for (char c : text.toCharArray()) {
            if (c == 'b') balloon[0]++;
            else if (c == 'a') balloon[1]++;
            else if (c == 'l') balloon[2]++;
            else if (c == 'o') balloon[3]++;
            else if (c == 'n') balloon[4]++;
        }
        balloon[2] /= 2;
        balloon[3] /= 2;
        Arrays.sort(balloon);
        return balloon[0];

    }

    public int islandPerimeter(int[][] grid) {
        int len = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++) {
                //
                if (grid[i][j] == 1) {
                    //判断在上下左右  墙  或者 0
                    if (j - 1 == -1) {
                        len++;
                    } else if (grid[i][j - 1] == 0) {
                        len++;
                    }
                    if (j + 1 == grid[i].length) {
                        len++;
                    } else if (grid[i][j + 1] == 0) {
                        len++;
                    }
                    if (i + 1 == grid.length) {
                        len++;
                    } else if (grid[i + 1][j] == 0) {
                        len++;
                    }
                    if (i - 1 == -1) {
                        len++;
                    } else if (grid[i - 1][j] == 0) {
                        len++;
                    }

                }

            }
        return len;


    }

    public List<List<Integer>> generate(int numRows) {
        //给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
        List<List<Integer>> r = new ArrayList<>();
        //第i行有i+1个元素   第0行 1个元素
        //空间
        List<Integer> row1 = new ArrayList<>();
        row1.add(1);
        r.add(row1);
        for (int i = 1; i < numRows; i++) {
            List<Integer> row = new ArrayList<>(Arrays.asList(new Integer[i + 1]));
            row.set(0, 1);
            row.set(i, 1);
            for (int j = 1; j < i; j++) {
                row.set(j, r.get(i - 1).get(j) + r.get(i - 1).get(j - 1));

            }
            r.add(row);
        }
        return r;


    }

    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> l = new ArrayList<>();
        Arrays.sort(arr);
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < arr.length; i++) {
            int v = arr[i] - arr[i - 1];
            if (v < min) min = v;
        }
        for (int i = 1; i < arr.length; i++) {
            int v = arr[i] - arr[i - 1];
            if (v == min) {
                List<Integer> t = new ArrayList<Integer>();
                t.add(arr[i - 1]);
                t.add(arr[i]);
                l.add(t);
            }
        }
        return l;

        //


    }

    public String licenseKeyFormatting(String S, int K) {
        S = S.toUpperCase();
        StringBuilder sb = new StringBuilder();
        int i = S.length() - 1;
        int l = 0;
        while (i >= 0) {
            if (l == K) {
                sb.append('-');
                l = 0;
            }

            char c = S.charAt(i);
            if (c != '-') {
                sb.append(c);
                l++;
            }
            i--;
        }
        S = sb.reverse().toString();
        if (S.length() == 0) return "";
        if (S.charAt(0) == '-')
            return S.substring(1);
        else return S;


    }

    static void countSort(int[] num, int[] result) {
        //int[] num=new int[]{6,0,2,0,1,3,4,6,1,3,2};
        int k = 1000;
        int[] count = new int[k];
        //int[]result=new int[num.length];
        for (int i : num) {
            count[i]++;
        }
        for (int i = 0; i + 1 < k; i++) {
            count[i + 1] += count[i];
        }
        //放置结果
        for (int i = 0; i < num.length; i++) {
            result[count[num[i]] - 1] = num[i];
            count[num[i]]--;
        }
        //num=result;

    }

    public boolean isPowerOfFour(int num) {
        double n = Math.log(num) / Math.log(4);
        return n - (int) n == 0;

    }

    public int dayOfYear(String date) {

        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8));

        int year = Integer.parseInt(date.substring(0, 4));
        boolean run = year % 4 == 0;//闰年
        int[] months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int num = 0;
        if (month >= 2 || (month == 1 && day == 29))
            num += run ? 1 : 0;
        for (int i = 1; i <= month - 1; i++) {
            num += months[i - 1];
        }
        num += day;
        return num;

    }

    public static void duplicateZeros1(int[] arr) {
        //找到0的个数  不包含越界的0
        //如果最后一个可写的元素是0  有可能写1个 有可能写2个
        int i = 0;
        int count = 0;
        int flag = 0;
        while (i + count < arr.length) {
            if (arr[i] == 0) {
                if (i + count == arr.length - 1) {
                    flag = 1;
                    arr[arr.length - 1] = 0;
                } else
                    count++;
            }
            i++;

        }
        int j = arr.length - 1 - flag;
        for (int k = i - flag - 1; k >= 0; k--) {
            arr[j] = arr[k];
            if (arr[k] == 0)
                arr[--j] = 0;
            j--;

        }

    }

    public static void duplicateZeros(int[] arr) {
        int[] tmp = new int[arr.length];
        int i = 0, j = 0;
        while (i < arr.length && j < arr.length) {
            tmp[j] = arr[i];
            if (arr[i] == 0)
                if (j + 1 < arr.length)
                    tmp[++j] = 0;
            i++;
            j++;


        }
        //直接用arr=tmp  指 不行 应该是由于tmp是函数内new出来的
        for (int k = 0; k < arr.length; k++) {
            arr[k] = tmp[k];
        }


    }

    public int numPrimeArrangements(int n) {
        int[] t = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53,
                59, 61, 67, 71, 73, 79, 83, 89, 97};
        int num = 0;//质数个数
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < t.length; j++) {
                if (i == t[j]) {
                    num++;
                    break;
                }
            }

        }
        return (int) ((fnum(num) * fnum(n - num)) % (1e9 + 7));//num! *(n-num)!


    }

    long fnum(int n) {
        if (n == 0 || n == 1) return 1;
        return n * fnum(n - 1);
    }
    //阶乘

    public double findMaxAverage(int[] nums, int k) {
        int max = Integer.MIN_VALUE;
        int sum = 0;
        int i = 0;
        for (int j = 0; j < k; j++) {
            sum += nums[i + j];
        }
        for (i = 0; i + k < nums.length; i++) {
            sum = sum + nums[i + k] - nums[i];
            if (sum > max) max = sum;

        }
        return (double) max / k;

    }

    public String removeDuplicates(String S) {

        //转换成int数组  负数做标记
        int[] t = new int[S.length()];
        for (int i = 0; i < S.length(); i++) {
            t[i] = S.charAt(i);
        }
        int i = 0, j = 1;
        while (j < S.length()) {
            while (t[i] < 0) i++;
            j = i + 1;
            if (t[i] == t[j]) {
                t[i] = t[j] = -1;
                i--;

            } else {
                i += 2;
                j += 2;
            }
            while (i < 0) i++;
        }
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < S.length(); k++) {
            if (t[k] >= 0) sb.append((char) (t[i]));
        }
        return sb.toString();
    }

    boolean validPalindrome(String s, int l, int r) {
        //r l不等
        while (l < r) {
            if (s.charAt(l) == s.charAt(r)) {
                l++;
                r--;
            } else return false;
        }
        return true;

    }

    public int countSegments(String s) {
        int i = 0;
        int sum = 0;
        int len = s.length();
        while (i < len) {
            if (s.charAt(i) == ' ') i++;
            else break;
        }
        int flag = 0;//单词是空
        while (i < len) {
            char c = s.charAt(i);
            if (c == ' ') {
                if (flag == 1) {
                    sum++;
                    flag = 0;
                }
            } else {
                flag = 1;
            }
            i++;


        }
        if (flag == 1) {
            sum++;
        }
        return sum;
    }

    public static int trailingZeroes(int n) {
        //末位是2 5的个数  末位是0的个数
        if (n < 5) return 0;
        int sum = n / 10 + n / 10;
        //最后的零头2 5
        sum += n % 10 >= 5 ? 1 : 0;
        //对末位是5的数处理
        for (int i = 25; i <= n; i += 50) {
            int k = i;
            while (k % 25 == 0) {
                sum++;
                k /= 5;
            }
        }
        sum += trailingZeroes(n / 10);//对末位是0的数继续处理
        return sum;


    }

    public boolean checkPerfectNumber(int num) {
        int sum = 0;
        for (int i = 1; i * i <= num; i++) {
            if (num % i == 0) {
                sum += i;
                if (i * i != num)
                    sum += num / i;
            }
        }
        return sum == num + num;

    }

    public TreeNode insertIntoMaxTree(TreeNode root, int val) {
        TreeNode node = new TreeNode(val);
        if(val>root.val)
        {
            node.left=root;
            return node;
        }
        TreeNode t=root;TreeNode pre=root;
        while(t.val>val)
        {
            pre=t;
            t=t.left;
        }
        pre.left=node;
        node.left=t;
        return root;

    }
    public int maxIncreaseKeepingSkyline(int[][] grid) {
        int sum = 0;
        int[] r = new int[grid.length];
        int[] c = new int[grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] > r[i]) r[i] = grid[i][j];
                if (grid[i][j] > c[j]) c[j] = grid[i][j];
            }

        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int min=Math.min(r[i],c[j] );
                sum+=min-grid[i][j];
            }
        }
        return sum;

    }

    public final TreeNode getTargetCopy(final TreeNode original,
                                        final TreeNode cloned, final TreeNode target) {
        dfsTargetCopy(original, target);
        TreeNode node = cloned;
        for (int i = 0; i < pathTargetCopy.length(); i++) {
            if (pathTargetCopy.charAt(0) == '0')
                node = node.left;
            else node = node.right;

        }
        return node;

    }

    String pathTargetCopy = "";//

    boolean dfsTargetCopy(TreeNode root, TreeNode target) {
        if (root == null) return false;
        //返回路径

        if (root != target) {
            pathTargetCopy += "0";
            if (!dfsTargetCopy(root.left, target)) {
                //去掉最后一个
                pathTargetCopy = pathTargetCopy.substring(0, pathTargetCopy.length() - 1);
            } else return true;


            pathTargetCopy += "1";

            if (!dfsTargetCopy(root.right, target))
                pathTargetCopy = pathTargetCopy.substring(0, pathTargetCopy.length() - 1);
            else return true;
            return false;

        } else {
            return true;
        }


    }

    public boolean validMountainArray(int[] A) {
        if (A.length < 3) return false;
        if (A[0] >= A[1]) return false;
        int flag = 0;
        for (int i = 1; i + 1 < A.length; i++) {
            if (A[i] > A[i + 1]) {
                flag = 1;
                for (int j = i + 1; j + 1 < A.length; j++) {
                    if (A[j] <= A[j + 1]) return false;
                }
            }

        }

        return flag == 1;

    }

    public int lengthOfLastWord(String s) {
        //
        int i = s.length() - 1;
        int len = 0;
        int flag = 0;
        while (i >= 0) {
            char c = s.charAt(i);
            if ((c <= 'Z' && c >= 'A') || (c <= 'z' && c >= 'a')) {
                len++;
                flag = 1;

            } else if (flag == 1) return len;
            i--;
        }
        return len;

    }

    public int minCostToMoveChips(int[] position) {

        int odd = 0;//奇数
        int even = 0;//偶数
        for (int i : position) {
            if (i % 2 == 0) even++;
            else odd++;
        }
        return Math.min(odd, even);


    }

    public int repeatedNTimes(int[] A) {
        if (A[A.length - 1] == A[0]) return A[0];//数组长度为4特例
        if (A[A.length - 1] == A[A.length - 2]) return A[A.length - 1];//for循环情况补充
        for (int i = 0; i + 2 < A.length; i++) {
            if (A[i] == A[i + 1] || A[i] == A[i + 2]) {
                return A[i];
            }

        }
        return -1;

    }
    /*
    给定一个非空数组，返回此数组中第三大的数。
    如果不存在，则返回数组中最大的数。要求算法时间复杂度必须是O(n)。
     */

    public static String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            if (n % 26 == 0) {
                sb.append('Z');
                n /= 27;
            } else {
                sb.append((char) (n % 26 + 'A' - 1));
                n /= 26;
            }

        }
        return sb.reverse().toString();


    }

    public List<Integer> powerfulIntegers(int x, int y, int bound) {
        List<Integer> result = new ArrayList<>();
        int i = 0;
        int j = (int) (Math.log(bound) / Math.log(y));
        while (j >= 0) {
            int v = (int) (Math.pow(x, i) + Math.pow(y, j));
            if (v <= bound) {
                result.add(v);
                i++;
            } else {
                i = 0;
                j--;
            }

        }
        //去重
        return result.stream().distinct().collect(Collectors.toList());

    }

    public boolean isLongPressedName(String name, String typed) {
        int i = 0, j = 0;
        char last = 0;
        if (name.charAt(i) == typed.charAt(j)) {
            last = name.charAt(i);
            i++;
        }
        while (i < name.length() && j < typed.length()) {
            if (name.charAt(i) == typed.charAt(j)) {
                last = name.charAt(i);
                i++;
                j++;
            } else if (last == typed.charAt(j)) {
                j++;
            } else {
                return false;
            }

        }
        if (i < name.length()) {
            return false;
        }
        while (j < name.length()) {
            if (typed.charAt(j) != name.charAt(name.length() - 1)) return false;
            j++;
        }
        return true;

    }

    public static String mostCommonWord(String paragraph, String[] banned) {
        //切割
        List<String> words = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paragraph.length(); i++) {
            char c = paragraph.charAt(i);
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                sb.append(c);
            } else {
                if (sb.length() != 0) {
                    words.add(sb.toString());
                    sb = new StringBuilder();
                }
            }
        }
        if (sb.length() != 0) {
            words.add(sb.toString());

        }

        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) {
            String s = word.toLowerCase();

            int flag = 1;
            for (int i = 0; i < banned.length; i++) {

                if (s.equals(banned[i])) {
                    flag = 0;
                    break;
                }
            }
            if (flag == 1) {
                if (!map.containsKey(s))
                    map.put(s, 1);
                else map.put(s, map.get(s) + 1);
            }

        }
        int max = 0;
        String k = "";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                k = entry.getKey();
            }
        }
        return k;

    }

    public int[] findErrorNums(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i + 1 < nums.length; i++) {
            if (nums[i] == nums[i + 1]) {
                return new int[]{nums[i], nums[i] + 1};
            }
        }
        return null;

    }

    public List<String> commonChars(String[] A) {
        List<String> result = new ArrayList<String>();
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < A[0].length(); i++) {
            char c = A[0].charAt(i);
            if (!map.containsKey(c)) {
                map.put(c, 1);
            } else map.put(c, map.get(c) + 1);
        }
        for (int i = 1; i < A.length; i++) {
            HashMap<Character, Integer> map1 = new HashMap<>();
            for (int j = 0; j < A[i].length(); j++) {
                char c = A[i].charAt(i);
                if (!map1.containsKey(c)) {
                    map1.put(c, 1);
                } else map1.put(c, map.get(c) + 1);
            }
            for (Map.Entry entry : map.entrySet()) {
                if (!map1.containsKey(entry.getKey())) {
                    map.remove(entry.getKey());
                } else if (map1.get(entry.getKey()) < (int) entry.getValue()) {
                    map.put((Character) entry.getKey(), map1.get(entry.getKey()));
                }
            }
        }
        for (Map.Entry entry : map.entrySet()) {
            for (int i = 0; i < (int) entry.getValue(); i++) {
                result.add(entry.getKey().toString());
            }
        }
        return result;


    }

    int AnstraverselongestUnivaluePath = 0;

    public int longestUnivaluePath(TreeNode root) {

        longnode(root);
        return AnstraverselongestUnivaluePath;

    }

    int longnode(TreeNode root)//以当前节点为根的最长路径
    {
        if (root == null) return 0;
        int l = longnode(root.left);
        int r = longnode(root.right);
        if (root.left != null && root.left.val == root.val
                && root.right != null && root.right.val == root.val)//左右都相同
        {
            int t = 2 + l + r;
            AnstraverselongestUnivaluePath = Math.max(AnstraverselongestUnivaluePath, t);

        }
        int lmax = 0;
        int rmax = 0;
        if (root.left != null && root.left.val == root.val) {
            lmax = 1 + l;
        }
        if (root.right != null && root.right.val == root.val) {
            rmax = r + 1;
        }
        int t = Math.max(lmax, rmax);
        AnstraverselongestUnivaluePath = Math.max(AnstraverselongestUnivaluePath, t);
        return t;


    }


    Stack<Integer> s = new Stack<>();
    int n1 = 0, n2 = 1, n3 = 0;

    public int fib(int N) {
        if (N == 0) return 0;
        if (N == 1) return 1;
        for (int i = 2; i <= N; i++) {

            n3 = n1 + n2;
            n1 = n2;
            n2 = n3;
        }

        return n3;

    }

    public int countCharacters(String[] words, String chars) {
        int sum = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : chars.toCharArray()) {
            if (map.containsKey(c)) map.put(c, map.get(c) + 1);
            else map.put(c, 1);

        }
        for (int i = 0; i < words.length; i++) {
            HashMap<Character, Integer> mapt = new HashMap<>();
            for (char c : words[i].toCharArray()) {
                if (mapt.containsKey(c)) mapt.put(c, mapt.get(c) + 1);
                else mapt.put(c, 1);
            }
            int flag = 0;
            for (Map.Entry<Character, Integer> entry : mapt.entrySet()) {
                if (map.containsKey(entry.getKey())) {
                    if (map.get(entry.getKey()) < entry.getValue()) {
                        //不符合 下个单词
                        flag = 1;
                        break;

                    }
                } else {
                    flag = 1;
                    break;

                }

            }
            if (flag == 0)
                sum += words[i].length();
        }
        return sum;
    }

    public boolean threeConsecutiveOdds(int[] arr) {
        int count = 0;//
        if (arr[0] % 2 == 1) count++;
        if (arr[1] % 2 == 1) count++;
        else count = 0;
        for (int i = 0; i + 2 < arr.length; i++) {
            if (arr[i + 2] % 2 == 1) {
                count++;
                if (count == 3) return true;
            } else count = 0;

        }
        return false;

    }

    public int[] sortArrayByParity(int[] A) {
        int[] B = new int[A.length];
        int l = 0;
        int r = A.length - 1;
        for (int i = 0; i < A.length; i++) {
            if (A[i] % 2 == 0) {
                B[l] = A[i];
                l++;
            } else {
                B[r] = A[i];
                r--;
            }

        }
        return B;

    }

    public static int findComplement(int num) {
        int n = num;
        int mask = 1;
        num /= 2;
        while (num > 0) {
            num /= 2;
            mask = 2 * mask + 1;
        }
        return mask ^ n;

    }

    public int minDeletionSize(String[] A) {
        //不符合非降序就删
        int D = 0;

        for (int j = 0; j < A[0].length(); j++) {
            for (int i = 0; i + 1 < A.length; i++) {
                if (j >= A[0].length()) break;

                if (A[i].charAt(j) > A[i + 1].charAt(j)) {
                    D++;
                    j++;
                    //下一列
                }
            }

        }
        return D;


    }

    public boolean uniqueOccurrences(int[] arr) {

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : arr
        ) {
            if (map.containsKey(i)) map.put(i, map.get(i) + 1);
            else map.put(i, 1);
        }
        HashSet<Integer> s = new HashSet<>();
        for (int i : map.values()) {
            if (s.contains(i)) return false;
            else s.add(i);

        }
        return true;
        /*
        int[] t=new int[map.size()];
        int n=0;
        for (int i:map.values())
        {
            t[n]=i;
            n++;

        }
        Arrays.sort(t);
        for (int i = 0; i+1 <t.length; i++)
        {
            if(t[i]==t[i+1])return false;
        }
        return true;
        */


    }

    public int[] diStringMatch(String S) {
        int[] result = new int[S.length() + 1];
        int l = 0;
        int r = S.length();
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == 'I') {
                result[i] = l;
                l++;
            } else {
                result[i] = r;
                r--;
            }
        }
        result[S.length()] = l;
        return result;


    }

    public int titleToNumber(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            sum = sum * 26 + s.charAt(i) - 'A' + 1;

        }
        return sum;

    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    //TreeNode node = new TreeNode(0);
    TreeNode p = node;

    public TreeNode increasingBST(TreeNode root) {
        if (root == null) return null;

        increasingBST(root.left);
        //
        node.right = root;
        node = node.right;
        increasingBST(root.right);

        return p.right;
    }


    public String reverseWords(String s) {
        int i = -1;//第一个空格
        int j = 0;//第二个空格
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < s.length(); k++) {
            if (s.charAt(k) == ' ') {
                j = k;
                //反转
                StringBuilder sb1 = new StringBuilder();
                for (int n = i + 1; n < j; n++) {
                    sb1.append(s.charAt(n));
                }
                sb.append(sb1.reverse().toString());
                i = j;
                sb.append(' ');

            }
        }
        j = s.length();
        StringBuilder sb1 = new StringBuilder();
        for (int n = i + 1; n < j; n++) {
            sb1.append(s.charAt(n));
        }
        sb.append(sb1.reverse().toString());
        return sb.toString();


    }

    public String generateTheString(int n) {
        StringBuilder sb = new StringBuilder();
        if (n % 2 == 0) {
            for (int i = 0; i < n - 1; i++) {
                sb.append('a');
            }
            sb.append('b');
            return sb.toString();
        } else {
            for (int i = 0; i < n; i++) {
                sb.append('a');
            }
            return sb.toString();
        }

    }

    static void qsort(int[] nums) {
        quicksort(nums, 0, nums.length - 1);
    }

    static void quicksort(int[] nums, int begin, int end) {
        if (begin < end) {
            int mid = partition(nums, begin, end);
            quicksort(nums, begin, mid - 1);
            quicksort(nums, mid + 1, end);
        }

    }

    static int partition(int[] nums, int begin, int end) {
        if (begin < end) {
            int r = nums[end];
            int i = begin;//最左侧大数位置
            int j = begin;
            while (j < end) {
                if (nums[j] <= r)//比标尺小
                {
                    int t = nums[j];
                    nums[j] = nums[i];
                    nums[i] = t;
                    i++;

                }

                j++;
            }
            int t = nums[i];
            nums[i] = r;
            nums[end] = t;
            return i;
        } else return -1;

    }


}

