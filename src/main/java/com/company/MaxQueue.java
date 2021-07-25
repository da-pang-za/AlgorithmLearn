package com.company;

import java.util.Deque;
import java.util.LinkedList;

class MaxQueue {
    Deque<Integer> maxQ;
    Deque<Integer>queue;
    public MaxQueue() {
        maxQ=new LinkedList<>();
        queue=new LinkedList<>();
    }

    public int max_value() {
        if(maxQ.isEmpty())return -1;
        return maxQ.getFirst();

    }

    public void push_back(int value) {
        queue.offer(value);
        if(maxQ.isEmpty()||maxQ.getFirst()>value){
            maxQ.offer(value);
        }
        else {
            maxQ.clear();
            maxQ.offer(value);
        }

    }

    public int pop_front() {
        if(queue.isEmpty())return -1;
        if(queue.peek().equals(maxQ.peek()))maxQ.poll();
        return queue.getFirst();

    }
}

/**
 * Your MaxQueue object will be instantiated and called as such:
 * MaxQueue obj = new MaxQueue();
 * int param_1 = obj.max_value();
 * obj.push_back(value);
 * int param_3 = obj.pop_front();
 */
