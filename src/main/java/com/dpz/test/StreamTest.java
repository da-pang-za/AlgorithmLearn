package com.dpz.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class StreamTest {
    public static void main(String[] args) throws IOException {
//        String[] dictionary = new String[]{"abc", "bac", "cab", "ab", "ba"};
//        int minGroupSize = 3;
//        try (Stream<String> words = Arrays.stream(dictionary)) {
//            words.collect(Collectors.groupingBy(word -> alphabetize(word)))
//                    .values().stream()
//                    .filter(group -> group.size() >= minGroupSize)
//                    .forEach(g -> System.out.println(g.size() + ": " + g));
        int[] nums = new int[]{9, 8, 4, 4, 1, 7};

        System.out.println(Arrays.stream(nums).sorted().map(e -> e*e).boxed()
                .collect(
                    Collectors.toMap(e->e,e->e-1,(a,b)->a)));

        List<Integer> list = List.of(6, 3, 5, 1, 9, 6);
        list.forEach(
                e-> System.out.println(e*e)
        );
//        Integer[] arr = list.stream().sorted().filter(p->p>(p-3)*(p-3)).toArray(Integer[]::new);
//        System.out.println(Arrays.toString(arr));
    }


    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
