package com.dpz.dataStructure;

//注意，跳表中可能存在多个相同的值
class Skiplist {
    //至少k+1层的概率 (1-p)^k  至少k+2层的概率 (1-p)^(k+1)    做除法：1-p   这里取3/4
    private static final float SKIPLIST_P = 0.25f;
    private static final int MAX_LEVEL = 32;//最大层数

    Node head;

    static class Node{
        int val;
        Node pre; //最低层的前一个
        Node[] next; //每一层的下一个

        public Node(int val){
            this.val = val;
            next = new Node[randomLevel()];
        }

        public Node(int val, int size){
            this.val = val;
            next = new Node[size+1];
        }

        private int randomLevel() {
            int level = 1;
            while (Math.random() < SKIPLIST_P && level < MAX_LEVEL) level++;
            return level;
        }
    }

    public Skiplist() {
        head = new Node(-1, MAX_LEVEL);
    }
    //找<=target的最后一个
    private Node searchNode(int target){
        if(isEmpty()) return head;
        Node pre = head;
        //尽量横跳
        for(int i=MAX_LEVEL;i>=0;i--) //垂直向下
            while(pre.next[i]!=null && pre.next[i].val<=target)//尽量横跳
                pre = pre.next[i];
        return pre;
    }

    public boolean search(int num) {
        Node p = searchNode(num);
        return p.val == num;
    }
    //0 <= num, target <= 20000
    //注意，跳表中可能存在多个相同的值，你的代码需要处理这种情况
    public void add(int num) {
        Node node = new Node(num);
        node.pre = searchNode(num);
        Node pre = node.pre;
        //核心代码
        for(int i = 0; i<node.next.length; i++){
            while(pre.next.length<i+1) pre = pre.pre;//往前找 最近的有第i层的
            if(i==0 && pre.next[i]!=null) pre.next[i].pre = node;//最后一层连pre
            node.next[i] = pre.next[i];
            pre.next[i] = node;
        }
    }
    //在跳表中删除一个值，如果 num 不存在，直接返回false. 如果存在多个 num ，删除其中任意一个即可
    public boolean erase(int num) {
        if(isEmpty()) return false;
        Node node = searchNode(num);
        if(node.val !=num) return false;
        //连上左右
        for(int i = 0; i<node.next.length; i++){
            Node pre = node.pre;
            while(pre.next.length<i+1) pre = pre.pre;
            if(i==0 && node.next[i]!=null) node.next[i].pre = pre;
            pre.next[i] = node.next[i];
        }
        return true;
    }

    private boolean isEmpty(){
        return head.next[0]==null;
    }
}

