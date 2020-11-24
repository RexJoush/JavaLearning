package com.joush.proxy;

import java.util.ArrayList;
import java.util.List;

public class FindRoad {
    static List<String> list = new ArrayList<>();
    static int [][] points = new int[][]
            {
            {1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1}};


    public static void main(String[] args) {

        for (int i = 0; i <points.length; i++){
            for (int j = 0; j < points[0].length; j++){
                if (points[i][j] == 1){
                    continue;
                }
                dp(i, j);
                System.out.println(list);
                list.clear();
            }
        }

    }


    public static void dp(int i, int j){

        if (isBorder(i, j)){
            list.add(i + "," + j);
            return;
        }
        if (points[i][j] == 1){
            return;
        }
        list.add(i + "," + j);
        dp(i + 1,j);
        dp(i - 1,j);
        dp(i, j - 1);
        dp(i, j + 1);
    }

    public static boolean isBorder(int i,int j){
        return i == 0 || i == points.length - 1 || j == 0 || j == points[0].length - 1;
    }


}
