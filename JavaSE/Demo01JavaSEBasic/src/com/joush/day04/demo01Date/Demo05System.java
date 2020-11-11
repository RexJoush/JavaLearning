package com.joush.day04.demo01Date;

import java.util.Arrays;

/*
    java.long.System 类提供大量静态方法，可以获取与系统有关的信息或操作
    常用方法：
        public static long currentTimeMillis() 返回当前的毫秒时间
        public static void arraycopy(Object src, int srcPos, Object dest, int detPos, int length)
        将数组中指定的数据拷贝到另一数组中
        参数
            - src       源数组
            - srcPos    源数组起始位置
            - dest      目标数组
            - destPos   目标数组起始位置
            - length    复制的数组元素数量
 */
public class Demo05System {
    public static void main(String[] args) {
        demo02();
    }

    /*
        public static void arraycopy(Object src, int srcPos, Object dest, int detPos, int length)
        将 src 数组前三个元素复制到 dest 数组前三个位置上
     */
    private static void demo02() {
        int[] src = {1,2,3,4,5,6};
        int[] dest = {4,5,6,7,8,9};

        System.arraycopy(src,0,dest,0,3);
        System.out.println(Arrays.toString(dest));
    }

    /*
        public static long currentTimeMillis()
        可以用来测试程序的效率
    */
    private static void demo01() {
        // 程序开始前，获取一次毫秒值
        long s = System.currentTimeMillis();

        for (int i = 0; i < 9999; i++) {
            System.out.print(i);
        }
        // 结束获取一次
        long e = System.currentTimeMillis();
        // 得到程序执行时间
        System.out.println();
        System.out.println("时间差: " + (e-s));
    }
}
