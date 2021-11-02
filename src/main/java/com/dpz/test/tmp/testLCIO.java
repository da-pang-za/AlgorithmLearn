//package com.dpz.test.tmp;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
//public class testLCIO {
//
//
//    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        String input = "[\"SummaryRanges\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\",\"addNum\",\"getIntervals\"]\n" +
//                "[[],[49],[],[97],[],[53],[],[5],[],[33],[],[65],[],[62],[],[51],[],[100],[],[38],[],[61],[],[45],[],[74],[],[27],[],[64],[],[17],[],[36],[],[17],[],[96],[],[12],[],[79],[],[32],[],[68],[],[90],[],[77],[],[18],[],[39],[],[12],[],[93],[],[9],[],[87],[],[42],[],[60],[],[71],[],[12],[],[45],[],[55],[],[40],[],[78],[],[81],[],[26],[],[70],[],[61],[],[56],[],[66],[],[33],[],[7],[],[70],[],[1],[],[11],[],[92],[],[51],[],[90],[],[100],[],[85],[],[80],[],[0],[],[78],[],[63],[],[42],[],[31],[],[93],[],[41],[],[90],[],[8],[],[24],[],[72],[],[28],[],[30],[],[18],[],[69],[],[57],[],[11],[],[10],[],[40],[],[65],[],[62],[],[13],[],[38],[],[70],[],[37],[],[90],[],[15],[],[70],[],[42],[],[69],[],[26],[],[77],[],[70],[],[75],[],[36],[],[56],[],[11],[],[76],[],[49],[],[40],[],[73],[],[30],[],[37],[],[23],[]]";
//
//        String[] s = input.split("\n");
//        String[] opts = s[0].substring(1, s[0].length() - 1).split(",");
//        String[] vals = s[1].substring(1, s[1].length() - 1).split(",");
//        assert opts.length == vals.length;
//        System.out.println();
//        SummaryRanges summaryRanges = new SummaryRanges();
//        for (int i = 1; i < opts.length; i++) {
//            String methodName = opts[i].substring(1, opts[i].length() - 1);
//
//            String[] params = vals[i].substring(1, vals[i].length() - 1).split(",");
//            Integer[][] p = new Integer[params.length][];
//            for (int j = 0; j < params.length; j++) {
//                String[] ps = params[j].split(",");
//                p[j] = new Integer[ps.length];
//                for (int k = 0; k < ps.length; k++) {
//                    p[j][k] = Integer.parseInt(ps[k]);
//                }
//            }
//            Method method = summaryRanges.getClass().getDeclaredMethod(methodName, int.class);
//            method.invoke(summaryRanges,  p.length>0?p[0]:null);
//            System.out.println("invoke");
//        }
//
//    }
//
//}
//
//
