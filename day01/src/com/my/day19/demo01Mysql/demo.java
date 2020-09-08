package com.my.day19.demo01Mysql;

import java.util.ArrayList;
import java.util.Scanner;

public class demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.next();

        System.out.println(func(s));

    }

    public static int func(String str) {
        int n = Integer.parseInt(str);
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 9;i>1;i--){
            while (n%i==0){
                arrayList.add(i);
                n/=i;
            }
        }
        if (arrayList.size() < 2 ){
            return -1;
        }
        String s = "";
        for (int i = arrayList.size()-1; i>=0; i--){
            s += arrayList.get(i);
        }

        return Integer.parseInt(s);

    }


}
