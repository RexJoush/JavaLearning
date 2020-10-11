package com.my.day08.demo04HashSet;

import java.util.HashSet;

/*
    Set 集合不允许重复元素的原理
 */
public class Demo03HashSetSaveString {
    public static void main(String[] args) {
        // 创建 HashSet 集合
        HashSet<String> set = new HashSet<>();
        String s1 = new String("abc");
        String s2 = new String("abc");
        set.add(s1);
        set.add(s2);
        set.add("重地");
        set.add("通话");
        set.add("abc");
        System.out.println(set); // [重地, 通话, abc]
    }
}
