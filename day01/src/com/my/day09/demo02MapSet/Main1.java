package com.my.day09.demo02MapSet;

import java.util.Scanner;

/*
    有一个2*n的网格，有一个人位于(1,1)的位置，即左上角，他希望从左上角走到右下角，即(2,n)的位置。在每一次，他可以进行三种操作中的一种：
    1． 向右走一格，即从(x,y)到(x,y+1);
    2． 向上右方走一格，即，如果他在(2,y)的位置可以走到(1,y+1);
    3． 向下右方走一格，即，如果他在(1,y)的位置可以走到(2,y+1);
    问题当然不会这么简单，在这2*n的格子中，有一部分格子上有障碍物，他不能停在障碍物上，当然也不能走出网格，
    请问他有多少种不同的路线可以到达(2,n)。
 */
public class Main1 {
    static int count = 0;
    static int nowCol = 0;
    static int nowRow = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 输入
        int N;
        N = sc.nextInt();
        char[] ch1 = sc.next().toCharArray();
        char[] ch2 = sc.next().toCharArray();
        int[][] a = new int[2][N];
        // 换成 01数组
        for (int i=0;i<N;i++){
            if (ch1[i] == 'X'){
                a[0][i] = 0;
            }else {
                a[0][i] = 1;
            }
            if (ch2[i] == 'X'){
                a[1][i] = 0;
            }else {
                a[1][i] = 1;
            }
        }

        dfs(a,N);

    }

    public static void dfs(int[][] a,int N){
        if (nowCol == N-1 && nowRow == 1){
            count++;
            return;
        }
        if (goToYou(a)){
            dfs(a,N);
        }
        else if (goToYouShang(a)){
            dfs(a,N);
        } else if (goToYouXia(a)){
            dfs(a,N);
        }else {
            System.out.print(-1);
        }
    }
    public static boolean goToYouShang(int[][] a){
        if (nowRow == 0)
            return false;
        else if (a[0][nowCol+1] == 1){
            nowCol++;
            return true;
        }
        return false;
    }
    public static boolean goToYouXia(int[][] a){
        if (nowRow == 1)
            return false;
        else if (a[1][nowCol+1] == 1){
            nowCol++;
            return true;
        }
        return false;
    }
    public static boolean goToYou(int[][] a){
        if (a[nowRow][nowCol+1] == 1){
            nowCol++;
            return true;
        }else {
            return false;
        }
    }
}
