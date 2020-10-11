package com.my.day07.demo10ArrayListClass;

import java.util.ArrayList;
import java.util.List;

/*
    java.util.List 是 java.util.ArrayList 所实现的接口
 */
public class DemoInterface {
    public static void main(String[] args) {

        // 左边是接口名，右边是实现类名称
        List<String> list = new ArrayList<>();
        List<String> result = addName(list);

        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }

    public static List<String> addName(List<String> list){
        list.add("迪丽热巴");
        list.add("古力娜扎");
        list.add("玛尔扎哈");
        list.add("沙扬娜拉");
        return list;
    }
}
