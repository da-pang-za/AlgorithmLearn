package com.dpz.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Tips {

    public static void main(String[] args) {
        //快速创建带有元素的list  可以修改   java 8
        List<String> strings = Stream.of("how", "to", "do", "in", "java")
                .collect(Collectors.toList());
        List<Integer> list = Stream.of(2, 6, 1, 4, 8, 7, 9).collect(Collectors.toList());
        System.out.println(list);
        //如果用IntStream需要boxed装箱
        System.out.println(IntStream.of( 4, 8, 7, 9).boxed().collect(Collectors.toList()));
        //把List<Integer>转换成int[](Java 8)
        int[] newNum = list.stream().mapToInt(Integer::intValue).toArray();
        //todo int[] -> List<Integer>

    }
}
