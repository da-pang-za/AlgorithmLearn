package com.dpz.test;

public class Debug {
    /**
     * 生成main函数
     */
    public static void main(String[] args) {
        genMain("[[4,1,0,4,0],[1,0,4,0,5],[1,3,0,4,1],[4,4,0,4,0]]");
    }


    static void genMain(String datas) {
        String[] ss = datas.split("\n");
        StringBuilder data = new StringBuilder();
        for (String s : ss) {
            s = s.replaceAll("\n", ",");
            s = s.replaceAll("\\[", "{");
            s = s.replaceAll("]", "}");
            int idx = s.indexOf("{{");
            String type = null;
            if (s.contains("\"")) type = "String";
            else type = "int";
            //二维数组
            if (idx != -1) {
                s = s.substring(0, idx) + "new " + type + "[][]" + s.substring(idx);
            }
            //一维数组
            else {
                idx = s.indexOf("{");
                if (idx != -1) s = s.substring(0, idx) + "new " + type + "[]" + s.substring(idx);
            }
            data.append(s).append(',');

        }
        data.deleteCharAt(data.length() - 1);


        System.out.println(
                "    public static void main(String[] args) {\n" +
                        "        System.out.println(new Solution().fun(" + data + "));\n" +
                        "    }"
        );
    }
}
