package com.my.day09.Collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
    public static <T> void sort(List<T> list,Comparator<? super T>)
    Comparator 和 Comparable 有区别
        Comparable：自己和别人比较，需要重写 compareTo 方法
        Comparator：想当于找第三方裁判比较

 */
public class Demo03Sort {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(5);
        list.add(3);
        System.out.println(list); // [1, 5, 3]


        Collections.sort(list, new Comparator<Integer>() {
            // 重写比较的规则
            @Override
            public int compare(Integer o1, Integer o2) {
                // 前减后，默认升序，后减前，降序排序
                //return o1 - o2; // 升序
                return o2 - o1; // 降序
            }
        });
        System.out.println(list); // [1, 3, 5]

        ArrayList<Person> list1 = new ArrayList<>();
        list1.add(new Person("迪丽热巴",12));
        list1.add(new Person("杨幂",12));
        list1.add(new Person("杨幂",18));

        Collections.sort(list1, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                // 按年龄升序
                int result = o1.getAge() - o2.getAge();
                // 如果年龄相同，按姓名排列
                if (result == 0){
                    result = o1.getName().charAt(0) - o2.getName().charAt(0);
                }
                return result;
            }
        });

        System.out.println(list1); // [Person{name='杨幂', age=12}, Person{name='迪丽热巴', age=12}, Person{name='杨幂', age=18}]


    }
}
