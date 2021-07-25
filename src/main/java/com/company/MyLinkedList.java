package com.company;
class MyLinkedList {

    int val;
    MyLinkedList next;
    /** Initialize your data structure here. */
    public MyLinkedList() {
        //无参数的时候是空的
        next=null;
        val=2000;//1-1000

    }
    public MyLinkedList(int x) {
        next=null;
        val=x;

    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
//    public int get(int index) {
//        if(index<0)return -1;
//        if(this.val==2000)return -1;
//        MyLinkedList node=this;
//
//        while(node!=null&&index!=0)
//        {
//            index--;
//            node=node.next;
//        }
//        if(node!=null)
//        return node.val;
//        else return -1;
//
//    }
    public int get(int index) {
        if(index<0)return -1;
        if(this.val==2000)return -1;
        if(index==0)return val;
        return this.next.get(index-1);

    }
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        if(this.val==2000){this.val=val;return;}
        //head作为原来的this
        MyLinkedList head=new MyLinkedList(this.val);
        head.next=this.next;
        this.val=val;
        this.next=head;

        //this=head;//不能这样用    java改变this指向
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        if(this.val==2000){this.val=val;return;}

        MyLinkedList pre=new MyLinkedList();
        pre.next=this;
        while(pre.next!=null)
        {
            pre=pre.next;

        }
        pre.next=new MyLinkedList(val);
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if(index<=0)addAtHead(val);
        MyLinkedList pre=new MyLinkedList();
        MyLinkedList node=this;
        pre.next=node;

        while(node!=null&&0!=index)
        {
            node=node.next;
            pre=pre.next;
            index--;

        }
        //
        if(node==null)
        {
            if(0==index)//
            {
                pre.next=new MyLinkedList(val);
            }
            //else 不加
        }
        else{
            pre.next=new MyLinkedList(val);
            pre.next.next=node;
        }

    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if(index<0)return;
        if(index==0)//删除头
        {
            if(this.next!=null)
            {
                this.val=this.next.val;
                this.next=this.next.next;
            }
            else
            {
                this.val=2000;
            }
            return;
        }
        MyLinkedList pre=new MyLinkedList();
        pre.next=this;

        while(pre.next!=null&&0!=index)
        {
            pre=pre.next;
            index--;

        }
        if(pre.next==null)return;
        pre.next=pre.next.next;
        //this改为

    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */