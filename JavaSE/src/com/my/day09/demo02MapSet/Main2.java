package com.my.day09.demo02MapSet;

import java.util.*;

/*
    给出一个序列包含n个正整数的序列A，然后给出一个正整数x，你可以对序列进行任意次操作的，
    每次操作你可以选择序列中的一个数字，让其与x做按位或运算。你的目的是让这个序列中的众数出现的次数最多。
    请问众数最多出现多少次。
    in  输入第一行仅包含两个正整数n和x，表示给出的序列的长度和给定的正整数。(1<=n<=100000,1<=x<=1000)
        接下来一行有n个正整数，即这个序列，中间用空格隔开。(1<=a_i<=1000)
    out 输出仅包含一个正整数，表示众数最多出现的次数。
    5 2
    3 1 3 2 5
    3
 */
public class Main2 {
    public static void main(String[] args) {
        Map<Integer,Integer> map = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        int N,k,tempkey,tempval;
        int bestKey = 0;
        int bestTemp = 0;
        N = sc.nextInt();
        int[] a = new int[N];
        k = sc.nextInt();
        for (int i = 0;i<N;i++){
            a[i] = sc.nextInt();
        }
        for (int i = 0;i<N;i++){
            tempkey = a[i];
            if (!map.containsKey(tempkey)){
                map.put(tempkey,1);
            }
            else {
                tempval = map.get(tempkey);
                if (tempval+1 > bestTemp){
                    bestKey = tempkey;
                }
                map.put(tempkey,tempval+1);

            }
        }
        Set<Integer> set = new HashSet<>();
        set = map.keySet();
        Iterator<Integer> it = set.iterator();
        while(it.hasNext()){
            int temp = it.next();
            if (bestKey == temp){
                continue;
            }
            tempkey = map.get(temp) | k;
            if (map.containsKey(tempkey)) {
                tempval = map.get(tempkey);
                if (tempval+1 > bestTemp){
                    bestKey = tempkey;
                }
                map.put(tempkey,tempval+1);
            }
        }
        System.out.print(bestKey);
    }
}
