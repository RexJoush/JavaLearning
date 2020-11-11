package com.joush.day09.demo02MapSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
    HashMap 存储自定义类型键值
    Map 集合保证 key 唯一
        作为 key 的元素必须重写 hashCode 方法，和 equals 方法
 */
public class Demo04HashMapSavePerson {
    public static void main(String[] args) {
        demo02();
    }

    /*
        HashMap 存储自定义类型键值
        key: Person
            Person 类需要重写 hashCode 方法，和 equals 方法，以保证 key 唯一
        value: String
            value可以重复
     */
    private static void demo02() {
        HashMap<Person,String> map = new HashMap<>();
        map.put(new Person("女王",18),"英国");
        map.put(new Person("秦始皇",18),"秦国");
        map.put(new Person("普京",18),"俄罗斯");
        map.put(new Person("女王",18),"毛里求斯");

        // 使用 EntrySet 和增强 for 遍历集合
        Set<Map.Entry<Person,String>> set = map.entrySet();
        for (Map.Entry<Person,String> entry :set){
            Person key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + value);
        }
    }

    /*
        HashMap 存储自定义类型键值
        key: String
            String 重写了 hashCode 方法，和 equals 方法，可以保证 key 唯一
        value: Person
            value可以重复，同名同年龄视为重复
     */
    private static void demo01() {
        HashMap<String,Person> map = new HashMap<>();
        map.put("北京",new Person("张三",18));
        map.put("上海",new Person("李四",19));
        map.put("广州",new Person("王五",20));
        map.put("北京",new Person("赵六",18));

        //System.out.println(map);
        for (String set : map.keySet()){
            System.out.println(set + map.get(set));
        }

    }
}
