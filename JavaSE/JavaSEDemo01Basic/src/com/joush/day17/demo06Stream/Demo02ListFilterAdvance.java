package com.joush.day17.demo06Stream;

import java.util.ArrayList;

/*
    使用 Stream 流进行操作，jdk1.8 之后
 */
public class Demo02ListFilterAdvance {
    public static void main(String[] args) {
        // 创建集合
        ArrayList<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("张三丰");
        list.add("赵敏");
        list.add("张亮");
        list.add("张三");
        list.add("赵丽颖");
        list.add("迪丽热巴");

        /*
            优化之前的操作
            1.获取张姓的人，添加到新集合中
            2.获取张姓名字有三个字的，添加到新集合中
            3.打印输出
         */
        list.stream()
                .filter(name -> name.startsWith("张"))
                .filter(name -> name.length() == 3)
                .forEach(name -> System.out.println(name));

    }
}
