package com.joush.day10.demo01Exception;

public class Demo02Exception {
    public static void main(String[] args) {

        int[] arr = {1,2,3};
        int i = getElement(arr,3);
        // 抛出 java.lang.ArrayIndexOutOfBoundsException
        System.out.println(i);

    }

    // 定义一个方法，获取数组指定索引处的元素
    public static int getElement(int[] arr,int index){
        return arr[index];
    }
}
