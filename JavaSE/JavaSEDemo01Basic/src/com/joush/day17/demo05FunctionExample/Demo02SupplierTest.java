package com.joush.day17.demo05FunctionExample;

import java.util.function.Supplier;

/*
    求数组元素的最大值
        使用 Supplier 接口作为方法的参数类型，通过 Lambda 表达式求出 int 数组中的最大值
        提示，接口的泛型使用 java.lang.Integer
 */
public class Demo02SupplierTest {
    public static int getMax(Supplier<Integer> supplier){
        return supplier.get();
    }

    public static void main(String[] args) {
        int[] arr = {100,0,-50,88,99,22,-403};

        int maxValue = getMax(() -> {
            // 获取数组最大值并返回
            int max = arr[0];

            for (int i : arr) {
                if (i > max) {
                    max = i;
                }
            }
            return max;
        });

        System.out.println(maxValue);

    }

}
