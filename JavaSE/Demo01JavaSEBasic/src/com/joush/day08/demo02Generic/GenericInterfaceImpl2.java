package com.joush.day08.demo02Generic;

/*
    含有泛型的接口的第二种使用方式，接口使用什么泛型，实现类就使用什么泛型，等于定义了一个含有泛型的类

    - public interface List<E>{
        boolean add(E e);
        E get(int index);
    }
    - public class ArrayList<E> implements List<E>{
        public boolean add(E e){}
        public E get(int index){}
    }

 */
public class GenericInterfaceImpl2<E> implements GenericInterface<E>{

    @Override
    public void method(E e) {
        System.out.println(e);
    }
}
