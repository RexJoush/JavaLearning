package com.joush.day01.demo03ArrayListPrictise;

import java.util.ArrayList;
import java.util.Random;

/*
    题目：
    生成 6 个 1-33 之间的随机整数，添加到集合，并遍历集合。
 */
public class Demo01ArrayListRandom {
    public static void main(String[] args) {
        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            list.add(random.nextInt(33)+1);
        }

        // 遍历集合
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

    }
}
