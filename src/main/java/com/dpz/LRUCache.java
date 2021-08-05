package com.dpz;

import java.util.HashMap;

class LRUCache {
    static class Node{
        int key;
        int val;
        Node next;
        Node pre;
        Node(){}
        Node(int k ,int v){
            key=k;
            val=v;
        }
    }
    int capacity;
    int curSize;
    Node head=new Node();
    Node tail=new Node();
    HashMap<Integer,Node> map=new HashMap<>();
    public LRUCache(int capacity) {
        this.capacity=capacity;
        curSize=0;
        head.next=tail;
        tail.pre=head;
    }

    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        return addTail(key).val;
    }

    public void put(int key, int value) {
        if(!map.containsKey(key)){

            Node node=new Node(key,value);
            //加到tail
            node.next=tail;
            node.pre=tail.pre;
            tail.pre.next=node;
            tail.pre=node;

            map.put(key,node);
            curSize++;
        }
        else{
            addTail(key).val=value;
        }
        if(curSize>capacity){
            curSize--;
            Node node=head.next;
            head.next=node.next;
            node.next.pre=head;
            node.next=node.pre=null;
            map.remove(node.key);
        }
    }
    //拆下来放到尾部
    Node addTail(int key){
        Node node=map.get(key);
        node.pre.next=node.next;
        node.next.pre=node.pre;
        node.next=node.pre=null;
        //加到tail
        node.next=tail;
        node.pre=tail.pre;
        tail.pre.next=node;
        tail.pre=node;
        return node;
    }
}
