package com.dpz.template;

import java.util.*;

public class SortAgrm10 {
    public static void main(String[] args) {
        test();
    }

    static void test() {
        SortAgrm10 sortAgrm10 = new SortAgrm10();
        for (int j = 0; j < 100000; j++) {
            int[] nums = new int[30];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = (int) (100 * Math.random());
            }
            int[] nums2 = nums.clone();
            Arrays.sort(nums);
            //*****************************//
            sortAgrm10.quickSort(nums2);

            if (!Arrays.equals(nums, nums2))
                System.out.println(Arrays.toString(nums2));
        }
    }

    //冒泡  稳定
    static void Bubble(int[] nums) {
        //每次产生一个最大的数 放在末尾
        for (int i = nums.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }

            }
        }
    }

    //快速排序   冒泡升级  不稳定
    //引入一个pivot  大的放在一边  小的放在另一边   对每一边进行相同的操作
    void quickSort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
    }


    static Random random = new Random();

    static void quickSort(int[] nums, int left, int right) {
        if (left >= right) return;
        int l = left - 1, r = right + 1;
        //选一个轴
        int pivot = left + random.nextInt(right - left + 1);
//        pivot = left + right >> 1;
        int v = nums[pivot];
        while (l < r) {
            do l++; while (nums[l] < v);
            do r--; while (nums[r] > v);
            if (l < r) swap(nums, l, r);
        }
        quickSort(nums, left, r);
        quickSort(nums, r + 1, right);
    }

    //快速选择 排序后下标k位置的数
    static int quick(int[] nums, int left, int right, int k) {
        if (left >= right) return nums[left];
        int l = left - 1, r = right + 1;
        //选一个轴
        int pivot = left + random.nextInt(right - left + 1);
//        pivot = left + right >> 1;
        int v = nums[pivot];

        while (l < r) {
            do l++; while (nums[l] < v);
            do r--; while (nums[r] > v);
            if (l < r) swap(nums, l, r);
        }
        if (r >= k) return quick(nums, left, r, k);
        else return quick(nums, r + 1, right, k);
    }


    //选择  选择最小的 放在最前面  不稳定
    static void SelectSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int min = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[min]) {
                    min = j;
                }
            }
            swap(nums, i, min);
        }
    }

    //堆排序  选择排序的升级版   通过堆的方式选择最大元素 不稳定
    //大顶堆  一棵完全二叉树  最大元素是第一个  拿下来放到最后  在此元素之前维护大顶堆
    //int index = nums.length / 2 - 1;//最后一个有子节点的节点   //2个  3个元素 都返回下标0
    static void HeapSort(int[] nums) {

        //1.构建大顶堆   复杂度O(N)   算法导论p88
        build(nums);

        //2.排序  复杂度O(NlogN)
        //i为要交换的位置   位置0不用交换所以是i>0
        for (int i = nums.length - 1; i > 0; i--) {
            swap(nums, 0, i);
            //维护大顶堆
            heapify(nums, 0, i);
        }
    }

    //建堆
    static private void build(int[] nums) {
        //找到最后一个有子节点的位置
        // 这里必须自底向上 反过来是错的
        //因为heapify只会把当前节点换为两个子节点中更大的，但子节点下方可能还有更大的值

        //如果从上到下：
        //1->2->3 :  2->3->1  错误
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }
    }

    /**
     * 堆调整
     *
     * @param len 堆的长度  超过的是排序好的 不动
     */
    static void heapify(int[] nums, int index, int len) {

        while (index * 2 + 1 < len) { //首先判断是否有子节点  有子节点才能下沉
            int son = index * 2 + 1;
            //子节点较大的
            if (son + 1 < len && nums[son] < nums[son + 1]) son++;
            //交换 大的放上面
            if (nums[index] < nums[son]) {
                swap(nums, index, son);
                //继续下沉
                index = son;
            }
            //返回
            else break;
        }

    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    //插入排序 稳定   插入前面的有序部分
    static void insertSort(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        for (int i = 1; i <= end; i++) {

            if (nums[i - 1] > nums[i]) {
                int t = nums[i];
                int index = i - 1;
                while (index >= start && t < nums[index]) {
                    nums[index + 1] = nums[index];
                    index--;

                }
                //插入
                nums[index + 1] = t;
            }
        }

    }

    //shell   改良版插入
    static void shell(int[] nums) {
        shellSort(nums, 0, nums.length - 1);
    }

    static void shellSort(int[] nums, int start, int end) {
        int increment = end - start + 1;
        while (increment > 1) {
            increment = increment / 3 + 1;
            for (int i = increment + start; i <= end; i++) {
                //和前面i-inc比
                if (nums[i] < nums[i - increment]) {
                    //移到前面
                    int t = nums[i];
                    int index = i - increment;
                    while (index >= start && nums[index] > t) {
                        nums[increment + index] = nums[index];
                        index -= increment;
                    }
                    //插入
                    nums[index + increment] = t;
                }
            }

        }
    }


    //归并排序  稳定
    //1. 二路归并
    //左边排序  右边排序

    static int[] mergeHelp;

    static void mergeSort(int[] nums) {
        mergeHelp = new int[nums.length];
        merge(nums, 0, nums.length - 1);
    }

    static void merge(int[] nums, int l, int r) {
        //结束条件
        if (l >= r) return;
        //排序两个子数组
        int mid = (l + r) / 2;
        merge(nums, l, mid);
        merge(nums, mid + 1, r);

        //合并两个有序数组   存到mergeHelp的[l,r]
        int k1 = l, k2 = mid + 1;
        int index = l;
        while (k1 <= mid && k2 <= r) {
            if (nums[k1] < nums[k2]) {
                mergeHelp[index] = nums[k1];
                k1++;
            } else {
                mergeHelp[index] = nums[k2];
                k2++;
            }
            index++;
        }
        //剩余部分

        while (k1 <= mid) {
            mergeHelp[index++] = nums[k1++];
        }

        while (k2 <= r) {
            mergeHelp[index++] = nums[k2++];
        }

        //复制回去
        for (int i = l; i <= r; i++) {
            nums[i] = mergeHelp[i];
        }

    }

    //2. 多路归并
    //

    /**
     * 基数排序 根据每一位排序    要求全为正数 或0
     *
     * @time n*10    (int范围~=10^9一共10位十进制)
     * @space n
     */
    static void baseSort(int[] nums) {
        int n = nums.length;
        int[] tmp = new int[n];
        int[] count = new int[10];//0-9个数
        int bit = 1;
        //低位到高位
        for (int j = 0; j < 10; j++) {//10^0-10^9
            for (int i = 0; i < n; i++) {
                int cur = (nums[i] / bit) % 10;
                count[cur]++;//0-9
            }
            //找到每个数字最后一个位置   //还需要-1
            for (int i = 1; i < 10; i++) {
                count[i] += count[i - 1];
            }
            //放置  大的放后面
            for (int i = n - 1; i >= 0; i--) {
                int cur = (nums[i] / bit) % 10;
                tmp[count[cur]-- - 1] = nums[i];
            }
            int[] t = nums;
            nums = tmp;
            tmp = t;

            bit *= 10;
            count = new int[10];
        }
    }
    //计数排序   对范围内的每个数计数

    //桶排序  给数据范围划分为几个部分  每个部分排序

}
