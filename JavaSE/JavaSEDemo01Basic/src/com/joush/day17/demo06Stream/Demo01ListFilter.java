package com.joush.day17.demo06Stream;

import java.util.ArrayList;

/*
    对集合的数据进行过滤
 */
public class Demo01ListFilter {
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
            获取张姓的人，添加到新集合中
         */
        ArrayList<String> listA = new ArrayList<>();
        for (String s : list) {
            if (s.startsWith("张")){
                listA.add(s);
            }
        }
        /*
            获取张姓名字有三个字的，添加到新集合中
         */
        ArrayList<String> listB = new ArrayList<>();
        for (String s : listA) {
            if (s.length() == 3){
                listB.add(s);
            }
        }

        // 打印输出
        for (String s : listB) {
            System.out.println(s);
        }

    }
}
