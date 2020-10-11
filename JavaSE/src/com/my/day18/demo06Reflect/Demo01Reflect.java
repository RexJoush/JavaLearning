package com.my.day18.demo06Reflect;

/*
    - 获取类对象的方式
        1.Class.forName("全类名"); 将字节码文件加载进内存，返回 Class 对象
        2.类名.class; 通过类名的属性，class 获取
        3.对象.getClass(); getClass() 方法定义在 Object 类中
 */
public class Demo01Reflect {
    public static void main(String[] args) throws ClassNotFoundException {
        // 1.Class.forName("全类名");
        Class cls1 = Class.forName("com.my.day18.demo06Reflect.Person");
        System.out.println(cls1);

        // 2.类名.class;
        Class cls2 = Person.class;
        System.out.println(cls2);

        // 3.对象.getClass();
        Person p = new Person();
        Class cls3 = p.getClass();
        System.out.println(cls3);

        System.out.println(cls1 == cls2); // true
        System.out.println(cls1 == cls3); // true
        

    }
}
