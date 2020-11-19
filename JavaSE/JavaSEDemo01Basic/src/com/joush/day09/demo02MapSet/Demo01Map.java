package com.joush.day09.demo02MapSet;

import java.util.HashMap;
import java.util.Map;

/*
    java.util.Map<k,v> 集合
    Map集合的特点：
        1.Map是一个双列集合，一个元素包含两个值，（key,value）
        2.Map集合中的 key 和 value 数据类型可以不同
        3.Map集合中的 key 是不允许重复，但 value 可以重复
        4.Map集合的 key 和 value 是一一对应的
    java.util.HashMap<k,v> implements Map<k,v>
    HashMap特点
        1.HashMap底层是哈希表，查询速度很快
            jdk1.8之前，由数组和链表组成
            jdk1.8之后，由数组和链表或者数组和红黑树
        2.HashMap集合是无序的集合
    java.util.LinkedHashMap<k.v> extends HashMap<k,v>
        1.LinkedHashMap 集合底层是哈希表+链表
        2.LinkedHashMap 集合是有序集合
 */
public class Demo01Map {
    public static void main(String[] args) {
        demo04();
    }

    /*
        boolean containsKey(Object key) 判断集合中是否包含指定的键
        包含返回 true，不包含返回 false
     */
    private static void demo04() {
        Map<String,Integer> map = new HashMap<>();
        map.put("赵丽颖",168);
        map.put("杨颖",165);
        map.put("林志玲",178);

        boolean b1 = map.containsKey("赵丽颖");
        System.out.println(b1); // true
        boolean b2 = map.containsKey("赵颖");
        System.out.println(b2); // false

    }

    /*
        public V get(Object key) 根据指定的键，在 Map 集合中获取对应的值
            返回值，key 存在，返回对应的值，key 不存在，返回 null
     */
    private static void demo03() {
        Map<String,Integer> map = new HashMap<>();
        map.put("赵丽颖",168);
        map.put("杨颖",165);
        map.put("林志玲",178);

        Integer v1 = map.get("杨颖");
        System.out.println(v1); // 165
        Integer v2 = map.get("迪丽热巴");
        System.out.println(v2); // null

    }

    /*
        public V remove(Object key) 把指定键对应的键值对元素在集合中删除，返回被删除的 value
            返回值，如果 key 存在，v 返回被删除的值，key 不存在，v 返回 null
     */
    private static void demo02() {
        Map<String,Integer> map = new HashMap<>();
        map.put("赵丽颖",168);
        map.put("杨颖",165);
        map.put("林志玲",178);

        System.out.println(map); // {林志玲=178, 赵丽颖=168, 杨颖=165}

        Integer v1 = map.remove("林志玲");
        System.out.println(v1); // 178

        System.out.println(map); // {赵丽颖=168, 杨颖=165}

        // 如果是 null 用 int 接受的话会爆出异常 java.lang.NullPointerException
        // 所以尽量使用 Integer
        Integer v2 = map.remove("林志颖");
        System.out.println(v1); // null

    }

    /*
        public V put(K key, V value) 把指定的键值添加到集合中
        返回值 V
            存储键值对的时候，key 不重复返回值是 null，重复，会使用新的 value 替换 旧的 value，并返回旧的 value
     */
    private static void demo01() {
        // 创建 Map 集合，多态
        Map<String,String> map = new HashMap<>();

        String v1 = map.put("李晨", "范冰冰1");
        System.out.println(v1); // null
        String v2 = map.put("李晨", "范冰冰2");
        System.out.println(v2); // 范冰冰1

        System.out.println(map); // {李晨=范冰冰2}

        map.put("冷锋","龙小云");
        map.put("杨过","小龙女");
        map.put("尹志平","小龙女");

        System.out.println(map); // {杨过=小龙女, 尹志平=小龙女, 李晨=范冰冰2, 冷锋=龙小云}

    }
}
