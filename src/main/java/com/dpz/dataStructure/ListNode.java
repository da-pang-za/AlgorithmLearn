package com.dpz.dataStructure;


public class ListNode {
    public int val;
    public ListNode next;
    public ListNode(){}
    public ListNode(int x) {
        val = x;
    }
    public static ListNode arr2Node(int[]nums){
        ListNode head=new ListNode(nums[0]);
        ListNode node=head;
        for(int i=1;i<nums.length;i++){
            node.next=new ListNode(nums[i]);
            node=node.next;
        }
        return head;
    }
}
