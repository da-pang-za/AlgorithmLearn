package com.dpz.leetcode;

import java.util.*;

class AllOne {
    public static void main(String[] args) {
        AllOne allOne=new AllOne();
        allOne.inc("hello");
        allOne.dec("hello");
        allOne.dec("hello");
        allOne.inc("leet");
        allOne.getMinKey();

    }
    static class Node{
        String key;
        int val;
        Node pre;
        Node next;
        Node(){}
        Node(String k,int v){
            key=k;
            val=v;
        }
    }
    //key node
    HashMap<String,Node>keyMap;
    //val node
    //每个val的最前面的元素
    HashMap<Integer,Node>valMap;
    HashMap<Integer,Node>tailMap;
    //虚拟头尾
    Node head;//最大的
    Node tail;//最小的
    /** Initialize your data structure here. */
    public AllOne() {
        head=new Node();
        tail=new Node();
        head.next=tail;
        tail.pre=head;
        keyMap=new HashMap<>();
        valMap=new HashMap<>();
        tailMap=new HashMap<>();
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        if(keyMap.containsKey(key)){
            Node node=keyMap.get(key);
            //如果是队尾 看前一个是不是队尾
            if(node==tailMap.get(node.val)){
                if(node.pre.val==node.val){
                    tailMap.put(node.val,node.pre);
                }
                else tailMap.remove(node.val);

            }
            //不是队头
            if(node!=valMap.get(node.val)){

                //从原位置 拆除
                remove(node);
                //插到val的队头

                add(valMap.get(node.val).pre,node);
            }
            //是队头需要更新队头
            else{
                if(node.next.val==node.val){
                    valMap.put(node.val,node.next);
                }
                else{
                    valMap.remove(node.val);
                }
            }
            node.val++;
            //新队尾 队头
            tailMap.put(node.val,node);
            if(node.pre.val!=node.val){
                valMap.put(node.val,node);
            }

        }
        else{
            Node node=new Node(key,1);
            keyMap.put(key,node);
            //插到队尾
            add(tail.pre,node);
            if(!valMap.containsKey(1)){
                valMap.put(1,node);
            }
            tailMap.put(1,node);
        }

    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        if(keyMap.containsKey(key)){

            Node node=keyMap.get(key);
            //如果是队头
            if(valMap.get(node.val)==node){
                //更新队头
                if(node.next.val==node.val){
                    valMap.put(node.val,node.next);
                }
                else{
                    valMap.remove(node.val);
                }

            }
            //如果不是队尾   放到队尾
            if(tailMap.get(node.val)!=node){
                remove(node);
                add(tailMap.get(node.val),node);

            }
            //是队尾更新队尾
            else{
                if(node.pre.val==node.val){
                    tailMap.put(node.val,node.pre);
                }
                else{
                    tailMap.remove(node.val);
                }
            }
            node.val--;
            //新队头  队尾
            valMap.put(node.val,node);
            if(node.next.val!=node.val){
                tailMap.put(node.val,node);
            }
            if(node.val==0){
                remove(node);
                keyMap.remove(node.key);
                tailMap.remove(0);
                valMap.remove(0);

            }

        }

    }
    void remove(Node node){
        node.next.pre=node.pre;
        node.pre.next=node.next;
        node.next=null;
        node.pre=null;
    }
    void add(Node pos ,Node node){
        pos.next.pre=node;
        node.next=pos.next;
        node.pre=pos;
        pos.next=node;
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        if(head.next==tail)return "";
        return head.next.key;

    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        if(head.next==tail)return "";
        return tail.pre.key;

    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
