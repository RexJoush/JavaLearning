package com.joush.day08.demo04HashSet;

/*
    哈希值，是一个十进制整数，由系统随机给出，对象的地址，是模拟地址，不是实际的内存物理地址
    Object 类有一个方法可以获取对象的哈希值
    int hashCode(); 返回对象的哈希值
        public native int hashCode();
        native 是指调用该方法的是本地操作系统方法
 */
public class Demo01HashCode {
    public static void main(String[] args) {
        // Person 类继承了 Object 类，所以可以使用 hashCode 方法
        Person p1 = new Person();
        int h1 = p1.hashCode();
        System.out.println(h1); // 1239731077

        /*
            toString 方法
            return getClass().getName() + "@" + Integer.toHexString(hashCode());
         */
        System.out.println(p1); // com.my.day08.demo04.Person@49e4cb85

        /*
            String 类的哈希值
                String 类重写了 Object 类的 hashCode 方法
         */
        String s1 = "abc";
        String s2 = "abc";
        System.out.println(s1.hashCode()); // 96354
        System.out.println(s2.hashCode()); // 96354

        // 虽然字符串不一样，但哈希值一样
        System.out.println("重地".hashCode()); // 1179395
        System.out.println("通话".hashCode()); // 1179395
    }

}
