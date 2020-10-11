package com.my.day18.demo08ReflectAnnotation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Demo01 {


    public void show() {
        System.out.println("demo01 ... show");
    }


    BigDecimal unique(int m, int n) {
        int N = m + n + 2;
        int K = N - 1;
        BigDecimal bg = new BigDecimal(1);
        BigDecimal decimal;
        decimal = new BigDecimal(0);

        for (int i = 1; i <= n - 1; i++) {
            BigDecimal res = new BigDecimal(N - K + i);
            BigDecimal res2 = new BigDecimal(i);
            decimal = bg.multiply(res).divide(res2);
        }

        return decimal;
    }

}
