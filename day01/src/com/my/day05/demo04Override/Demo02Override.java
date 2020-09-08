package com.my.day05.demo04Override;

/*
    方法覆盖重写的注意事项：
    1.必须保证父子类之间方法的名称相同，参数列表也相同
        @Override，写在方法前面，用来检测是不是有效正确的覆盖重写
    2.子类方法的返回值必须小于等于父类返回值的范围。
        java.lang.Object 类是所有类的父类。
    3.子类方法的权限，必须大于等于父类的修饰符
        public > protected > (default) > private default不是关键字，是指什么都不写
 */
public class Demo02Override {

}
