package com.joush.day04.demo01Date;

/*
    java.text.DateFormat 是日期/时间格式化子类的抽象类
    作用：
        格式化（日期 --> 文本）解析（文本 --> 日期）
    成员方法：
        String format (Date date) 按照指定的模式，把 Date 日期格式化为符合模式的字符串
        Date parse (String source) 把符合模式的字符串解析为 Date 日期
    DateFormat 是一个抽象类，无法创建对象，使用 DateFormat 类的子类

    java.text.SimpleDateFormat extends DateFormat
    构造方法
        SimpleDateFormat (String patten)
            用给定的模式和默认语言环境的日期格式符号构造 SimpleDateFormat
        参数
            String patten 传递指定模式
        模式，区分大小写
            y   年
            M   月
            d   日
            H   时
            m   分
            s   秒
        写对应的模式，会把模式转换为对应的日期和时间
            "yyyy-MM-dd HH:mm:ss"
         注意，模式中的字母不能更改，但连接模式的符号可以改
            "yyyy年MM月dd日 HH时mm分ss秒"
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Demo03DateFormat {
    public static void main(String[] args) throws ParseException {
        demo02();
    }

    /*
        使用 DateFormat 类中的方法 parse，把文本转换为日期
        注意
            parse 声明了一个 ParsException 异常
            如果字符串和构造方法的模式不一样，那么程序就抛出此异常
     */
    private static void demo02() throws ParseException {
        // 1.创建 SimpleDateFormat 对象，构造方法中传递指定模式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 2.调用 SimpleDateFormat 对象的方法 parse，按照构造方法中指定的模式，把字符串解析为日期
        Date date = sdf.parse("2020-03-04 18:14:14");
        System.out.println(date);
    }

    /*
        使用 DateFormat 类中的方法 format，把日期转换为文本
     */
    private static void demo01() {
        // 1.创建 SimpleDateFormat 对象，构造方法中传递指定模式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        // 2.调用 SimpleDateFormat 对象的方法 format，按照构造方法中指定的模式，把 Date 日期格式化为复合模式的字符串
        String str = sdf.format(date);
        System.out.println(date); // Wed Mar 04 18:14:14 CST 2020
        System.out.println(str); // 2020-03-04 18:14:14
    }
}
