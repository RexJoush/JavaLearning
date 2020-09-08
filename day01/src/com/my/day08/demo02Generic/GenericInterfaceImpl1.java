package com.my.day08.demo02Generic;

/*
    含有泛型接口的第一种使用方法，定义接口的实现类，实现接口，指定接口泛型
    public interface Iterator<E> {
        public E next();
    }
    Scanner 类实现了 Iterator 接口，并指定接口泛型为 String，所以重写的 next() 方法泛型默认就是 String
    public final class Scanner implements Iterator<String> {
        public String next();
    }

 */
public class GenericInterfaceImpl1 implements GenericInterface<String> {

    @Override
    public void method(String s) {
        System.out.println(s);
    }
}
