package com.dpz.dataStructure;


public class ListNode {
    public int val;
    public ListNode next;
    public ListNode pre;

    public ListNode() {
    }

    public ListNode(int x) {
        val = x;
    }

    public ListNode(int[] nums) {
        assert nums.length > 0;
        this.val = nums[0];
        ListNode node = this;
        for (int i = 1; i < nums.length; i++) {
            node.next = new ListNode(nums[i]);
            node.next.pre = node;
            node = node.next;
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode node = this;
        while (node != null) {
            sb.append(node.val).append("->");
            node = node.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //test
        ListNode head = new ListNode(new int[]{1, 2, 3, 6, 5, 3});
        System.out.println(head);
    }
}
