package com.joush.day10.demo03MyException;

/*
    自定义异常类：
        java 提供的异常类，需要自定义异常
    格式
        public class XXXException extends Exception | RuntimeException {
            // 1.添加一个空参数的构造方法
            // 2.添加一个带信息的构造方法
        }
    注意事项
        1.自定义异常类一般都是以 Exception 结尾，说明该类是一个异常类
        2.自定义异常类，必须继承 Exception 或 RuntimeException
            继承 Exception 那么定义的类就是编译期异常，如果方法内部抛出了编译期异常，就必须处理，throw 或者 try...catch
            继承 RuntimeException 那么定义的类就是运行期异常，无需处理，交给 jvm 处理
 */
public class MyException extends Exception{
    // 空参数构造方法
    public MyException() {

    }

    // 带异常信息的构造方法
    public MyException(String message) {
        super(message);
    }
}
