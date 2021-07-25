package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class 剑指Offer {
    static 剑指Offer offer=new 剑指Offer();
    public static void main(String[] args) {


        System.out.println(offer.findNthDigit(1000000000));
    }

    //剑指 Offer 64. 求1+2+…+n
    public int sumNums(int n) {
        boolean flag = n > 0 && (n += sumNums(n - 1)) > 0;
        StringBuilder sb = new StringBuilder();
        sb.deleteCharAt(sb.length());
        return n;
    }

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        if (pushed.length != popped.length) return false;
        //弹一个数必须在压这个数之后
        //弹的是栈顶
        int push = 0;
        int pop = 0;
        //如果弹不了就压  否则压栈

        Deque<Integer> stack = new LinkedList<>();
        while (push < pushed.length && pop < popped.length) {
            if (stack.isEmpty() || popped[pop] != stack.getLast()) {
                stack.offer(pushed[push++]);
            } else {
                stack.pollLast();
                pop++;
            }
        }
        if (push < pushed.length) return false;//还有没压栈的
        while (pop < popped.length) {
            if (popped[pop++] != stack.pollLast()) {
                return false;
            }
        }
        return true;

    }

    public int findNthDigit(int n) {
        if(n<10)return n;
        long cur=9;
        int base=9;
        int i=1;
        List<Integer> list=new ArrayList<>();
        while(cur<Integer.MAX_VALUE){
            list.add((int)cur);
            base*=10;i++;
            cur+= (long) i *base;//必须强制转换为long  不然cur超不过int

        }
        list.add(Integer.MAX_VALUE);
        i=0;
        for(;n>list.get(i);i++);
        // i是本段 第一个数字0的个数  如 2  100  i+1本段数字长度

        int k=(int)Math.pow(10,i);
        cur=(n-list.get(i-1)-1)/(i+1);//第几个数字  0-j
        base=i-(n-list.get(i-1)-1)%(i+1);// i-从左到右第几位 0-j
        int num=k+(int)cur;

        for(int c=0;c<base;c++){
            num/=10;
        }
        return num%10;

    }
    //60
//    public double[] dicesProbability(int n) {
//        double base=Math.pow(6,n);
//
//    }
    //45
    public String minNumber(int[] nums) {
        //把数拆成一位  小的放前面  第一个不是0
        List <Integer>list=new ArrayList<>();
        for (int num : nums) {

            while (num>=10){
                list.add(num%10);
                num/=10;
            }
            list.add(num);
        }
        Collections.sort(list);
        StringBuilder sb=new StringBuilder();
        int zeros=0;
        for (Integer num : list) {
            if(num==0){zeros++;}
            else {
                sb.append(num);
                break;
            }
        }
        for(int i=0;i<zeros;i++)sb.append(0);
        for(int i=zeros+1;i<list.size();i++){
            sb.append(list.get(i));
        }
        return sb.toString();


    }

}

//class Node {
//    int val;
//    Node next;
//    Node random;
//
//    public Node(int val) {
//        this.val = val;
//        this.next = null;
//        this.random = null;
//    }
//}
