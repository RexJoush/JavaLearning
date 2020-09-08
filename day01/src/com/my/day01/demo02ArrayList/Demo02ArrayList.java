package com.my.day01.demo02ArrayList;

import java.util.ArrayList;

// 数组长度不可以发生改变，但ArrayList可以随意改变
// 对于ArrayList来说，有一个尖括号 <E> 泛型，可以装统一类型。
// 注意，泛型必须是引用类型，不能是基本类型。

// 注意，对于 ArrayList 集合来说，直接打印出现的是内容，而不是地址值，空内容则就是 []

public class Demo02ArrayList {
    public static void main(String[] args) {
        // 创建了一个ArrayList集合，装String字符串类型
        // 从 jdk1.7+ ，右侧 <> 内容可以省略，但 <> 不可省
        ArrayList<String> list = new ArrayList<>();

        System.out.println(list); // []

        // 向集合添加数据，需要用到 add 方法
        list.add("赵丽颖");
        System.out.println(list); // [赵丽颖]

        list.add("迪丽热巴");
        list.add("古力娜扎");
        list.add("玛尔扎哈");
        System.out.println(list); // [赵丽颖, 迪丽热巴, 古力娜扎, 玛尔扎哈]

    }
}
