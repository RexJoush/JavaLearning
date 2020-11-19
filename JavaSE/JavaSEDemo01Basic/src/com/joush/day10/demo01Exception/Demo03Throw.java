package com.joush.day10.demo01Exception;

/*
    throw 关键字，可以使用 throw 关键字在指定的方法中抛出指定异常
    格式：
        throw new xxxException("异常的原因");
    注意：
        1.throw 关键字必须写在方法的内部
        2.throw 关键字后边 new 的对象必须是 Exception或者 Exception 的子类
        3.throw 关键字抛出指定异常对象，我们就必须处理这个异常对象
            throw 关键字后边创建的是 RuntimeException 或者是 RuntimeException 的子类
            我们可以不处理，默认交给 JVM 处理，打印异常对象，中断程序
            throw 关键字后边创建的是编译异常(写代码的时候报错)，我们就必须处理这个异常，要么 throws，要么 try... catch
 */
public class Demo03Throw {

    public static void main(String[] args) {
        int[] arr = null;
        int i = getElement(arr,1);
        System.out.println(i);
    }

    /*
        以后我们首先必须对方法传递的参数进行校验，如果不合法，那么我们就必须抛出异常，告知方法的调用者，传递参数有问题
     */
    public static int getElement(int[] arr,int index){
        /*
            对传过来的参数进行校验
            如果数组是 null，抛出空指针异常，告知调用者，数组为空
            注意：java.lang.NullPointerException 是一个运行期异常，我们不用处理，默认交给 JVM 处理

         */
        if (arr == null){
            throw new NullPointerException("数组为空！");
        }

        /*
            对 index 进行合法校验
            如果 index 超过数组索引范围，那么就抛出数组索引越界异常
         */
        if (index < 0 || index > arr.length){
            throw new ArrayIndexOutOfBoundsException("索引越界！");
        }

        return arr[index];
    }

}
