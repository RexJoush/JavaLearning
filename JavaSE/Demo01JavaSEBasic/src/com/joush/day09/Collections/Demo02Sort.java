package com.joush.day09.Collections;

import java.util.ArrayList;
import java.util.Collections;

/*
    java.util.Collections 是集合工具类
        - public static <T> void sort(List<T> list) 将集合中的元素进行排序

    注意：
        如果比较的是自定义类型，需要实现 Comparable接口，重写 compareTo方法自定义排序规则
    排序规则：
        自己(this) - 参数 （升序）
        参数 - 自己(this)（降序）
 */
public class Demo02Sort {

    public static void main(String[] args) {
        ArrayList<Integer> list01 = new ArrayList<>();
        list01.add(1);
        list01.add(3);
        list01.add(2);
        list01.add(6);
        System.out.println(list01); // [1, 3, 2, 6]
        Collections.sort(list01);
        System.out.println(list01); // 默认升序，[1, 2, 3, 6]

        ArrayList<String> list02 = new ArrayList<>();
        list02.add("aa");
        list02.add("bb");
        list02.add("cc");
        Collections.sort(list02);
        System.out.println(list02); // [aa, bb, cc]

        ArrayList<Person> list03 = new ArrayList<>();
        list03.add(new Person("张三",12));
        list03.add(new Person("李四",19));
        list03.add(new Person("王五",15));
        System.out.println(list03); // [Person{name='张三', age=12}, Person{name='李四', age=19}, Person{name='王五', age=15}]
        Collections.sort(list03);
        System.out.println(list03); // [Person{name='张三', age=12}, Person{name='王五', age=15}, Person{name='李四', age=19}]
    }

}
