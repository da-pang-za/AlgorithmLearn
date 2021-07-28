package com.dpz;

import java.util.HashMap;
class LRUCache {

    public static void main(String[] args) {
        LRUCache lru=new LRUCache(2);
        lru.put(1,1);
        lru.put(2,2);
        lru.get(1);
        lru.put(3,3);
        lru.get(2);

        lru.put(4,4);
        lru.get(1);
        lru.get(3);
        lru.get(4);



    }
    static class Node{
        int key;
        int val;
        Node pre;
        Node next;
        Node(int k,int v){
            key=k;
            val=v;
        }
    }
    Node head;
    Node tail;
    int capacity;
    int cur=0;//当前容量
    HashMap<Integer, Node>map;
    public LRUCache(int capacity) {
        head=null;
        tail=null;
        this.capacity=capacity;
        map=new HashMap<>();

    }

    public int get(int key) {
        if(map.containsKey(key)){
            Node node= map.get(key);
            //把此节点放到头部
            if(node!=head){//注意
                delete(node);
                add2Head(node);
            }
            return node.val;

        }
        return -1;

    }

    private void add2Head(Node node) {
        if(head==null){
            head=node;
            tail=node;
        }
        else {
            head.next=node;
            node.pre=head;
            head=node;
        }

    }
    //已经保证存在此节点
    private void delete(Node node) {
        if(node==tail){
            node=tail.next;
            node.pre=null;
            tail.next=null;
            tail=node;
        }
        else {
            node.pre.next=node.next;
            node.next.pre=node.pre;
            node.pre=null;
            node.next=null;
        }

    }
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node= map.get(key);
            //把此节点放到头部
            node.val=value;
            if(node!=head){//注意
                delete(node);
                add2Head(node);
            }
        }
        else{
            //添加
            Node node= new Node(key, value);
            //加到链表和HashMap中
            add2Head(node);
            map.put(key,node);
            if(cur==capacity){
                map.remove(tail.key);
                delete(tail);
            }
            else cur++;
        }

    }


}