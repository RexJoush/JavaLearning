package com.my.day01.demo03ArrayListPrictise;

/*
    题目：
    一个大集合存入20个随机数字，然后筛选其中的偶数元素，存入小集合。
    使用自定义方法来实现筛选
 */

import java.util.ArrayList;
import java.util.Random;

public class Demo04ArrayListReturn {
    public static void main(String[] args) {
        ArrayList<Integer> listBig = new ArrayList<>();
        ArrayList<Integer> listResult = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            listBig.add(random.nextInt(100));
        }
        listResult = getSmallList(listBig);
        System.out.println("偶数共有：" + listResult.size() + "个");
        System.out.println(listResult);
    }

    public static ArrayList<Integer> getSmallList(ArrayList<Integer> list){
        ArrayList<Integer> listSmall = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) % 2 == 0){
                listSmall.add(list.get(i));
            }
        }
        return  listSmall;
    }
}
