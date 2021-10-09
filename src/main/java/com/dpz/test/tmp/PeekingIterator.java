package com.dpz.test.tmp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class PeekingIterator implements Iterator<Integer> {

    List<Integer> list=new ArrayList<>();
    int index=0;
    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        while(iterator.hasNext()){
            list.add(iterator.next());
        }

    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return list.get(index);
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        return list.get(index++);
    }

    @Override
    public boolean hasNext() {
        return index<list.size();
    }
}