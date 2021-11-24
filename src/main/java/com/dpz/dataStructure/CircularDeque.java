package com.dpz.dataStructure;

//循环双端队列
class CircularDeque {

    int[] nums;
    int head;
    int tail;
    int len;
    int size=0;
    //head-->   <--tail
    //<--tail   head-->
    //相等时 还有一个位置可用
    public CircularDeque(int k) {
        nums = new int[k];
        head = 0;
        tail = k - 1;
        len = k;
    }

    public boolean insertFront(int value) {
        if (isFull()) return false;
        nums[head] = value;
        head = (head + 1) % len;
        size++;
        return true;
    }

    public boolean insertLast(int value) {
        if (isFull()) return false;
        nums[tail] = value;
        tail = (len + tail - 1) % len;
        size++;
        return true;
    }

    public boolean deleteFront() {
        if (isEmpty()) return false;
        head = (len + head - 1) % len;
        size--;
        return true;
    }

    public boolean deleteLast() {
        if (isEmpty()) return false;
        tail = (tail + 1) % len;
        size--;
        return true;

    }

    public int getFront() {
        if (isEmpty()) return -1;
        return nums[(len+head-1)%len];
    }

    public int getRear() {
        if (isEmpty()) return -1;
        return nums[(tail+1)%len];
    }

    public boolean isEmpty() {
        return size==0;
    }

    public boolean isFull() {
        return size==len;
    }
}

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * MyCircularDeque obj = new MyCircularDeque(k);
 * boolean param_1 = obj.insertFront(value);
 * boolean param_2 = obj.insertLast(value);
 * boolean param_3 = obj.deleteFront();
 * boolean param_4 = obj.deleteLast();
 * int param_5 = obj.getFront();
 * int param_6 = obj.getRear();
 * boolean param_7 = obj.isEmpty();
 * boolean param_8 = obj.isFull();
 */