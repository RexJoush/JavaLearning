package com.my.day09.demo03JDK9;

import java.util.HashMap;
import java.util.Scanner;

/*
    练习，计算一个字符串中每个字符的个数
 */
public class MapTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<Character,Integer> map = new HashMap<>();
        Integer times = 0;

        for (char c : sc.next().toCharArray()){
            if (map.containsKey(c)){
                times = map.get(c);
                map.put(c,++times);
            }
            else {
                map.put(c,1);
            }
        }

        System.out.println(map);


    }
}
