package com.my.day10.demo03MyException;

import java.util.List;

/*
    异常的注意事项
 */
public class Demo01Exception {
    public static void main(String[] args) {
        /*
            多个异常如何处理？
            1.多个异常分别处理
            2.多个异常一次捕获，多次处理
            3.多个异常一次捕获，一次处理
         */





        // 1.多个异常分别处理
        /*
        try {
            int[] arr = {1,2,3};
            System.out.println(arr[3]); // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

        try {
            List<Integer> list = List.of(1, 2, 3);
            System.out.println(list.get(3)); // java.lang.IndexOutOfBoundsException: Index 3 out of bounds for length 3
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

         */

        // 2.多个异常，一次捕获，多次处理
        /*
            注意事项：
                1.catch里面定义的异常变量，如果有子父类关系，那么子类的异常变量必须写在上面
                ArrayIndexOutOfBoundsException extends IndexOutOfBoundsException
         */
        /*
        try{
            int[] arr = {1,2,3};
            System.out.println(arr[3]);
            List<Integer> list = List.of(1, 2, 3);
            System.out.println(list.get(3));
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
         */

        // 3.多个异常，一次捕获，一次处理
        try{
            int[] arr = {1,2,3};
            System.out.println(arr[3]);
            List<Integer> list = List.of(1, 2, 3);
            System.out.println(list.get(3));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
