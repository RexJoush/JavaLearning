package com.my.day13.demo03Lambda;

public class Demo01Cook {
    public static void main(String[] args) {

        // 调用 invokeCook 方法，参数是 Cook 接口，传递 Cook 接口的匿名内部类
        invokeCook(new Cook() {
            @Override
            public void makeFood() {
                System.out.println("吃饭啦！");
            }
        });

        // 使用 Lambda 表达式，简化代码
        invokeCook(() -> {
            System.out.println("吃饭啦");
        });

        // 优化 Lambda 表达式
        invokeCook(() -> System.out.println("吃饭啦"));

    }
    // 定义一个方法，参数传递 Cook 接口，方法内部调用 Cook 接口中的方法 makeFood
    public static void invokeCook(Cook cook){
        cook.makeFood();
    }
}
