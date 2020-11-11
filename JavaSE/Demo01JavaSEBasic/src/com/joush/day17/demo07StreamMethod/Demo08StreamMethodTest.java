package com.joush.day17.demo07StreamMethod;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Demo08StreamMethodTest {
    public static void main(String[] args) {
        // 第一支
        ArrayList<String> one = new ArrayList<>();
        one.add("迪丽热巴");
        one.add("宋元桥");
        one.add("苏星河");
        one.add("石破天");
        one.add("石中玉");
        one.add("老子");
        one.add("庄子");
        one.add("洪七公");

        // 1.获取三个字的存储到新集合中
        ArrayList<String> one1 = new ArrayList<>();
        for (String name : one) {
            if (name.length() == 3){
                one1.add(name);
            }
        }
        // 2.筛选完后只要前三个人
        ArrayList<String> one2 = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            one2.add(one1.get(i));
        }



        // 第二支
        ArrayList<String> two = new ArrayList<>();
        two.add("古力娜扎");
        two.add("张无忌");
        two.add("赵丽颖");
        two.add("张三丰");
        two.add("尼古拉斯赵四");
        two.add("张天爱");
        two.add("张二狗");

        // 优化后的方式
        Stream<String> oneStream = one.stream()
                .filter((name) -> name.length() == 3)       // 取三个字
                .limit(3);

        Stream<String> twoStream = two.stream()
                .filter((name) -> name.startsWith("张"));
        Stream.concat(oneStream, twoStream)
                .map(name->new Person(name))
        .forEach((p) -> System.out.println(p));



        /*

        // 3.只要姓张的成员
        ArrayList<String> two1 = new ArrayList<>();
        for (String name : two) {
            if (name.startsWith("张")){
                two1.add(name);
            }
        }
        // 4.不要前两个人
        ArrayList<String> two2 = new ArrayList<>();
        for (int i = 2; i < two1.size(); i++) {
            two2.add(two1.get(i));
        }


        // 5.合并两个集合
        ArrayList<String> all = new ArrayList<>();
        all.addAll(one2);
        all.addAll(two2);

        // 6.根据姓名创建 Person 对象，存储到新集合
        ArrayList<Person> list = new ArrayList<>();
        for (String name : all){
            list.add(new Person(name));
        }

        // 7.打印信息
        for (Person person : list) {
            System.out.println(person);
        }

         */


    }
}
